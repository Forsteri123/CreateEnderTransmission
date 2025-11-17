package com.forsteri.createendertransmission.blocks.itemTransmitter;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ItemTransmitterInventoryHandler extends ItemStackHandler {
    protected final Supplier<INBTSerializable<CompoundTag>> superWrapper;

    public ItemTransmitterInventoryHandler(Supplier<INBTSerializable<CompoundTag>> superWrapper) {
        super();
        this.superWrapper = superWrapper;
    }

    public ItemStackHandler superWrapper() {
        return ((ItemStackHandler) superWrapper.get());
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return superWrapper().insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return superWrapper().extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlots() {
        return superWrapper().getSlots();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot)
    {
        return superWrapper().getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        superWrapper().setStackInSlot(slot, stack);
    }

    @Override
    public void setSize(int size) {
        superWrapper().setSize(size);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return superWrapper().isItemValid(slot, stack);
    }

    @Override
    public @NotNull CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return superWrapper().serializeNBT(provider);
    }


    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        superWrapper().deserializeNBT(provider, nbt);
    }

    @Override
    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= superWrapper().getSlots())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
    }
}
