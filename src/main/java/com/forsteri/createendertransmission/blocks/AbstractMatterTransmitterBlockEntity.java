package com.forsteri.createendertransmission.blocks;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.forsteri.createendertransmission.CreateEnderTransmission;
import com.forsteri.createendertransmission.transmitUtil.ITransmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractMatterTransmitterBlockEntity<T> extends KineticBlockEntity implements ITransmitter {
    public Supplier<T> capability;
    public AbstractMatterTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        capability = () -> getCapability().apply(this::getInv);
    }

    protected abstract MatterTransmitterNetwork getNetwork();

    protected abstract Function<Supplier<INBTSerializable<CompoundTag>>, T> getCapability();

    public INBTSerializable<CompoundTag> getInv(){
        Map<String, INBTSerializable<CompoundTag>> channel = getNetwork().channels.get(getChannel());

        if (channel.containsKey(getPassword()))
            return channel.get(getPassword());

        INBTSerializable<CompoundTag> inv = getNetwork().defaultInv.get();
        channel.put(getPassword(), inv);

        CreateEnderTransmission.savedData.setDirty();

        return inv;
    }


    public static <T> void registerCapabilities(BlockCapability<T, ?> capability, BlockEntityType<? extends AbstractMatterTransmitterBlockEntity<T>> entityType, RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(capability, entityType, (be, context) -> {
            return be.capability.get();
        });
    }

    @Override
    public void reloadSettings() {
        ITransmitter.super.reloadSettings();
    }
}
