package com.forsteri.createendertransmission.blocks.energyTransmitter;

import com.forsteri.createendertransmission.entry.TransmissionBlockEntities;
import com.forsteri.createendertransmission.entry.TransmissionBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.forsteri.createendertransmission.transmitUtil.TransmitterScreen;
import net.createmod.catnip.gui.ScreenOpener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class EnergyTransmitterBlock extends DirectionalKineticBlock implements IBE<EnergyTransmitterBlockEntity>, IWrenchable {
    public EnergyTransmitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(FACING).getAxis();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                          BlockHitResult hit) {
        ItemStack held = player.getMainHandItem();
        if (AllItems.WRENCH.isIn(held))
            return InteractionResult.PASS;
        if (held.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof KineticBlock && hasShaftTowards(worldIn, pos, state, hit.getDirection()))
                return InteractionResult.PASS;
        }

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> withBlockEntityDo(worldIn, pos, te -> this.displayScreen(te, player)));
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(EnergyTransmitterBlockEntity te, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener.open(new TransmitterScreen(te, TransmissionBlocks.ENERGY_TRANSMITTER_BLOCK.asStack()));
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        if(getBlockEntity(p_60516_, p_60517_) != null)
            Objects.requireNonNull(getBlockEntity(p_60516_, p_60517_)).getConnectedTransmitters().remove(getBlockEntity(p_60516_, p_60517_));
        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }

    @Override
    public Class<EnergyTransmitterBlockEntity> getBlockEntityClass() {
        return EnergyTransmitterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends EnergyTransmitterBlockEntity> getBlockEntityType() {
        return TransmissionBlockEntities.ENERGY_TRANSMITTER_TILE.get();
    }
}
