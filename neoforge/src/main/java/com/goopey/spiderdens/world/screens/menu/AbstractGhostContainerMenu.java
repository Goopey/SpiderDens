package com.goopey.spiderdens.world.screens.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AbstractGhostContainerMenu extends AbstractContainerMenu {
  public AbstractGhostContainerMenu(MenuType<?> pMenuType, int pContainerId) {
    super(pMenuType, pContainerId);
  }

  //##################################################################
  //              DENY DRAG CLICKING IN GHOST SLOTS
  //##################################################################

  @Override
  public boolean canDragTo(Slot slot) {
    return !(slot instanceof GhostSlot);
  }

  //########################################
  //            OVERRIDE STUFF
  //########################################

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'quickMoveStack'");
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'stillValid'");
  }

}
