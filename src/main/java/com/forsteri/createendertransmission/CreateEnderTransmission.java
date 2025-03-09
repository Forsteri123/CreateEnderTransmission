package com.forsteri.createendertransmission;

import com.forsteri.createendertransmission.blocks.MatterWorldSavedData;
import com.forsteri.createendertransmission.entry.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateEnderTransmission.MOD_ID)
public class CreateEnderTransmission {

    public static final String MOD_ID = "createendertransmission";

    public CreateEnderTransmission(IEventBus modEventBus, ModContainer modContainer) {
//        NeoForge.EVENT_BUS.register(this);

//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(modEventBus);

        TransmissionBlocks.register();
        TransmissionBlockEntities.register();
        TransmissionPackets.register();
        TransmissionLang.register();
        TransmissionTab.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, TransmissionConfig.SPEC, "createendertransmission-server.toml");
    }

    public static MatterWorldSavedData savedData = null;

    @EventBusSubscriber
    public static class CommonEvents {
        @SubscribeEvent
        public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer serverPlayer) {
                MinecraftServer server = serverPlayer.getServer();
                if (server == null)
                    return;
                CreateEnderTransmission.savedData = MatterWorldSavedData.load(server);
            }
        }

        @SubscribeEvent
        public static void onLoadWorld(LevelEvent.Load event) {
            LevelAccessor level = event.getLevel();
            MinecraftServer server = event.getLevel().getServer();
            if (server == null || server.overworld() != level)
                return;
            CreateEnderTransmission.savedData = MatterWorldSavedData.load(server);
        }
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateEnderTransmission.MOD_ID);

}
