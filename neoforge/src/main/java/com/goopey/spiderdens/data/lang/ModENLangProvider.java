package com.goopey.spiderdens.data.lang;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.ItemInit;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModENLangProvider extends LanguageProvider {
  public ModENLangProvider(PackOutput output) {
    super(output, SpiderDens.MODID, "en_us");
  }
  
  @Override
  protected void addTranslations() {
    addItem(ItemInit.EXAMPLE_ITEM, "Example Item");
  }
}
