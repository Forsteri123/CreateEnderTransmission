package com.forsteri.createendertransmission.blocks.itemTransmitter;

import com.forsteri.createendertransmission.blocks.fluidTrasmitter.FluidTransmitterBlock;
import com.forsteri.createendertransmission.entry.TransmissionBlockEntities;
import com.forsteri.createendertransmission.entry.TransmissionBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.forsteri.createendertransmission.transmitUtil.TransmitterScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemTransmitterBlock extends Block implements IBE<ItemTransmitterBlockEntity>, IWrenchable {
    public ItemTransmitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<ItemTransmitterBlockEntity> getBlockEntityClass() {
        return ItemTransmitterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ItemTransmitterBlockEntity> getBlockEntityType() {
        return TransmissionBlockEntities.ITEM_TRANSMITTER_TILE_ENTITY.get();
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

    @net.neoforged.api.distmarker.OnlyIn(value = net.neoforged.api.distmarker.Dist.CLIENT)
    protected void displayScreen(ItemTransmitterBlockEntity te, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener.open(new TransmitterScreen(te, TransmissionBlocks.ITEM_TRANSMITTER_BLOCK.asStack()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(1, 0, 1, 15, 14, 15);
    }
}
