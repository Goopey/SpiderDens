package com.goopey.spiderdens.world.screens.menu;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class GhostSlot extends Slot {
  public GhostSlot(Container pContainer, int pSlot, int pX, int pY) {
    super(pContainer, pSlot, pX, pY);
  }

  @Override
  public boolean isFake() {
    return true;
  }
}
