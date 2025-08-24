package com.forsteri.createendertransmission.entry;

import net.neoforged.neoforge.common.ModConfigSpec;

public class TransmissionConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue CHUNK_LOADER;

    static {
        BUILDER.push("Create Ender Transmission Config");
        CHUNK_LOADER = BUILDER.comment("Enable the chunk loader").define("chunkLoader", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
