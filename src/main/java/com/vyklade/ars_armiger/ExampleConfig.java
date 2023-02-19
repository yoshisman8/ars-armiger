package com.vyklade.ars_armiger;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
//import com.vyklade.ars_armiger.item.EnchantersGauntlet;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = ArsArmiger.MODID)
public class ExampleConfig {
//    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
//    public static ForgeConfigSpec spec;
//    //public static ForgeConfigSpec.IntValue honeGauntletBase;
//    //public static ForgeConfigSpec.IntValue honeGauntletMultiplier;
//    static {
//        //honeGauntletBase = builder.comment("The base value for number of uses required before an Enchanter's Gauntlet can be honed").defineInRange("hone_gauntlet_base", 110, -2147483648, 2147483647);
//        //honeGauntletMultiplier = builder.comment("Integrity multiplier for Enchanter's Gauntlet honing, a value of 2 would cause a gauntlet which uses 3 integrity to require 2*3 times as many uses before it can be honed").defineInRange("hone_gauntlet_integrity_multiplier", 65, -2147483648, 2147483647);
//        builder.pop();
//        spec = builder.build();
//    }
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
//        onModConfigLoad();
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
//        onModConfigLoad();
    }

//     public static void setup() {
//        CommentedFileConfig configData = (CommentedFileConfig)CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("ars_armiger.toml")).sync().autosave().preserveInsertionOrder().writingMode(WritingMode.REPLACE).build();
//        configData.load();
//        spec.setConfig((CommentedConfig)configData);
//    }
//    private static void onModConfigLoad() {
//        EnchantersGauntlet.instance.updateConfig((Integer)honeGauntletBase.get().intValue(),(Integer)honeGauntletMultiplier.get().intValue());
//    }
}
