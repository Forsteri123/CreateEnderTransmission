package com.forsteri.createendertransmission.blocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MatterWorldSavedData extends SavedData {
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider registries) {
        for (MatterTransmitterNetwork network : MatterTransmitterNetwork.values()) {
            CompoundTag networkTag = new CompoundTag();
            List<Map<String, INBTSerializable<CompoundTag>>> channels = network.channels;
            pTag.put(network.name().toLowerCase(), networkTag);
            for (int i = 0; i < 10; i++) {
                Map<String, INBTSerializable<CompoundTag>> channel = channels.get(i);

                CompoundTag channelTag = new CompoundTag();
                networkTag.put(String.valueOf(i), channelTag);

                for (Map.Entry<String, INBTSerializable<CompoundTag>> entry : channel.entrySet())
                    channelTag.put(entry.getKey(), entry.getValue().serializeNBT(registries));
            }
        }

        return pTag;
    }

    private static MatterWorldSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        for (MatterTransmitterNetwork network : MatterTransmitterNetwork.values()) {
            CompoundTag networkTag = tag.getCompound(network.name().toLowerCase());
            List<Map<String, INBTSerializable<CompoundTag>>> channels = network.channels;
            for (int i = 0; i < 10; i++) {
                Map<String, INBTSerializable<CompoundTag>> channel = channels.get(i);

                CompoundTag channelTag = networkTag.getCompound(String.valueOf(i));

                channelTag.getAllKeys().forEach(
                        key -> {
                            if (!(channelTag.get(key) instanceof CompoundTag compoundTag))
                                return;

                            if (!channel.containsKey(key)) {
                                channel.put(key, network.defaultInv.get());
                            }
                            channel.get(key).deserializeNBT(lookupProvider, compoundTag);
                        }
                );
            }
        }

        return new MatterWorldSavedData();
    }

    public static MatterWorldSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(new Factory<>(MatterWorldSavedData::new, MatterWorldSavedData::load), "ender_transmission_matter_transmission");
    }
}
