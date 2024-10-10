package com.goopey.spiderdens.data.texture;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.ItemInit;
import com.goopey.spiderdens.util.NameUtility;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemStateProvider extends ItemModelProvider {
  public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, SpiderDens.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    itemBuilder(ItemInit.EXAMPLE_ITEM.get());
    eggItemBuilder(ItemInit.RED_SPIDER_EGG.get());
  }

  private void itemBuilder(Item item) {
    String name = NameUtility.getItemName(item);
    getBuilder(name)
      .parent(getExistingFile(mcLoc("item/generated")))
      .texture("layer0", "item/" + name);
  }

  private void eggItemBuilder(DeferredSpawnEggItem eggItem) {
    String name = NameUtility.getItemName(eggItem);
    getBuilder(name)
      .parent(getExistingFile(mcLoc("item/template_spawn_egg")));
  }
}
