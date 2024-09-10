package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.world.blockentity.FilterHopperBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SpiderDens.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FilterHopperBlockEntity>> FILTER_HOPPER_TILEENTITY = 
        BLOCK_ENTITY.register("filter_hopper", () -> BlockEntityType.Builder.of(FilterHopperBlockEntity::new, BlockInit.FILTER_HOPPER.get()).build(null));

}
