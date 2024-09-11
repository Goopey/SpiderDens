package com.goopey.spiderdens.world.block;

import com.goopey.spiderdens.core.init.BlockEntityInit;
import com.goopey.spiderdens.world.block.entity.FilterHopperBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FilterHopper extends HopperBlock {
   public FilterHopper(Properties properties) {
      super(properties);
   }

   @Override
   protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player player, BlockHitResult pHitResult) {
      if (pLevel.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = pLevel.getBlockEntity(pPos);
         if (blockentity instanceof FilterHopperBlockEntity) {
            player.openMenu((FilterHopperBlockEntity)blockentity);
            player.awardStat(Stats.INSPECT_HOPPER);
         }

         return InteractionResult.CONSUME;
      }
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
      return new FilterHopperBlockEntity(pPos, pState);
   }

   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> blockEntityType) {
      return pLevel.isClientSide ? null : createTickerHelper(blockEntityType, BlockEntityInit.FILTER_HOPPER_TILEENTITY.get(), FilterHopperBlockEntity::filterPushItemsTick);
   }

   @Override
   protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
      BlockEntity blockentity = pLevel.getBlockEntity(pPos);
      if (blockentity instanceof FilterHopperBlockEntity) {
         FilterHopperBlockEntity.filterEntityInside(pLevel, pPos, pState, pEntity, (FilterHopperBlockEntity)blockentity);
      }
   }
}
