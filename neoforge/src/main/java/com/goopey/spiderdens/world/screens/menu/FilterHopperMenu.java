package com.goopey.spiderdens.world.screens.menu;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.MenuInit;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilterHopperMenu extends AbstractContainerMenu {
   public static final int CONTAINER_SIZE = 5;
   public static final int FILTER_CONTAINER_SIZE = 27;
   private final Container hopper;

   public FilterHopperMenu(int containerId, Inventory playerInventory, final FriendlyByteBuf data) {
      this(containerId, playerInventory, new SimpleContainer(CONTAINER_SIZE + FILTER_CONTAINER_SIZE));
   }

   public FilterHopperMenu(int containerId, Inventory playerInventory, Container container) {
      super(MenuInit.FILTER_HOPPER_MENU.get(), containerId);
      this.hopper = container;
      checkContainerSize(container, CONTAINER_SIZE + FILTER_CONTAINER_SIZE);
      container.startOpen(playerInventory.player);

      // Hopper Inventory
      for(int i = 0; i < 5; ++i) {
         this.addSlot(new Slot(container, i, 44 + i * 18, 16));
      }

      // Hopper Filter Inventory
      for(int i = 0; i < 3; i++) {
         for(int k = 0; k < 9; k++) {
            this.addSlot(new GhostSlot(container, k + i * 9 + CONTAINER_SIZE, 8 + k * 18, i * 18 + 38));
         }
      }

      // Player Inventory
      for(int i = 0; i < 3; ++i) {
         for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k + i * 9 + 9, 8 + k * 18, i * 18 + 103));
         }
      }

      // Player Hotbar
      for(int i = 0; i < 9; ++i) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 161));
      }
   }

   // @Override
   // public void clicked(int slotId, int button, ClickType clickType, Player player) {
   //    try {
   //       if (this.slots.get(slotId) instanceof GhostSlot) {
   //          super.clicked(slotId, button, clickType, player);
   //       } else {
   //          super.clicked(slotId, button, clickType, player);
   //       }
   //    } catch (Exception exception) {
   //       CrashReport crashreport = CrashReport.forThrowable(exception, "Container click");
   //       CrashReportCategory crashreportcategory = crashreport.addCategory("Click info");
   //       crashreportcategory.setDetail("Menu Class", () -> this.getClass().getCanonicalName());
   //       crashreportcategory.setDetail("Slot Count", this.slots.size());
   //       crashreportcategory.setDetail("Slot", slotId);
   //       crashreportcategory.setDetail("Button", button);
   //       crashreportcategory.setDetail("Type", clickType);
   //       throw new ReportedException(crashreport);
   //    }
   // }

   public boolean stillValid(Player pPlayer) {
      return this.hopper.stillValid(pPlayer);
   }

   public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(pIndex);
      
      // Remove items from filter.
      if (slot instanceof GhostSlot) {
         slot.setByPlayer(ItemStack.EMPTY);
      // Do logic as per standard.
      } else if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (pIndex < CONTAINER_SIZE) {
            if (!this.moveItemStackTo(itemstack1, CONTAINER_SIZE, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, CONTAINER_SIZE, false)) {
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
