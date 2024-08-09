package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SpiderDens.MOD_ID);

  public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
}