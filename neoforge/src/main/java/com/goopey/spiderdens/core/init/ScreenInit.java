package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.screens.FilterHopperScreen;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ScreenInit {
  /**
   * Used to register a Block and its BlockItem counterpart
   * 
   * @param name the name of the block
   * @param block the block it's gonna be
   */
  public static void registerScreens(RegisterMenuScreensEvent modEvent) {
    modEvent.register(MenuInit.FILTER_HOPPER_MENU.get(), FilterHopperScreen::new);
  }
}