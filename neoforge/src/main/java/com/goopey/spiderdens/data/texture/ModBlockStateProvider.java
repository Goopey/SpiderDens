package com.goopey.spiderdens.data.texture;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.init.BlockInit;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SpiderDens.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        addHopper((HopperBlock) BlockInit.FILTER_HOPPER.value());
    }

    protected void addBlock(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        this.simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        this.simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }

    /**
     * 
     * @param block the HopperBlock 
     */
    protected void addHopper(HopperBlock block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        String blockPath = "block/" + path;
        String itemPath = "item/" + path;

        Function<BlockState, ConfiguredModel[]> function = state -> hopperStates(state, block, path, blockPath);

        this.getVariantBuilder(block).forAllStates(function);
        this.simpleBlockItem(block, 
            this.models()
                .getBuilder(itemPath)
                .parent(this.models().getExistingFile(mcLoc("item/generated")))
                .texture("layer0", itemPath)
        );
    }

    private ConfiguredModel[] hopperStates(BlockState state, HopperBlock block, String path, String blockPath) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        String sidePath = path + "_side";
        // save clutter by making the models here.
        ModelFile baseModel = this.models()
            .withExistingParent(path, "hopper")
            .texture("particle", modLoc(blockPath + "_outside"))
            .texture("top", modLoc(blockPath + "_top"))
            .texture("side", modLoc(blockPath + "_outside"))
            .texture("inside", modLoc(blockPath + "_inside"));
        ModelFile sideModel = this.models()
            .withExistingParent(sidePath, "hopper_side")
            .texture("particle", modLoc(blockPath + "_outside"))
            .texture("top", modLoc(blockPath + "_top"))
            .texture("side", modLoc(blockPath + "_outside"))
            .texture("inside", modLoc(blockPath + "_inside"));

        // if the state is down, return the down-facing hopper model
        if (state.getValue(HopperBlock.FACING) == Direction.DOWN) {
            models[0] = new ConfiguredModel(baseModel);

            return models;
        } else {
            // otherwise, return the side-facing model
            models[0] = new ConfiguredModel(
                sideModel, 
                0, 
                getFacingInt(state.getValue(HopperBlock.FACING)) * 90, 
                false
            );

            return models;
        }
    }

    private int getFacingInt(Direction i) {
        switch (i.ordinal()) {
            case 1:
               return 0;
            case 2:
               return 4;
            case 3:
               return 2;
            case 4:
               return 3;
            case 5:
               return 1;
            default:
               return 0;
         }
    }
}