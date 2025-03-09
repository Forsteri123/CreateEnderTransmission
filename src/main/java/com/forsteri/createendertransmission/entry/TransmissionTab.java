package com.forsteri.createendertransmission.entry;

import com.forsteri.createendertransmission.CreateEnderTransmission;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;

public class TransmissionTab {
    private static final DeferredRegister<CreativeModeTab> REGISTER
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateEnderTransmission.MOD_ID);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

//    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> TAB =
//            REGISTER.register("ender_transmission",
//                    () -> CreativeModeTab.builder()
//                            .title(Component.translatable("itemGroup.ender_transmission"))
//                            .withTabsBefore(ResourceLocation.fromNamespaceAndPath("create:palettes", ":"))
//                            .icon(() -> new ItemStack(TransmissionBlocks.ENERGY_TRANSMITTER_BLOCK.get()))
//                            .displayItems(
//                                    (parameters, output) ->
//                                            output.acceptAll(
//                                                    CreateEnderTransmission.REGISTRATE.getAll(Registries.ITEM).stream().map(
//                                                            regObj -> new ItemStack(regObj.get())
//                                                    ).toList()
//                                            )
//                            )
//                            .build());
//
//    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> TAB = REGISTRATE.defaultCreativeTab("ender_transmission",
//            c -> c.icon(() -> new ItemStack(Registration.DIAMOND_DRILL_ITEM.get()))
//    ).register();
}
