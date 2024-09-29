package com.goopey.spiderdens.screens;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.screens.menu.FilterHopperMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FilterHopperScreen extends AbstractContainerScreen<FilterHopperMenu> {
  private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(SpiderDens.MOD_ID, "textures/gui/container/filter_screen.png");

  public FilterHopperScreen(FilterHopperMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
		this.imageHeight = 185;
		this.imageWidth = 176;
		this.inventoryLabelY = this.imageHeight - 93;
  }

  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
  }
 
  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
    int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		guiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
  }
}
