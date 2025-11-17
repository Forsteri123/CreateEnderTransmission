package com.forsteri.createendertransmission.blocks.itemTransmitter;

import com.forsteri.createendertransmission.blocks.AbstractMatterTransmitterBlockEntity;
import com.forsteri.createendertransmission.blocks.MatterTransmitterNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemTransmitterBlockEntity extends AbstractMatterTransmitterBlockEntity<IItemHandler> {

    public ItemTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected MatterTransmitterNetwork getNetwork() {
        return MatterTransmitterNetwork.ITEM;
    }

    @Override
    protected Function<Supplier<INBTSerializable<CompoundTag>>, IItemHandler> getCapability() {
        return ItemTransmitterInventoryHandler::new;
    }
}
