package com.forsteri.createendertransmission.blocks.fluidTrasmitter;

import com.forsteri.createendertransmission.blocks.AbstractMatterTransmitterBlockEntity;
import com.forsteri.createendertransmission.blocks.MatterTransmitterNetwork;
import com.forsteri.createendertransmission.transmitUtil.ITransmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FluidTransmitterBlockEntity extends AbstractMatterTransmitterBlockEntity<IFluidHandler> implements ITransmitter {
    public FluidTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected MatterTransmitterNetwork getNetwork() {
        return MatterTransmitterNetwork.FLUID;
    }

    @Override
    protected Function<Supplier<INBTSerializable<CompoundTag>>, IFluidHandler> getCapability() {
        return FluidTransmitterInventoryHandler::new;
    }
}
