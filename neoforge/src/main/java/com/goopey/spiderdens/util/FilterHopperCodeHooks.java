package com.goopey.spiderdens.util;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import com.goopey.spiderdens.block.FilterHopper;
import com.goopey.spiderdens.block.entity.FilterHopperBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class FilterHopperCodeHooks {
  //##################################################################################
  //      Copied from VanillaInventoryCodeHooks and adapted to Filter Hoppers
  //##################################################################################

  @Nullable
  public static Boolean extractHook(Level level, Hopper dest, FilterHopperBlockEntity blockEntity) {
    return getSourceItemHandler(level, dest)
      .map(itemHandlerResult -> {
        IItemHandler handler = itemHandlerResult.getKey();

        for (int i = 0; i < handler.getSlots(); i++) {
          ItemStack extractItem = handler.extractItem(i, 1, true);
          if (!extractItem.isEmpty()) {
            if (FilterHopperBlockEntity.checkMatch(extractItem, blockEntity.getFilterItems())) {
              for (int j = 0; j < dest.getContainerSize(); j++) {
                ItemStack destStack = dest.getItem(j);
                
                if (dest.canPlaceItem(j, extractItem) && (destStack.isEmpty() || destStack.getCount() < destStack.getMaxStackSize() && destStack.getCount() < dest.getMaxStackSize() && ItemStack.isSameItemSameComponents(extractItem, destStack))) {
                  extractItem = handler.extractItem(i, 1, false);
                  if (destStack.isEmpty())
                    dest.setItem(j, extractItem);
                  else {
                    destStack.grow(1);
                    dest.setItem(j, destStack);
                  }
                  dest.setChanged();
                  return true;
                }
              }
            }
          }
        }

        return false;
      })
      .orElse(false);
  }

  public static boolean insertHook(FilterHopperBlockEntity blockEntity) {
    Direction hopperFacing = blockEntity.getBlockState().getValue(FilterHopper.FACING);

    return getAttachedItemHandler(blockEntity.getLevel(), blockEntity.getBlockPos(), hopperFacing)
      .map(destinationResult -> {
        IItemHandler itemHandler = destinationResult.getKey();
        Object destination = destinationResult.getValue();
        if (isFull(itemHandler)) {
          return false;
        } else {
          for (int i = 0; i < blockEntity.getHopperSize(); ++i) {
            ItemStack item = blockEntity.getItem(i);

            if (!item.isEmpty()) {
              if (FilterHopperBlockEntity.checkMatch(item, blockEntity.getFilterItems())) {

                ItemStack originalSlotContents = item.copy();
                ItemStack insertStack = blockEntity.removeItem(i, 1);
                ItemStack remainder = putStackInInventoryAllSlots(blockEntity, destination, itemHandler, insertStack);
                
                if (remainder.isEmpty()) {
                  return true;
                }
                
                blockEntity.setItem(i, originalSlotContents);
              }
            }
          }

          return false;
        }
      })
      .orElse(false);
  }

  /**
   * Copied from TileEntityHopper#insertStack and added capability support
   */
  private static ItemStack insertStack(BlockEntity source, Object destination, IItemHandler destInventory, ItemStack stack, int slot) {
    ItemStack itemstack = destInventory.getStackInSlot(slot);

    if (destInventory.insertItem(slot, stack, true).isEmpty()) {
      boolean insertedItem = false;
      boolean inventoryWasEmpty = isEmpty(destInventory);

      if (itemstack.isEmpty()) {
        destInventory.insertItem(slot, stack, false);
        stack = ItemStack.EMPTY;
        insertedItem = true;
      } else if (ItemStack.isSameItemSameComponents(itemstack, stack)) {
        int originalSize = stack.getCount();
        stack = destInventory.insertItem(slot, stack, false);
        insertedItem = originalSize < stack.getCount();
    }

      if (insertedItem) {
        if (inventoryWasEmpty && destination instanceof HopperBlockEntity) {
          HopperBlockEntity destinationHopper = (HopperBlockEntity) destination;

          if (!destinationHopper.isOnCustomCooldown()) {
            int k = 0;
            if (source instanceof HopperBlockEntity) {
              if (destinationHopper.getLastUpdateTime() >= ((HopperBlockEntity) source).getLastUpdateTime()) {
                k = 1;
              }
            }
            destinationHopper.setCooldown(8 - k);
          }
        }
      }
    }

    return stack;
  }

  private static Optional<Pair<IItemHandler, Object>> getItemHandlerAt(Level worldIn, double x, double y, double z, final Direction side) {
    BlockPos blockpos = BlockPos.containing(x, y, z);
    BlockState state = worldIn.getBlockState(blockpos);
    BlockEntity blockEntity = state.hasBlockEntity() ? worldIn.getBlockEntity(blockpos) : null;

    // Look for block capability first
    var blockCap = worldIn.getCapability(Capabilities.ItemHandler.BLOCK, blockpos, state, blockEntity, side);
    if (blockCap != null)
      return Optional.of(ImmutablePair.of(blockCap, blockEntity));

    // Otherwise fallback to automation entity capability
    // Note: the isAlive check matches what vanilla does for hoppers in EntitySelector.CONTAINER_ENTITY_SELECTOR
    List<Entity> list = worldIn.getEntities((Entity) null, new AABB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelector.ENTITY_STILL_ALIVE);
    if (!list.isEmpty()) {
      Collections.shuffle(list);
      for (Entity entity : list) {
        IItemHandler entityCap = entity.getCapability(Capabilities.ItemHandler.ENTITY_AUTOMATION, side);
        if (entityCap != null)
          return Optional.of(ImmutablePair.of(entityCap, entity));
      }
    }

    return Optional.empty();
  }

  //####################################
  //              HELPER
  //####################################

  private static Optional<Pair<IItemHandler, Object>> getAttachedItemHandler(Level level, BlockPos pos, Direction direction) {
    return getItemHandlerAt(level, pos.getX() + direction.getStepX() + 0.5, pos.getY() + direction.getStepY() + 0.5, pos.getZ() + direction.getStepZ() + 0.5, direction.getOpposite());
  }

  private static Optional<Pair<IItemHandler, Object>> getSourceItemHandler(Level level, Hopper hopper) {
    return getItemHandlerAt(level, hopper.getLevelX(), hopper.getLevelY() + 1.0, hopper.getLevelZ(), Direction.DOWN);
  }

  private static boolean isFull(IItemHandler itemHandler) {
    for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
      ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
      if (stackInSlot.isEmpty() || stackInSlot.getCount() < itemHandler.getSlotLimit(slot)) {
        return false;
      }
    }
    return true;
  }

  private static ItemStack putStackInInventoryAllSlots(BlockEntity source, Object destination, IItemHandler destInventory, ItemStack stack) {
    for (int slot = 0; slot < destInventory.getSlots() && !stack.isEmpty(); slot++) {
      stack = insertStack(source, destination, destInventory, stack, slot);
    }
    return stack;
  }

  private static boolean isEmpty(IItemHandler itemHandler) {
    for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
        ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
        if (stackInSlot.getCount() > 0) {
            return false;
        }
    }
    return true;
  }
}
