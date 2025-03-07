package com.forsteri.createendertransmission.blocks.itemTransmitter;

import com.forsteri.createendertransmission.entry.TransmissionBlockEntities;
import com.forsteri.createendertransmission.entry.TransmissionBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.forsteri.createendertransmission.transmitUtil.TransmitterScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
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

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                          BlockHitResult hit) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> withBlockEntityDo(worldIn, pos, te -> this.displayScreen(te, player)));
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(value = Dist.CLIENT)
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
