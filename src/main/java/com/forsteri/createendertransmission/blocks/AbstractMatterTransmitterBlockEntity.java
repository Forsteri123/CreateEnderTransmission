package com.forsteri.createendertransmission.blocks;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.forsteri.createendertransmission.CreateEnderTransmission;
import com.forsteri.createendertransmission.transmitUtil.ITransmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractMatterTransmitterBlockEntity extends KineticBlockEntity implements ITransmitter {
    public LazyOptional<?> capability;
    public AbstractMatterTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        capability = LazyOptional.of(() -> getCapability().apply(this::getInv));
    }

    protected abstract MatterTransmitterNetwork getNetwork();

    protected abstract Function<Supplier<INBTSerializable<CompoundTag>>, ?> getCapability();

    public INBTSerializable<CompoundTag> getInv(){
        Map<String, INBTSerializable<CompoundTag>> channel = getNetwork().channels.get(getChannel());

        if (channel.containsKey(getPassword()))
            return channel.get(getPassword());

        INBTSerializable<CompoundTag> inv = getNetwork().defaultInv.get();
        channel.put(getPassword(), inv);

        if (CreateEnderTransmission.savedData != null) {
            CreateEnderTransmission.savedData.setDirty();
        }

        return inv;
    }

    protected abstract Predicate<Capability<?>> getMatterCapPredicate();

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (getMatterCapPredicate().test(cap)) return capability.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void reloadSettings() {
        ITransmitter.super.reloadSettings();
    }
}
