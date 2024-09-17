package com.goopey.spiderdens.world.screens.menu;

import java.util.Optional;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GhostSlot extends Slot {
  public static final int GHOST_SLOT_MAX_STACK_SIZE = 1;

  public GhostSlot(Container container, int slot, int x, int y) {
    super(container, slot, x, y);
  }

  @Override
  public boolean isFake() {
    return true;
  }

  @Override
  public int getMaxStackSize() {
    return GHOST_SLOT_MAX_STACK_SIZE;
  }

  @Override
  public int getMaxStackSize(ItemStack itemStack) {
    return GHOST_SLOT_MAX_STACK_SIZE;
  }

  // #############################################
  //          OVERRIDE MOVING BEHAVIOR
  // #############################################

  @Override
  public ItemStack safeTake(int pCount, int pDecrement, Player pPlayer) {
    Optional<ItemStack> optional = this.tryRemove(pCount, pDecrement, pPlayer);
    optional.ifPresent(p_150655_ -> this.onTake(pPlayer, p_150655_));
    return optional.orElse(ItemStack.EMPTY);
  }

  @Override
  public ItemStack safeInsert(ItemStack pStack) {
    return this.safeInsert(pStack, 1);
  }

  @Override
  public ItemStack safeInsert(ItemStack pStack, int pIncrement) {
    if (!pStack.isEmpty() && this.mayPlace(pStack)) {
        ItemStack itemstack = this.getItem();
        if (itemstack.isEmpty()) {
            this.setByPlayer(pStack);
        } else if (ItemStack.isSameItemSameComponents(itemstack, pStack)) {
            this.setByPlayer(itemstack);
        }

        return pStack;
    } else {
        return pStack;
    }
  }

  @Override
  public Optional<ItemStack> tryRemove(int count, int decrement, Player player) {
    if (!this.mayPickup(player)) {
        return Optional.empty();
    } else if (!this.allowModification(player) && decrement < this.getItem().getCount()) {
        return Optional.empty();
    } else {
        ItemStack itemstack = this.remove(1);
        
        if (itemstack.isEmpty()) {
          return Optional.empty();
        } else {
          if (this.getItem().isEmpty()) {
            this.setByPlayer(ItemStack.EMPTY, itemstack);
          }

          return Optional.empty();
        }
    }
  }
}
