package com.forsteri.createendertransmission.blocks.fluidTrasmitter;

import com.forsteri.createendertransmission.blocks.energyTransmitter.EnergyTransmitterBlockEntity;
import com.forsteri.createendertransmission.entry.TransmissionBlockEntities;
import com.forsteri.createendertransmission.entry.TransmissionBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.forsteri.createendertransmission.transmitUtil.TransmitterScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FluidTransmitterBlock extends Block implements IBE<FluidTransmitterBlockEntity>, IWrenchable {

    public FluidTransmitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (AllItems.WRENCH.isIn(stack))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (FMLLoader.getDist() == net.neoforged.api.distmarker.Dist.CLIENT) {
            withBlockEntityDo(level, pos, te -> this.displayScreen(te, player));
        }

        return ItemInteractionResult.SUCCESS;
    }

    @OnlyIn(value = net.neoforged.api.distmarker.Dist.CLIENT)
    protected void displayScreen(FluidTransmitterBlockEntity te, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener.open(new TransmitterScreen(te, TransmissionBlocks.FLUID_TRANSMITTER_BLOCK.asStack()));
    }

    @Override
    public Class<FluidTransmitterBlockEntity> getBlockEntityClass() {
        return FluidTransmitterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FluidTransmitterBlockEntity> getBlockEntityType() {
        return TransmissionBlockEntities.FLUID_TRANSMITTER_TILE_ENTITY.get();
    }
}
