package com.goopey.spiderdens.world.screens;

import com.goopey.spiderdens.world.screens.menu.FilterHopperMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FilterHopperScreen extends AbstractContainerScreen<FilterHopperMenu> {
  private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
	private final int containerRows;

  public FilterHopperScreen(FilterHopperMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
		int i = 222;
		int j = 114;
		this.containerRows = 2;
		this.imageHeight = j + this.containerRows * 18;
		this.inventoryLabelY = this.imageHeight - 94;
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
		guiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
		guiGraphics.blit(CONTAINER_BACKGROUND, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
  }
}
