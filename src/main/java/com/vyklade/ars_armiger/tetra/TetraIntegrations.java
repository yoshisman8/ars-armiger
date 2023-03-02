package com.vyklade.ars_armiger.tetra;

import com.vyklade.ars_armiger.ArsArmiger;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfig;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.*;

public class TetraIntegrations {
    public static final ItemEffect SourceLeech = ItemEffect.get("source_leech");

    public static final ItemEffect SpellstrikePower = ItemEffect.get("spellstrike_power");
    public static final ItemEffect SpellstrikeEfficiency = ItemEffect.get("spellstrike_efficiency");
    public static final ItemEffect SpellstrikeDuration = ItemEffect.get("spellstrike_duration");

    public static final ItemEffect Spellguarding = ItemEffect.get("spellguard");
    public static final ItemEffect AirAttunement = ItemEffect.get(("air_attunement"));
    public static final ItemEffect EarthAttunement = ItemEffect.get(("earth_attunement"));
    public static final ItemEffect FireAttunement = ItemEffect.get(("fire_attunement"));
    public static final ItemEffect WaterAttunement = ItemEffect.get(("water_attunement"));

    public static final IStatGetter SourceLeechGetter = new StatGetterEffectLevel(SourceLeech,1.0d);
    public static final GuiStatBar SourceLeechBar = new GuiStatBar(0,0,59,"tetra.stats.source_leech",0D,100D, false, SourceLeechGetter,LabelGetterBasic.percentageLabel, new TooltipGetterPercentageDecimal("tetra.stats.source_leech.tooltip", SourceLeechGetter));

    public static final IStatGetter SpellguardGetter = new StatGetterEffectLevel(Spellguarding,1.0d);
    public static final GuiStatBar SpellguardBar = new GuiStatBar(0,0,59,"tetra.stats.spellguard",0D,100D, false, SpellguardGetter,LabelGetterBasic.percentageLabel, new TooltipGetterSpellguard());

    public static final IStatGetter SpellstrikePowerGetter = new StatGetterEffectLevel(SpellstrikePower,1.0D);
    public static final GuiStatBar SpellstrikePowerBar = new GuiStatBarSpellPower(0,0,59);
    public static final IStatGetter SpellstrikeEfficiencyGetter = new StatGetterEffectEfficiency(SpellstrikeEfficiency,100.0D);
    public static final GuiStatBar SpellstrikeEfficiencyBar = new GuiStatBar(0,0,59,"tetra.stats.spellstrike_efficiency",0d,100d,false, SpellstrikeEfficiencyGetter, LabelGetterBasic.percentageLabelInverted, new TooltipGetterPercentage("tetra.stats.spellstrike_efficiency.tooltip", SpellstrikeEfficiencyGetter));
    public static final IStatGetter SpellstrikeDurationGetter = new StatGetterEffectEfficiency(SpellstrikeDuration,1.0D);
    public static final GuiStatBar SpellstrikeDurationBar = new GuiStatBar(0,0,59,"tetra.stats.spellstrike_duration",0d,7d,false, SpellstrikeDurationGetter, LabelGetterBasic.decimalLabelInverted, new TooltipGetterDecimal("tetra.stats.spellstrike_duration.tooltip", SpellstrikeDurationGetter));

    public static final IStatGetter AirAttunementGetter = new StatGetterEffectLevel(AirAttunement,1.0d);
//    public static final GuiStatBar AirAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.air_attunement",0d,2d,true,AirAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.air_attunement.tooltip", AirAttunementGetter));

    public static final IStatGetter EarthAttunementGetter = new StatGetterEffectLevel(EarthAttunement,1.0d);
//    public static final GuiStatBar EarthAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.earth_attunement",0d,2d,true,EarthAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.earth_attunement.tooltip", EarthAttunementGetter));

    public static final IStatGetter FireAttunementGetter = new StatGetterEffectLevel(FireAttunement,1.0d);
//    public static final GuiStatBar FireAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.fire_attunement",0d,2d,true,FireAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.fire_attunement.tooltip", FireAttunementGetter));

    public static final IStatGetter WaterAttunementGetter = new StatGetterEffectLevel(WaterAttunement,1.0d);
//    public static final GuiStatBar WaterAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.water_attunement",0d,2d,true,WaterAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.water_attunement.tooltip", WaterAttunementGetter));
    public static void RegisterNewBars() {
        ArsArmiger.LOGGER.info("Initializing new Tetra UI bars.");

        WorkbenchStatsGui.addBar(SourceLeechBar);
        HoloStatsGui.addBar(SourceLeechBar);

        WorkbenchStatsGui.addBar(SpellstrikePowerBar);
        HoloStatsGui.addBar(SpellstrikePowerBar);

        WorkbenchStatsGui.addBar(SpellstrikeDurationBar);
        HoloStatsGui.addBar(SpellstrikeDurationBar);

        WorkbenchStatsGui.addBar(SpellstrikeEfficiencyBar);
        HoloStatsGui.addBar(SpellstrikeEfficiencyBar);

        WorkbenchStatsGui.addBar(SpellguardBar);
        HoloStatsGui.addBar(SpellguardBar);

//        WorkbenchStatsGui.addBar(AirAttunementBar);
//        HoloStatsGui.addBar(AirAttunementBar);
//
//        WorkbenchStatsGui.addBar(EarthAttunementBar);
//        HoloStatsGui.addBar(EarthAttunementBar);
//
//        WorkbenchStatsGui.addBar(FireAttunementBar);
//        HoloStatsGui.addBar(FireAttunementBar);
//
//        WorkbenchStatsGui.addBar(WaterAttunementBar);
//        HoloStatsGui.addBar(WaterAttunementBar);

        ArsArmiger.LOGGER.info("Finished initialization of new Tetra UI bars.");
    }

}
