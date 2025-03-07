package com.forsteri.createendertransmission.blocks.fluidTrasmitter;

import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import net.createmod.catnip.data.Iterate;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FluidTransmitterInventoryHandler extends CombinedTankWrapper {

    protected final Supplier<INBTSerializable<CompoundTag>> superWrapper;

    public FluidTransmitterInventoryHandler(Supplier<INBTSerializable<CompoundTag>> handlers) {
        super((IFluidHandler) handlers.get());
        superWrapper = handlers;
    }

    public IFluidHandler superWrapper() {
        return ((IFluidHandler) superWrapper.get());
    }

    protected IFluidHandler getHandlerFromIndex(int index) {
        if (index != 0)
            return (IFluidHandler) EmptyHandler.INSTANCE;
        return superWrapper();
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty())
            return resource;

        FluidStack drained = FluidStack.EMPTY;
        resource = resource.copy();

        IFluidHandler iFluidHandler = superWrapper();
        FluidStack drainedFromCurrent = iFluidHandler.drain(resource, action);
        int amount = drainedFromCurrent.getAmount();
        resource.shrink(amount);

        if (!drainedFromCurrent.isEmpty() && (drained.isEmpty() || drainedFromCurrent.isFluidEqual(drained)))
            drained = new FluidStack(drainedFromCurrent.getFluid(), amount + drained.getAmount(),
                    drainedFromCurrent.getTag());

        return drained;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack drained = FluidStack.EMPTY;

        IFluidHandler iFluidHandler = superWrapper();
        FluidStack drainedFromCurrent = iFluidHandler.drain(maxDrain, action);
        int amount = drainedFromCurrent.getAmount();

        if (!drainedFromCurrent.isEmpty() && (drained.isEmpty() || drainedFromCurrent.isFluidEqual(drained)))
            drained = new FluidStack(drainedFromCurrent.getFluid(), amount + drained.getAmount(),
                    drainedFromCurrent.getTag());

        return drained;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty())
            return 0;

        int filled = 0;
        resource = resource.copy();

        boolean fittingHandlerFound = false;
        for (boolean searchPass : Iterate.trueAndFalse) {
            IFluidHandler iFluidHandler = superWrapper();

            for (int i = 0; i < iFluidHandler.getTanks(); i++)
                if (searchPass && iFluidHandler.getFluidInTank(i)
                        .isFluidEqual(resource))
                    fittingHandlerFound = true;

            if (searchPass && !fittingHandlerFound)
                continue;

            int filledIntoCurrent = iFluidHandler.fill(resource, action);
            resource.shrink(filledIntoCurrent);
            filled += filledIntoCurrent;

            if (resource.isEmpty())
                break;
            if (fittingHandlerFound && (enforceVariety || filledIntoCurrent != 0))
                break;
        }

        return filled;
    }
}
