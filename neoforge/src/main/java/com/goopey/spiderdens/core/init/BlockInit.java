package com.goopey.spiderdens.core.init;

import java.util.function.Supplier;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockInit {
  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SpiderDens.MOD_ID);

  public static final DeferredBlock<Block> EXAMPLE_BLOCK = registerBlock("example_block", () -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

  // public static final DeferredBlock<Block> 

  /**
   * Used to register a Block and its BlockItem counterpart
   * 
   * @param name the name of the block
   * @param block the block it's gonna be
   */
  public static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block) {
    DeferredBlock<Block> blockReg = BLOCKS.register(name, block);
    ItemInit.ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));

    return blockReg;
  }
}