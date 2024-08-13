package com.goopey.spiderdens.data.recipe.provider;

import com.goopey.spiderdens.core.init.BlockInit;
import com.goopey.spiderdens.core.init.ItemInit;
import com.goopey.spiderdens.data.recipe.MainModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CraftingTableRecipeProvider extends MainModRecipeProvider {
    private final RecipeOutput output;

    public CraftingTableRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> providerRegistry, RecipeOutput output) {
        super(generator, providerRegistry);
        this.output = output;
    }

    public void build() {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, BlockInit.EXAMPLE_BLOCK.get(), 1)
                .requires(ItemInit.EXAMPLE_ITEM.get(), 9)
                .unlockedBy("has_item", has(ItemInit.EXAMPLE_ITEM.get()))
                .save(output, getModId("example_item_block_recipe"));
    }
}