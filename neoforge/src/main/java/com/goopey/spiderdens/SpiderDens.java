package com.goopey.spiderdens;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.fml.ModList;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import com.goopey.spiderdens.core.init.BlockInit;
import com.goopey.spiderdens.core.init.ItemInit;
import com.goopey.spiderdens.data.DataGenerators;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SpiderDens.MOD_ID)
public class SpiderDens {
    public static final String MOD_ID = "spiderdens";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SpiderDens(IEventBus modEventBus, ModContainer modContainer) {
        // register
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);

        // listeners
        modEventBus.addListener(DataGenerators::gatherData);

        // Event Buses
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Log when the mod is done loading if it is loaded.
            ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                LOGGER.info("Loaded {}, using version {}", modContainer.getModInfo().getDisplayName(), modContainer.getModInfo().getVersion());
            });
        }
    }
}
