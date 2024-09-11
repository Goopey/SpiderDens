package com.goopey.spiderdens.world.block.entity;

import com.goopey.spiderdens.core.init.BlockEntityInit;
import com.goopey.spiderdens.world.block.FilterHopper;
import com.goopey.spiderdens.world.screens.menu.FilterHopperMenu;

import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
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
import net.neoforged.neoforge.items.VanillaInventoryCodeHooks;

public class FilterHopperBlockEntity extends HopperBlockEntity {
   public static final int MOVE_ITEM_SPEED = 8;
   // 5 for the hopper moving spaces and 27 for the inventory slots to define accepted items
   public static final int HOPPER_CONTAINER_SIZE = 5;
   public static final int HOPPER_FILTER_SIZE = 27;
   private static final int[][] CACHED_SLOTS = new int[54][];
   private NonNullList<ItemStack> items;
   private NonNullList<ItemStack> filterItems;
   private int cooldownTime;
   // This stuff is used, but only by the parent class, so we need to keep it.
   @SuppressWarnings("unused")
   private long tickedGameTime;
   private Direction facing;

   public FilterHopperBlockEntity(BlockPos pos, BlockState blockState) {
      super(pos, blockState);
      this.items = NonNullList.withSize(HOPPER_CONTAINER_SIZE, ItemStack.EMPTY);
      this.filterItems = NonNullList.withSize(HOPPER_FILTER_SIZE, ItemStack.EMPTY);
      this.cooldownTime = -1;
      this.facing = (Direction)blockState.getValue(FilterHopper.FACING);
   }

   //####################################################
   //                  FILTER HOPPER
   //####################################################

   public static void filterPushItemsTick(Level pLevel, BlockPos pPos, BlockState pState, FilterHopperBlockEntity blockEntity) {
      --blockEntity.cooldownTime;
      blockEntity.tickedGameTime = pLevel.getGameTime();
      if (!blockEntity.isOnCooldown()) {
         blockEntity.setCooldown(0);
         tryMoveItems(pLevel, pPos, pState, blockEntity, () -> {
            return filterSuckInItems(pLevel, blockEntity);
         });
      }
   }

   public static void filterEntityInside(Level pLevel, BlockPos pPos, BlockState blockState, Entity pEntity, FilterHopperBlockEntity blockEntity) {
      if (pEntity instanceof ItemEntity itementity) {
         if (!itementity.getItem().isEmpty() && pEntity.getBoundingBox().move((double)(-pPos.getX()), (double)(-pPos.getY()), (double)(-pPos.getZ())).intersects(blockEntity.getSuckAabb())) {
               tryMoveItems(pLevel, pPos, blockState, blockEntity, () -> {
                  if (checkMatch(itementity.getItem(), blockEntity.getFilterItems())) {
                     return addItem(blockEntity, itementity);
                  }

                  return false;
               });
         }
      }
   }

   private static boolean checkMatch(ItemStack item, NonNullList<ItemStack> filterItems) {
      for (ItemStack filterItem : filterItems) {
         if (item.getItem().equals(filterItem.getItem())) {
            return true;
         }
      }         

      return false;
   }

   //###################################################
   //                   HOPPER
   //###################################################

   public static boolean filterSuckInItems(Level pLevel, FilterHopperBlockEntity blockEntity) {
      BlockPos blockpos = BlockPos.containing(blockEntity.getLevelX(), blockEntity.getLevelY() + 1.0, blockEntity.getLevelZ());
      BlockState blockstate = pLevel.getBlockState(blockpos);
      Boolean ret = VanillaInventoryCodeHooks.extractHook(pLevel, blockEntity);
      if (ret != null) {
         return ret;
      } else {
         Container container = getSourceContainer(pLevel, blockEntity, blockpos, blockstate);
         if (container != null) {
            Direction direction = Direction.DOWN;
            int[] var12 = getSlots(container, direction);
            int var13 = var12.length;

            for(int var9 = 0; var9 < var13; ++var9) {
               int i = var12[var9];
               if (tryTakeInItemFromSlot(blockEntity, container, i, direction)) {
                  return true;
               }
            }

            return false;
         } else {
            boolean flag = blockEntity.isGridAligned() && blockstate.isCollisionShapeFullBlock(pLevel, blockpos) && !blockstate.is(BlockTags.DOES_NOT_BLOCK_HOPPERS);
            if (!flag) {
               Iterator<ItemEntity> var7 = getItemsAtAndAbove(pLevel, blockEntity).iterator();

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

   private static boolean tryTakeInItemFromSlot(FilterHopperBlockEntity pHopper, Container pContainer, int pSlot, Direction pDirection) {
      ItemStack itemstack = pContainer.getItem(pSlot);
      if (!itemstack.isEmpty() && canTakeItemFromContainer(pHopper, pContainer, itemstack, pSlot, pDirection)) {
         if (checkMatch(itemstack, pHopper.getFilterItems())) {
            int i = itemstack.getCount();
            ItemStack itemstack1 = addItem(pContainer, pHopper, pContainer.removeItem(pSlot, 1), (Direction)null);
            if (itemstack1.isEmpty()) {
               pContainer.setChanged();
               return true;
            }
            
            itemstack.setCount(i);
            if (i == 1) {
               pContainer.setItem(pSlot, itemstack);
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

   private static boolean tryMoveItems(Level pLevel, BlockPos pPos, BlockState pState, FilterHopperBlockEntity blockEntity, BooleanSupplier pValidator) {
      if (!pLevel.isClientSide) {
         if (!blockEntity.isOnCooldown() && (Boolean)pState.getValue(FilterHopper.ENABLED)) {
            boolean flag = false;
      
            if (!blockEntity.isEmpty()) {
               flag = ejectItems(pLevel, pPos, blockEntity);
            }

            if (!blockEntity.inventoryFull()) {
               flag |= pValidator.getAsBoolean();
            }

            if (flag) {
               blockEntity.setCooldown(8);
               setChanged(pLevel, pPos, pState);
      
               return true;
            }
         }
      }

      return false;
   }

   private static boolean ejectItems(Level pLevel, BlockPos pPos, FilterHopperBlockEntity blockEntity) {
      if (VanillaInventoryCodeHooks.insertHook(blockEntity)) {
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
               for(int i = 0; i < blockEntity.getContainerSize(); ++i) {
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

   private static boolean isFullContainer(Container pContainer, Direction pDirection) {
      int[] aint = getSlots(pContainer, pDirection);
      int[] var3 = aint;
      int var4 = aint.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int i = var3[var5];
         ItemStack itemstack = pContainer.getItem(i);
         if (itemstack.getCount() < itemstack.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private static int[] getSlots(Container pContainer, Direction pDirection) {
      if (pContainer instanceof WorldlyContainer worldlycontainer) {
         return worldlycontainer.getSlotsForFace(pDirection);
      } else {
         int i = pContainer.getContainerSize();
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

   private static int[] createFlatSlots(int pSize) {
      int[] aint = new int[pSize];

      for(int i = 0; i < aint.length; aint[i] = i++);

      return aint;
   }

   private boolean inventoryFull() {
      Iterator<ItemStack> var1 = this.items.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(!itemstack.isEmpty() && itemstack.getCount() == itemstack.getMaxStackSize());

      return false;
   }

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

   private boolean isOnCooldown() {
      return this.cooldownTime > 0;
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

   //##############################################
   //                CONTAINERS
   //##############################################

   @Override
   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return new FilterHopperMenu(id, player, this);
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   protected NonNullList<ItemStack> getFilterItems() {
      return this.filterItems;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> newItems) {
      this.items = newItems;
   }
}
