package com.goopey.spiderdens.world.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FilterHopper extends HopperBlock {
  public FilterHopper(Properties properties) {
    super(properties);
  }

  // @Override
  // protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
  //     if (pLevel.isClientSide) {
  //        return InteractionResult.SUCCESS;
  //     } else {
  //        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
  //        if (blockentity instanceof HopperBlockEntity) {
  //           pPlayer.openMenu((HopperBlockEntity)blockentity);
  //           pPlayer.awardStat(Stats.INSPECT_HOPPER);
  //        }

  //        return InteractionResult.CONSUME;
  //     }
  //  }

  // @Override
  // public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
  //     return new HopperBlockEntity(pPos, pState);
  //  }

  //  @Override
  //  @Nullable
  //  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
  //     return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, BlockEntityType.HOPPER, HopperBlockEntity::pushItemsTick);
  //  }
}
