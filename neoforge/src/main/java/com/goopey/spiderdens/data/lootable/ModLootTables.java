package com.goopey.spiderdens.data.lootable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTables extends LootTableProvider {
  public ModLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> holderLookup) {
      super(output, Set.of(), List.of(new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)), holderLookup);
  }
}
