package com.goopey.spiderdens.world.blockentity;

import com.goopey.spiderdens.core.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FilterHopperTileEntity extends BlockEntity implements Nameable {
  public FilterHopperTileEntity(BlockPos pos, BlockState blockState) {
    super(BlockEntityInit.FILTER_HOPPER_TILEENTITY.get(), pos, blockState);
  }

  @Override
  public Component getName() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getName'");
  }
}
