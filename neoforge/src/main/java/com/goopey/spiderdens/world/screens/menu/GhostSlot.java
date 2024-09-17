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
  public ItemStack safeTake(int count, int decrement, Player player) {
    Optional<ItemStack> optional = this.tryRemove(count, decrement, player);
    optional.ifPresent(itemStack -> this.onTake(player, itemStack));
    return optional.orElse(ItemStack.EMPTY);
  }

  @Override
  public ItemStack safeInsert(ItemStack pStack) {
    return this.safeInsert(pStack, 1);
  }

  @Override
  public ItemStack safeInsert(ItemStack stack, int increment) {
    if (!stack.isEmpty() && this.mayPlace(stack)) {
        ItemStack itemstack = this.getItem();
        if (itemstack.isEmpty()) {
            this.setByPlayer(stack.copyWithCount(1));
        } else if (ItemStack.isSameItemSameComponents(itemstack, stack)) {
            this.setChanged();
        }

        return stack;
    } else {
        return stack;
    }
  }

  @Override
  public Optional<ItemStack> tryRemove(int count, int decrement, Player player) {
    ItemStack itemStack = this.remove(1);
    this.setByPlayer(ItemStack.EMPTY, itemStack);
    return Optional.of(ItemStack.EMPTY);
  }
}
