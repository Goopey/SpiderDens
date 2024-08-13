package com.goopey.spiderdens.data;

import java.util.concurrent.CompletableFuture;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.data.lang.ModENLangProvider;
import com.goopey.spiderdens.data.lootable.ModLootTables;
import com.goopey.spiderdens.data.recipe.MainModRecipeProvider;
import com.goopey.spiderdens.data.tags.ModBlockTagsProvider;
import com.goopey.spiderdens.data.tags.ModItemTagsProvider;
import com.goopey.spiderdens.data.texture.ModBlockStateProvider;
import com.goopey.spiderdens.data.texture.ModItemStateProvider;

import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
  
  public static void gatherData(GatherDataEvent event) {
    try {
      DataGenerator generator = event.getGenerator();
      PackOutput output = event.getGenerator().getPackOutput();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

      generator.addProvider(true, new ModENLangProvider(output));
      generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
      generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
      generator.addProvider(true, new ModLootTables(output, lookupProvider));
      generator.addProvider(true, new MainModRecipeProvider(generator, lookupProvider));

      ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, event.getLookupProvider(), existingFileHelper);
      generator.addProvider(true, blockTagsProvider);
      generator.addProvider(true, new ModItemTagsProvider(output, event.getLookupProvider(), blockTagsProvider, existingFileHelper));
            
    } catch(RuntimeException e) {
      SpiderDens.LOGGER.error("Failed to generate data", e);
    }
  }
}
