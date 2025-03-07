package com.forsteri.createendertransmission.entry;

import com.forsteri.createendertransmission.CreateEnderTransmission;
import com.forsteri.createendertransmission.blocks.chunkLoader.LoaderBlock;
import com.forsteri.createendertransmission.blocks.energyTransmitter.EnergyTransmitterBlock;
import com.forsteri.createendertransmission.blocks.fluidTrasmitter.FluidTransmitterBlock;
import com.forsteri.createendertransmission.blocks.itemTransmitter.ItemTransmitterBlock;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

@SuppressWarnings("removal")
public class TransmissionBlocks {

    private static final CreateRegistrate REGISTRATE = CreateEnderTransmission
            .registrate()
            .setCreativeTab(TransmissionTab.TAB);

    static {
        assert REGISTRATE != null;
    }

    public static final BlockEntry<LoaderBlock> CHUNK_LOADER_BLOCK =
            REGISTRATE.block("chunk_loader", LoaderBlock::new)
                    .initialProperties(() -> net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.strength(10.0F, 1200.0F))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .blockstate((context, provider) -> provider.getVariantBuilder(context.getEntry()).forAllStates(state ->
                            ConfiguredModel.builder().modelFile(provider.models()
                                    .getExistingFile(provider.modLoc("block/" + context.getName() + "/block"))).build()))
                    .item()
                    .model((context, provider) -> provider.withExistingParent(context.getName(), provider.modLoc("block/" + context.getName() + "/block")))
                    .build()
                    .addLayer(() -> RenderType::cutoutMipped)
                    .onRegister((block) -> {
                        BlockStressValues.IMPACTS.register(block, () -> 16);
                    })
                    .register();

    public static final BlockEntry<EnergyTransmitterBlock> ENERGY_TRANSMITTER_BLOCK =
            REGISTRATE.block("energy_transmitter", EnergyTransmitterBlock::new)
                    .initialProperties(() -> net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.strength(10.0F, 1200.0F))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .item()
                    .model(NonNullBiConsumer.noop())
                    .transform(customItemModel())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .onRegister((block) -> {
                        BlockStressValues.IMPACTS.register(block, () -> 8.0);
                    })
                    .register();

    public static final BlockEntry<ItemTransmitterBlock> ITEM_TRANSMITTER_BLOCK =
            REGISTRATE.block("item_transmitter", ItemTransmitterBlock::new)
                    .initialProperties(() -> net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.strength(10.0F, 1200.0F))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .blockstate((context, provider) -> provider.getVariantBuilder(context.getEntry()).forAllStates(state ->
                            ConfiguredModel.builder().modelFile(provider.models()
                                    .getExistingFile(provider.modLoc("block/" + context.getName() + "/block"))).build()))
                    .item()
                    .model((context, provider) -> provider.withExistingParent(context.getName(), provider.modLoc("block/" + context.getName() + "/block")))
                    .build()
                    .addLayer(() -> RenderType::cutoutMipped)
                    .register();

    public static final BlockEntry<FluidTransmitterBlock> FLUID_TRANSMITTER_BLOCK =
            REGISTRATE.block("fluid_transmitter", FluidTransmitterBlock::new)
                    .initialProperties(() -> net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(p -> p.strength(10.0F, 1200.0F))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .transform(pickaxeOnly())
                    .blockstate((context, provider) -> provider.getVariantBuilder(context.getEntry()).forAllStates(state ->
                            ConfiguredModel.builder().modelFile(provider.models()
                                    .getExistingFile(provider.modLoc("block/" + context.getName() + "/block"))).build()))
                    .item()
                    .model((context, provider) -> provider.withExistingParent(context.getName(), provider.modLoc("block/" + context.getName() + "/block")))
                    .build()
                    .addLayer(() -> RenderType::cutoutMipped)
                    .register();

    public static void register(){}
}
