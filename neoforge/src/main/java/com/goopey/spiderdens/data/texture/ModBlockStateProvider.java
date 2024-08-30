package com.goopey.spiderdens.data.texture;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;
import com.goopey.spiderdens.util.NameUtility;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SpiderDens.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        addHopper(BlockInit.FILTER_HOPPER.value());
    }

    protected void addBlock(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        this.simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        this.simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }

    protected void addHopper(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        String blockPath = "block/" + path;
        String itemPath = "item/" + path;
        this.simpleBlock(block, 
            this.models()
                .withExistingParent(path, "hopper")
                .texture("particle", modLoc(blockPath + "_outside"))
                .texture("top", modLoc(blockPath + "_top"))
                .texture("side", modLoc(blockPath + "_outside"))
                .texture("inside", modLoc(blockPath + "_inside"))
        );
        
        // simpleBlock(block, 
        //     models()
        //         .withExistingParent(path, "hopper_side")
        //         .texture("particle", modLoc(blockPath + "_outside"))
        //         .texture("top", modLoc(blockPath + "_top"))
        //         .texture("side", modLoc(blockPath + "_outside"))
        //         .texture("inside", modLoc(blockPath + "_inside"))
        // );
        
        simpleBlockItem(block, 
            this.models()
                .getBuilder(itemPath)
                .parent(this.models().getExistingFile(mcLoc("item/generated")))
                .texture("layer0", itemPath)
        );
    }
}