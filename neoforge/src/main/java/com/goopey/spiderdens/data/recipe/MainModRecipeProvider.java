package com.goopey.spiderdens.data.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.data.recipe.provider.CraftingTableRecipeProvider;
import com.goopey.spiderdens.data.recipe.provider.FurnaceRecipeProvider;

public class MainModRecipeProvider extends RecipeProvider {
    protected final DataGenerator generator;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public MainModRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> providerRegistry) {
        super(generator.getPackOutput(), providerRegistry);
        this.generator = generator;
        this.registries = providerRegistry;
    }


    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        new CraftingTableRecipeProvider(generator, registries, recipeOutput).build();
        new FurnaceRecipeProvider(generator, registries, recipeOutput).build();
    }

    public ResourceLocation getModId(String path) {
        return ResourceLocation.fromNamespaceAndPath(SpiderDens.MOD_ID, path);
    }
}