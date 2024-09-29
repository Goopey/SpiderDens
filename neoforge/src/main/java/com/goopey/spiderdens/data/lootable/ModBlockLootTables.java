package com.goopey.spiderdens.data.lootable;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;

public class ModBlockLootTables extends BlockLootSubProvider {
  public ModBlockLootTables() {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags());
  }

  @Override
  protected void generate() {
    dropSelf(BlockInit.FILTER_HOPPER.get());
  }

  @Override
  public @NotNull Iterable<Block> getKnownBlocks() {
    return BuiltInRegistries.BLOCK.stream()
            .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                    .filter(key -> key.getNamespace().equals(SpiderDens.MOD_ID))
                    .isPresent())
            .collect(Collectors.toSet());
  }
}