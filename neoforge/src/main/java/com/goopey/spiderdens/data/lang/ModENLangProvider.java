package com.goopey.spiderdens.data.lang;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;
import com.goopey.spiderdens.core.init.CreativeModeTabInit;
import com.goopey.spiderdens.core.init.ItemInit;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModENLangProvider extends LanguageProvider {
  public ModENLangProvider(PackOutput output) {
    super(output, SpiderDens.MOD_ID, "en_us");
  }
  
  @Override
  protected void addTranslations() {
    blocks();
    items();
    creativeTabs();
  }

  private void items() {
    addItem(ItemInit.EXAMPLE_ITEM, "Example Item");
  }

  private void blocks() {
    addBlock(BlockInit.FILTER_HOPPER, "Filter Hopper");
  }

  private void creativeTabs() {
    add(CreativeModeTabInit.SPIDER_DENS_TAB_TITLE, "Tutorial Mod");    
  }
}
