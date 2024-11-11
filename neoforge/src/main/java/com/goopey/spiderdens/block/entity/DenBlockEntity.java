package com.goopey.spiderdens.block.entity;

import com.goopey.spiderdens.block.DenBlock;
import com.goopey.spiderdens.core.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DenBlockEntity extends BlockEntity {
   // This stuff is used, but only by the parent class, so we need to keep it.
   @SuppressWarnings("unused")
   private long tickedGameTime;
   private Direction facing;
   private Direction facingCeiling;
   private int age;

   public DenBlockEntity(BlockPos pos, BlockState blockState) {
      super(BlockEntityInit.FILTER_HOPPER_TILEENTITY.get(), pos, blockState);
      this.facing = (Direction)blockState.getValue(DenBlock.FACING);
      this.facingCeiling = (Direction)blockState.getValue(DenBlock.FACING_CEILING);
      this.age = (int)blockState.getValue(DenBlock.AGE);
   }

   // protected void loadAdditional(CompoundTag tag, HolderLookup.Provider regisitries) {
   //    super.loadAdditional(tag, regisitries);
   //    this.items = NonNullList.withSize(HOPPER_CONTAINER_SIZE + HOPPER_FILTER_SIZE, ItemStack.EMPTY);
   //    if (!this.tryLoadLootTable(tag)) {
   //       ContainerHelper.loadAllItems(tag, this.items, regisitries);
   //    }

   //    this.cooldownTime = tag.getInt("TransferCooldown");
   // }

   // protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
   //    super.saveAdditional(tag, registries);
   //    if (!this.trySaveLootTable(tag)) {
   //       ContainerHelper.saveAllItems(tag, this.items, registries);
   //    }

   //    tag.putInt("TransferCooldown", this.cooldownTime);
   // }

  //#################################################
  //                 BLOCK ENTITY
  //#################################################

   @Override
   public BlockEntityType<?> getType() {
      return BlockEntityInit.FILTER_HOPPER_TILEENTITY.get();
   }
}
