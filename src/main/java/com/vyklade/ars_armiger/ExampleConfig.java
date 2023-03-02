package com.vyklade.ars_armiger;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = ArsArmiger.MODID)
public class ExampleConfig {

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        onModConfigLoad();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        onModConfigLoad();
    }

     public static void setup() {

    }
    private static void onModConfigLoad() {

    }
}
