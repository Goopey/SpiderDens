package com.goopey.spiderdens.data.lang;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;
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
  }

  private void items() {
    addItem(ItemInit.EXAMPLE_ITEM, "Example Item");
  }

  private void blocks() {
    addBlock(BlockInit.EXAMPLE_BLOCK, "Example Block");
  }
}
