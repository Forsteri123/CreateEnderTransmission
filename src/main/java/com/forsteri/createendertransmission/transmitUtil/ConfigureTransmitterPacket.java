package com.forsteri.createendertransmission.transmitUtil;

import com.forsteri.createendertransmission.entry.TransmissionPackets;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import net.minecraft.server.level.ServerPlayer;

public class ConfigureTransmitterPacket extends BlockEntityConfigurationPacket<KineticBlockEntity> {

    private int channel;
    private String password;

    public ConfigureTransmitterPacket(BlockPos pos, int channel, String password) {
        super(pos);
        this.channel = channel;
        this.password = password;
    }

    public static final StreamCodec<ByteBuf, ConfigureTransmitterPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, packet -> packet.pos,
            ByteBufCodecs.VAR_INT, packet -> packet.channel,
            ByteBufCodecs.STRING_UTF8, packet -> packet.password,
            ConfigureTransmitterPacket::new
    );

    @Override
    protected void applySettings(ServerPlayer serverPlayer, KineticBlockEntity tileEntity) {
        if (
                tileEntity.getPersistentData().getInt("channel") != channel ||
                        !tileEntity.getPersistentData().getString("password").equals(password)
        ) {
            ((ITransmitter) tileEntity).reloadSettings();
            tileEntity.getPersistentData().putInt("channel", channel);
            tileEntity.getPersistentData().putString("password", password);
            tileEntity.detachKinetics();
            tileEntity.attachKinetics();
            ((ITransmitter) tileEntity).afterReload();
        }
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return TransmissionPackets.CONFIGURE_TRANSMITTER;
    }
}
