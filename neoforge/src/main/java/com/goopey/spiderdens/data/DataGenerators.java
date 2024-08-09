package com.goopey.spiderdens.data;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.data.lang.ModENLangProvider;
import com.goopey.spiderdens.data.texture.ModItemStateProvider;

import net.minecraft.data.PackOutput;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
  
  public static void gatherData(GatherDataEvent event) {
    try {
      DataGenerator generator = event.getGenerator();
      PackOutput output = event.getGenerator().getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

      generator.addProvider(true, new ModENLangProvider(output));
      generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
    } catch(RuntimeException e) {
      SpiderDens.LOGGER.error("Failed to generate data", e);
    }
  }
}
