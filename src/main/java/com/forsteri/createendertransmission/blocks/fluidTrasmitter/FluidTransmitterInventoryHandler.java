package com.forsteri.createendertransmission.blocks.fluidTrasmitter;

import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import net.createmod.catnip.data.Iterate;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.EmptyFluidHandler;
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
            return (IFluidHandler) EmptyFluidHandler.INSTANCE;
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

        if (!drainedFromCurrent.isEmpty() && (drained.isEmpty() || FluidStack.isSameFluidSameComponents(drainedFromCurrent, drained)))
            drained = new FluidStack(drainedFromCurrent.getFluid().builtInRegistryHolder(), amount + drained.getAmount(),
                    drainedFromCurrent.getComponents().asPatch());

        return drained;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack drained = FluidStack.EMPTY;

        IFluidHandler iFluidHandler = superWrapper();
        FluidStack drainedFromCurrent = iFluidHandler.drain(maxDrain, action);
        int amount = drainedFromCurrent.getAmount();

        if (!drainedFromCurrent.isEmpty() && (drained.isEmpty() || FluidStack.isSameFluidSameComponents(drainedFromCurrent, drained)))
            drained = new FluidStack(drainedFromCurrent.getFluid().builtInRegistryHolder(), amount + drained.getAmount(),
                    drainedFromCurrent.getComponents().asPatch());

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
