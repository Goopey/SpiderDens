package com.goopey.spiderdens.world.screens.menu;

import com.goopey.spiderdens.core.init.MenuInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilterHopperMenu extends AbstractContainerMenu {
   public static final int CONTAINER_SIZE = 5;
   private final Container hopper;

   public FilterHopperMenu(int containerId, Inventory playerInventory, final FriendlyByteBuf data) {
      this(containerId, playerInventory, new SimpleContainer(5));
   }

   public FilterHopperMenu(int containerId, Inventory playerInventory, Container container) {
      super(MenuInit.FILTER_HOPPER_MENU.get(), containerId);
      this.hopper = container;
      checkContainerSize(container, 5);
      container.startOpen(playerInventory.player);

      int i1;
      for(i1 = 0; i1 < 5; ++i1) {
         this.addSlot(new Slot(container, i1, 44 + i1 * 18, 20));
      }

      for(i1 = 0; i1 < 3; ++i1) {
         for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k + i1 * 9 + 9, 8 + k * 18, i1 * 18 + 51));
         }
      }

      for(i1 = 0; i1 < 9; ++i1) {
         this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 109));
      }

   }

   public boolean stillValid(Player pPlayer) {
      return this.hopper.stillValid(pPlayer);
   }

   public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(pIndex);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (pIndex < this.hopper.getContainerSize()) {
            if (!this.moveItemStackTo(itemstack1, this.hopper.getContainerSize(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, this.hopper.getContainerSize(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   public void removed(Player pPlayer) {
      super.removed(pPlayer);
      this.hopper.stopOpen(pPlayer);
   }
}
