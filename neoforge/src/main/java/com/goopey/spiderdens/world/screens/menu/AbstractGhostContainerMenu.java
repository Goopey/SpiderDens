package com.goopey.spiderdens.world.screens.menu;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;
import com.goopey.spiderdens.SpiderDens;

import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ClickType;
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
