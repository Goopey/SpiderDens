package com.goopey.spiderdens.world.block.entity;

import com.goopey.spiderdens.core.init.BlockEntityInit;
import com.goopey.spiderdens.util.FilterHopperCodeHooks;
import com.goopey.spiderdens.world.block.FilterHopper;
import com.goopey.spiderdens.world.screens.menu.FilterHopperMenu;

import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FilterHopperBlockEntity extends HopperBlockEntity {
   public static final int MOVE_ITEM_SPEED = 8;
   // 5 for the hopper moving spaces and 27 for the inventory slots to define accepted items
   public static final int HOPPER_CONTAINER_SIZE = 5;
   public static final int HOPPER_FILTER_SIZE = 27;
   private static final int[][] CACHED_SLOTS = new int[54][];
   private NonNullList<ItemStack> items;
   private int cooldownTime;
   // This stuff is used, but only by the parent class, so we need to keep it.
   @SuppressWarnings("unused")
   private long tickedGameTime;
   private Direction facing;

   public FilterHopperBlockEntity(BlockPos pos, BlockState blockState) {
      super(pos, blockState);
      this.items = NonNullList.withSize(HOPPER_CONTAINER_SIZE + HOPPER_FILTER_SIZE, ItemStack.EMPTY);
      this.cooldownTime = -1;
      this.facing = (Direction)blockState.getValue(FilterHopper.FACING);
   }

   protected void loadAdditional(CompoundTag tag, HolderLookup.Provider regisitries) {
      super.loadAdditional(tag, regisitries);
      this.items = NonNullList.withSize(HOPPER_CONTAINER_SIZE + HOPPER_FILTER_SIZE, ItemStack.EMPTY);
      if (!this.tryLoadLootTable(tag)) {
         ContainerHelper.loadAllItems(tag, this.items, regisitries);
      }

      this.cooldownTime = tag.getInt("TransferCooldown");
   }

   protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
      super.saveAdditional(tag, registries);
      if (!this.trySaveLootTable(tag)) {
         ContainerHelper.saveAllItems(tag, this.items, registries);
      }

      tag.putInt("TransferCooldown", this.cooldownTime);
   }

   //####################################################
   //                  FILTER HOPPER
   //####################################################

   public static void filterPushItemsTick(Level level, BlockPos pos, BlockState state, FilterHopperBlockEntity blockEntity) {
      --blockEntity.cooldownTime;
      blockEntity.tickedGameTime = level.getGameTime();
      if (!blockEntity.isOnCooldown()) {
         blockEntity.setCooldown(0);
         tryMoveItems(level, pos, state, blockEntity, () -> {
            return filterSuckInItems(level, blockEntity);
         });
      }
   }

   public static void filterEntityInside(Level level, BlockPos pos, BlockState blockState, Entity entity, FilterHopperBlockEntity blockEntity) {
      if (entity instanceof ItemEntity itementity) {
         if (!itementity.getItem().isEmpty() && entity.getBoundingBox().move((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ())).intersects(blockEntity.getSuckAabb())) {
               tryMoveItems(level, pos, blockState, blockEntity, () -> {
                  if (checkMatch(itementity.getItem(), blockEntity.getFilterItems())) {
                     return addItem(blockEntity, itementity);
                  }

                  return false;
               });
         }
      }
   }

   //#########################################
   //             FILTER HELPER
   //#########################################

   public static boolean checkMatch(ItemStack item, NonNullList<ItemStack> filterItems) {
      for (ItemStack filterItem : filterItems) {
         if (item.getItem().equals(filterItem.getItem())) {
            return true;
         }
      }         

      return false;
   }

   //#######################################################
   //                   HOPPER MOVING
   //#######################################################

   public static boolean filterSuckInItems(Level level, FilterHopperBlockEntity blockEntity) {
      BlockPos blockpos = BlockPos.containing(blockEntity.getLevelX(), blockEntity.getLevelY() + 1.0, blockEntity.getLevelZ());
      BlockState blockstate = level.getBlockState(blockpos);

      Boolean ret = FilterHopperCodeHooks.extractHook(level, blockEntity, blockEntity);
      if (ret != null) {
         return ret;
      } else {
         Container container = getSourceContainer(level, blockEntity, blockpos, blockstate);
         if (container != null) {
            Direction direction = Direction.DOWN;
            int[] var12 = getSlots(container, direction);

            for(int var9 = 0; var9 < blockEntity.getHopperSize(); ++var9) {
               int i = var12[var9];
               if (tryTakeInItemFromSlot(blockEntity, container, i, direction)) {
                  return true;
               }
            }

            return false;
         } else {
            boolean flag = blockEntity.isGridAligned() && blockstate.isCollisionShapeFullBlock(level, blockpos) && !blockstate.is(BlockTags.DOES_NOT_BLOCK_HOPPERS);
            if (!flag) {
               Iterator<ItemEntity> var7 = getItemsAtAndAbove(level, blockEntity).iterator();

               while(var7.hasNext()) {
                  ItemEntity itementity = (ItemEntity)var7.next();
                  ItemStack itemEntityStack = itementity.getItem();

                  if (checkMatch(itemEntityStack, blockEntity.getFilterItems())) {
                     if (addItem(blockEntity, itementity)) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   private static boolean tryTakeInItemFromSlot(FilterHopperBlockEntity blockEntity, Container container, int slotId, Direction direction) {
      ItemStack itemstack = container.getItem(slotId);
      if (!itemstack.isEmpty() && canTakeItemFromContainer(blockEntity, container, itemstack, slotId, direction)) {
         if (checkMatch(itemstack, blockEntity.getFilterItems())) {
            int i = itemstack.getCount();
            ItemStack itemstack1 = addItem(container, blockEntity, container.removeItem(slotId, 1), (Direction)null);
            if (itemstack1.isEmpty()) {
               container.setChanged();
               return true;
            }
            
            itemstack.setCount(i);
            if (i == 1) {
               container.setItem(slotId, itemstack);
            }
         }
      }

      return false;
   }

   private static boolean canTakeItemFromContainer(Container pSource, Container pDestination, ItemStack pStack, int pSlot, Direction pDirection) {
      if (!pDestination.canTakeItem(pSource, pSlot, pStack)) {
         return false;
      } else {
         if (pDestination instanceof WorldlyContainer) {
            WorldlyContainer worldlycontainer = (WorldlyContainer)pDestination;
            if (!worldlycontainer.canTakeItemThroughFace(pSlot, pStack, pDirection)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean tryMoveItems(Level level, BlockPos pos, BlockState blockState, FilterHopperBlockEntity blockEntity, BooleanSupplier validator) {
      if (!level.isClientSide) {
         if (!blockEntity.isOnCooldown() && (Boolean)blockState.getValue(FilterHopper.ENABLED)) {
            boolean flag = false;
      
            if (!blockEntity.isEmpty()) {
               flag = ejectItems(level, pos, blockEntity);
            }

            if (!blockEntity.inventoryFull()) {
               flag |= validator.getAsBoolean();
            }

            if (flag) {
               blockEntity.setCooldown(8);
               setChanged(level, pos, blockState);
      
               return true;
            }
         }
      }

      return false;
   }

   private static boolean ejectItems(Level pLevel, BlockPos pPos, FilterHopperBlockEntity blockEntity) {
      if (FilterHopperCodeHooks.insertHook(blockEntity)) {
         return true;
      } else {
         Container container = getAttachedContainer(pLevel, pPos, blockEntity);
         if (container == null) {
            return false;
         } else {
            Direction direction = blockEntity.facing.getOpposite();
            if (isFullContainer(container, direction)) {
               return false;
            } else {
               for(int i = 0; i < blockEntity.getHopperSize(); ++i) {
                  ItemStack itemstack = blockEntity.getItem(i);
                  if (!itemstack.isEmpty()) {
                     if (checkMatch(itemstack, blockEntity.getFilterItems())) {
                        int j = itemstack.getCount();
                        ItemStack itemstack1 = addItem(blockEntity, container, blockEntity.removeItem(i, 1), direction);
                        if (itemstack1.isEmpty()) {
                           container.setChanged();
                           return true;
                        }
                        
                        itemstack.setCount(j);
                        if (j == 1) {
                           blockEntity.setItem(i, itemstack);
                        } 
                     }
                  }
               }

               return false;
            }
         }
      }
   }

   //#################################################
   //             SLOTS/INVENTORY HELPER
   //#################################################

   private static boolean isFullContainer(Container container, Direction direction) {
      int[] aint = getSlots(container, direction);
      int[] var3 = aint;
      int var4 = aint.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int i = var3[var5];
         ItemStack itemstack = container.getItem(i);
         if (itemstack.getCount() < itemstack.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private static int[] getSlots(Container container, Direction direction) {
      if (container instanceof WorldlyContainer worldlycontainer) {
         return worldlycontainer.getSlotsForFace(direction);
      } else {
         int i = container.getContainerSize();
         if (i < CACHED_SLOTS.length) {
            int[] aint = CACHED_SLOTS[i];
            if (aint != null) {
               return aint;
            } else {
               int[] aint1 = createFlatSlots(i);
               CACHED_SLOTS[i] = aint1;
               return aint1;
            }
         } else {
            return createFlatSlots(i);
         }
      }
   }

   private static int[] createFlatSlots(int size) {
      int[] aint = new int[size];

      for(int i = 0; i < aint.length; aint[i] = i++);

      return aint;
   }

   private boolean inventoryFull() {
      NonNullList<ItemStack> itemList = NonNullList.copyOf(this.items.subList(0, this.getHopperSize()));
      Iterator<ItemStack> var1 = itemList.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(!itemstack.isEmpty() && itemstack.getCount() == itemstack.getMaxStackSize());

      return false;
   }

   //####################################################
   //             GETTING CONTAINER STUFF
   //####################################################

   @Nullable
   private static Container getAttachedContainer(Level pLevel, BlockPos pPos, FilterHopperBlockEntity blockEntity) {
      return getContainerAt(pLevel, pPos.relative(blockEntity.facing));
   }

   @Nullable
   private static Container getSourceContainer(Level pLevel, Hopper pHopper, BlockPos pPos, BlockState pState) {
      return getContainerAt(pLevel, pPos, pState, pHopper.getLevelX(), pHopper.getLevelY() + 1.0, pHopper.getLevelZ());
   }

   @Nullable
   private static Container getContainerAt(Level pLevel, BlockPos pPos, BlockState pState, double pX, double pY, double pZ) {
      Container container = getBlockContainer(pLevel, pPos, pState);
      if (container == null) {
         container = getEntityContainer(pLevel, pX, pY, pZ);
      }

      return container;
   }

   @Nullable
   private static Container getBlockContainer(Level pLevel, BlockPos pPos, BlockState pState) {
      Block block = pState.getBlock();
      if (block instanceof WorldlyContainerHolder) {
         return ((WorldlyContainerHolder)block).getContainer(pState, pLevel, pPos);
      } else {
         if (pState.hasBlockEntity()) {
            BlockEntity var5 = pLevel.getBlockEntity(pPos);
            if (var5 instanceof Container) {
               Container container = (Container)var5;
               if (container instanceof ChestBlockEntity && block instanceof ChestBlock) {
                  container = ChestBlock.getContainer((ChestBlock)block, pState, pLevel, pPos, true);
               }

               return container;
            }
         }

         return null;
      }
   }

   @Nullable
   private static Container getEntityContainer(Level pLevel, double pX, double pY, double pZ) {
      List<Entity> list = pLevel.getEntities((Entity)null, new AABB(pX - 0.5, pY - 0.5, pZ - 0.5, pX + 0.5, pY + 0.5, pZ + 0.5), EntitySelector.CONTAINER_ENTITY_SELECTOR);
      return !list.isEmpty() ? (Container)list.get(pLevel.random.nextInt(list.size())) : null;
   }

  //#################################################
  //                 BLOCK ENTITY
  //#################################################


   @Override
   public BlockEntityType<?> getType() {
      return BlockEntityInit.FILTER_HOPPER_TILEENTITY.get();
   }

   @Override
   public int getContainerSize() {
      return HOPPER_CONTAINER_SIZE + HOPPER_FILTER_SIZE;
   }

   public int getHopperSize() {
      return HOPPER_CONTAINER_SIZE;
   }

   @Override
   public double getLevelX() {
      return (double)this.worldPosition.getX() + 0.5;
   }

   @Override
   public double getLevelY() {
      return (double)this.worldPosition.getY() + 0.5;
   }

   @Override
   public double getLevelZ() {
      return (double)this.worldPosition.getZ() + 0.5;
   }

   @Override
   public boolean isGridAligned() {
      return true;
   }

   private boolean isOnCooldown() {
      return this.cooldownTime > 0;
   }

   //##############################################
   //                MENU + ITEMS
   //##############################################

   @Override
   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return new FilterHopperMenu(id, player, this);
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   public NonNullList<ItemStack> getFilterItems() {
      NonNullList<ItemStack> filterItems = NonNullList.withSize(HOPPER_FILTER_SIZE, ItemStack.EMPTY);
      
      for (int i = HOPPER_CONTAINER_SIZE; i < HOPPER_FILTER_SIZE + HOPPER_CONTAINER_SIZE; i++) {
         filterItems.set(i - HOPPER_CONTAINER_SIZE, this.items.get(i));
      }

      return filterItems;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> newItems) {
      this.items = newItems;
   }
}
