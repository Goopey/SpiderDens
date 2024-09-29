package com.goopey.spiderdens.data.recipe.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

import com.goopey.spiderdens.core.init.ItemInit;
import com.goopey.spiderdens.data.recipe.MainModRecipeProvider;

public class FurnaceRecipeProvider extends MainModRecipeProvider {
    private final RecipeOutput output;

    public FurnaceRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> providerRegistry, RecipeOutput output) {
        super(generator, providerRegistry);
        this.output = output;
    }

    public void build() {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.EXAMPLE_ITEM.get()), RecipeCategory.MISC, ItemInit.EXAMPLE_ITEM.get(), 0.6f, 300)
                .unlockedBy("has_item", has(ItemInit.EXAMPLE_ITEM.get()))
                .save(output, getModId("raw_example_smelting"));
    }
}