package com.goopey.spiderdens.block;

import javax.annotation.Nonnull;

import com.goopey.spiderdens.block.entity.DenBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class DenBlock extends BaseEntityBlock {   
   public static final MapCodec<DenBlock> CODEC = simpleCodec(DenBlock::new);   
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final DirectionProperty FACING_CEILING = BlockStateProperties.VERTICAL_DIRECTION;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

   public DenBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(FACING_CEILING, Direction.DOWN).setValue(AGE, 0).setValue(WATERLOGGED, false));
   }

   @Override
	protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED, FACING, FACING_CEILING, AGE);
	}

	@Override
	public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(AGE, 0).setValue(WATERLOGGED, flag);
	}

   @Override
   public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
      return new DenBlockEntity(pPos, pState);
   }

   @Override
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
