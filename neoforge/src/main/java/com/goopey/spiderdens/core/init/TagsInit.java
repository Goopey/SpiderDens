package com.goopey.spiderdens.core.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.goopey.spiderdens.SpiderDens;

public class TagsInit {

  public static class BlockTagsInit {
      // // Ores
      // public static TagKey<Block> ORES_SPIDERDENS = BlockTags.create(createResourceLocation("ores/example_ore"));
      
      // // Deepslate ores
      // public static TagKey<Block> DEEPSLATE_ORES_SPIDERDENS = BlockTags.create(createResourceLocation("deepslate_ores/example_block"));

      // // For normal blocks - storage
      public static TagKey<Block> STORAGE_BLOCKS_SPIDERDENS = BlockTags.create(createResourceLocation("storage_blocks/storage_blocks"));

      // public static TagKey<Block> NEEDS_EXAMPLE = BlockTags.create(createCustomResourceLocation("needs_example"));


      private static ResourceLocation createResourceLocation(String name) {
          return ResourceLocation.fromNamespaceAndPath("forge", name);
      }

      private static ResourceLocation createCustomResourceLocation(String name) {
          return ResourceLocation.fromNamespaceAndPath(SpiderDens.MOD_ID, name);
      }
  }

  public static class ItemTagsInit {
      // // Ores
      // public static TagKey<Item> ORES_EXAMPLE = ItemTags.create(createResourceLocation("ores/example_ore"));

      // // Deepslate ores
      // public static TagKey<Item> DEEPSLATE_ORES_EXAMPLE = ItemTags.create(createResourceLocation("deepslate_ores/example_ore"));

      // // For normal blocks - storage
      public static TagKey<Item> STORAGE_BLOCKS_SPIDERDENS = ItemTags.create(createResourceLocation("storage_blocks/storage_blocks"));

      // Items

      // // ore ingot
      // public static TagKey<Item> INGOTS_EXAMPLE = ItemTags.create(createResourceLocation("ingots/example"));

      // // Raw ore scrap
      // public static TagKey<Item> SCRAPS_EXAMPLE = ItemTags.create(createResourceLocation("scraps/example"));


      private static ResourceLocation createResourceLocation(String name) {
          return ResourceLocation.fromNamespaceAndPath("forge", name);
      }
  }
}