package com.forsteri.createendertransmission.transmitUtil;

import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;

public interface ITransmitter extends IBlockEntityExtension {
    default void reloadSettings() {
    }

    default void afterReload() {
    }

    default int getChannel() {
        return getPersistentData().getInt("channel");
    }

    default String getPassword() {
        return getPersistentData().getString("password");
    }
}
