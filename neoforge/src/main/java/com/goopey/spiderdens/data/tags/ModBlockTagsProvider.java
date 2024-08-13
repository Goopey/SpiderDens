package com.goopey.spiderdens.data.tags;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;
import com.goopey.spiderdens.core.init.TagsInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
   public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SpiderDens.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ores
        // tag(TagsInit.BlockTagsInit.ORES_SPIDERDENS);

        // // deepslate ores
        // tag(TagsInit.BlockTagsInit.DEEPSLATE_ORES_SPIDERDENS);

        // // storage blocks
        // tag(TagsInit.BlockTagsInit.STORAGE_BLOCKS_SPIDERDENS);

        TagKey<Block> goldTags = createForgeTag("forge:needs_gold_tool");
        TagKey<Block> netheriteTags = createForgeTag("forge:needs_netherite_tool");
        TagKey<Block> woodTags = createForgeTag("forge:needs_wood_tool");

        tag(woodTags)
          .add(BlockInit.EXAMPLE_BLOCK.get());

        /// tags allowing the mining of the ores
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
          .add(BlockInit.EXAMPLE_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL);
    }

    private static TagKey<Block> createForgeTag(String name) {
        return BlockTags.create(new ResourceLocation(name));
    }
}
