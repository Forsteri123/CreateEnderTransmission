package com.forsteri.createendertransmission.blocks.chunkLoader;

import com.forsteri.createendertransmission.entry.TransmissionBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LoaderBlock extends KineticBlock implements IBE<LoaderBlockEntity>, IWrenchable {
    public LoaderBlock(Properties properties) {
        super(properties);
    }
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving){
        super.onRemove(state, worldIn, pos, newState, isMoving);

        if (worldIn.isClientSide) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) worldIn;

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                serverLevel.setChunkForced(
                        new ChunkPos(pos).x + i,
                        new ChunkPos(pos).z + j,
                        false
                );
            }
        }
    }
    @Override
    public Class<LoaderBlockEntity> getBlockEntityClass() {
        return LoaderBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LoaderBlockEntity> getBlockEntityType() {
        return TransmissionBlockEntities.CHUNK_LOADER_TILE.get();
    }
}
