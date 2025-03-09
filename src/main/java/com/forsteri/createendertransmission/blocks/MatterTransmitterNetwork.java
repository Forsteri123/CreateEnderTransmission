package com.forsteri.createendertransmission.blocks;

//import com.forsteri.createendertransmission.blocks.fluidTrasmitter.SerializableSmartFluidTank;
import com.forsteri.createendertransmission.CreateEnderTransmission;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum MatterTransmitterNetwork {
    ITEM(() -> new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            CreateEnderTransmission.savedData.setDirty();
        }
    }),
//    FLUID(() -> new SerializableSmartFluidTank(1000, contents -> CreateEnderTransmission.savedData.setDirty()))
    ;

    public final List<Map<String, INBTSerializable<CompoundTag>>> channels;

    public final Supplier<INBTSerializable<CompoundTag>> defaultInv;

    MatterTransmitterNetwork(Supplier<INBTSerializable<CompoundTag>> defaultInv){
        this.channels = new ArrayList<>();
        this.defaultInv = defaultInv;
        for (int i = 0; i < 10; i++) {
            channels.add(new HashMap<>());
        }
    }
}
