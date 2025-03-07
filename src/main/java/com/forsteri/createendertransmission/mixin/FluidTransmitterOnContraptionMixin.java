package com.forsteri.createendertransmission.mixin;

import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorage;
import com.forsteri.createendertransmission.blocks.fluidTrasmitter.FluidTransmitterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MountedFluidStorage.class, remap = false)
public class FluidTransmitterOnContraptionMixin {
    @Inject(method = "canUseAsStorage", at = @At("HEAD"), cancellable = true)
    private static void canUseAsStorage(BlockEntity be, CallbackInfoReturnable<Boolean> cir) {
        if (be instanceof FluidTransmitterBlockEntity)
            cir.setReturnValue(true);
    }
}
