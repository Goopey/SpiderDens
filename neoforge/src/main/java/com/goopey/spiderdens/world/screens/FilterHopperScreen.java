package com.goopey.spiderdens.world.screens;

import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HopperMenu;

public class FilterHopperScreen extends HopperScreen {
  public FilterHopperScreen(HopperMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
  }
}
