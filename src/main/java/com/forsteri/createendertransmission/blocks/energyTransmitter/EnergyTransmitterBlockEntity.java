package com.forsteri.createendertransmission.blocks.energyTransmitter;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.forsteri.createendertransmission.transmitUtil.ITransmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EnergyTransmitterBlockEntity extends KineticBlockEntity implements ITransmitter {
    public EnergyTransmitterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public boolean isCustomConnection(KineticBlockEntity other, BlockState state, BlockState otherState) {
        if (other instanceof EnergyTransmitterBlockEntity otherTransmitter) {
            return otherTransmitter.getChannel() == getChannel() &&
                    otherTransmitter.getLevel() == getLevel() &&
                    otherTransmitter.getPassword().equals(getPassword());
        }
        return false;
    }

    @Override
    public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
        var checks = new ArrayList<>(getConnectedTransmitters());
        for (KineticBlockEntity tile : checks) {
            if (tile.getBlockPos() != this.getBlockPos() && !neighbours.contains(tile.getBlockPos())) {
                neighbours.add(tile.getBlockPos());
            }
        }
        return super.addPropagationLocations(block, state, neighbours);
    }

    public List<KineticBlockEntity> getConnectedTransmitters() {
        Map<String, List<KineticBlockEntity>> channel = EnergyNetwork.ENERGY.channels.get(getChannel());

        channel.values().removeIf(List::isEmpty);

        channel.forEach((password, transmitters) -> transmitters.removeIf(blockEntity ->
                !(blockEntity instanceof EnergyTransmitterBlockEntity transmitter)
                        ||
                        transmitter.getChannel() != getChannel() || !transmitter.getPassword().equals(password)
        ));

        channel.values().forEach(list -> list.removeIf(BlockEntity::isRemoved));

        if (channel.containsKey(getPassword())) {
            List<KineticBlockEntity> list = channel.get(getPassword());

            if (!list.contains(this))
                list.add(this);
            return list;
        }

        ArrayList<KineticBlockEntity> list = new ArrayList<>(List.of(this));

        channel.put(this.getPassword(), list);

        return list;
    }

    @Override
    public void reloadSettings() {
        getConnectedTransmitters().remove(this);

        if (level == null) return;

//        detachKinetics();

        if (hasNetwork())
            getOrCreateNetwork().remove(this);
        detachKinetics();
        removeSource();
    }

    @Override
    public void afterReload() {
        if (level == null) return;

//        if (hasNetwork())
//            getOrCreateNetwork().remove(this);
//        detachKinetics();
//        removeSource();
        attachKinetics();
    }

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
        return (target.getPersistentData().getInt("channel") == getChannel()
                && target.getPersistentData().getString("password").equals(getPassword())
                && target.getBlockState().getBlock() instanceof EnergyTransmitterBlock) ? 1f : 0f;
    }

}
