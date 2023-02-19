package com.vyklade.ars_armiger.tetra;

import com.vyklade.ars_armiger.ArsArmiger;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.*;

public class TetraIntegrations {
    public static ItemEffect SourceLeech = ItemEffect.get("source_leech");
    public static ItemEffect Spellstrike = ItemEffect.get("spellstrike");
    public static ItemEffect AirAttunement = ItemEffect.get(("air_attunement"));
    public static ItemEffect EarthAttunement = ItemEffect.get(("earth_attunement"));
    public static ItemEffect FireAttunement = ItemEffect.get(("fire_attunement"));
    public static ItemEffect WaterAttunement = ItemEffect.get(("water_attunement"));

    public static final IStatGetter SourceLeechGetter = new StatGetterEffectLevel(SourceLeech,1.0d);
    public static final GuiStatBar SourceLeechBar = new GuiStatBar(0,0,59,"tetra.stats.source_leech",0D,100D, false, SourceLeechGetter,LabelGetterBasic.percentageLabel, new TooltipGetterPercentageDecimal("tetra.stats.source_leech.tooltip", SourceLeechGetter));
    public static final IStatGetter SpellstrikeGetter = new StatGetterEffectLevel(Spellstrike,1.0D);
    public static final GuiStatBar SpellstrikeBar = new GuiStatBar(0,0,59,"tetra.stats.spellstrike",0d,3d,true, SpellstrikeGetter,LabelGetterBasic.integerLabel, new TooltipGetterInteger("tetra.stats.spellstrike.tooltip", SpellstrikeGetter));

    public static final IStatGetter AirAttunementGetter = new StatGetterEffectLevel(AirAttunement,1.0d);
    public static  final GuiStatBar AirAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.air_attunement",0d,2d,true,AirAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.air_attunement.tooltip", AirAttunementGetter));

    public static final IStatGetter EarthAttunementGetter = new StatGetterEffectLevel(EarthAttunement,1.0d);
    public static  final GuiStatBar EarthAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.earth_attunement",0d,2d,true,EarthAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.earth_attunement.tooltip", EarthAttunementGetter));

    public static final IStatGetter FireAttunementGetter = new StatGetterEffectLevel(FireAttunement,1.0d);
    public static  final GuiStatBar FireAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.fire_attunement",0d,2d,true,FireAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.fire_attunement.tooltip", FireAttunementGetter));

    public static final IStatGetter WaterAttunementGetter = new StatGetterEffectLevel(WaterAttunement,1.0d);
    public static  final GuiStatBar WaterAttunementBar = new GuiStatBar(0,0,59,"tetra.stats.water_attunement",0d,2d,true,WaterAttunementGetter,LabelGetterBasic.integerLabel,new TooltipGetterInteger("tetra.stats.water_attunement.tooltip", WaterAttunementGetter));
    public static void RegisterNewBars() {
        ArsArmiger.LOGGER.info("Initializing new Tetra UI bars.");

        WorkbenchStatsGui.addBar(SourceLeechBar);
        HoloStatsGui.addBar(SourceLeechBar);

        WorkbenchStatsGui.addBar(SpellstrikeBar);
        HoloStatsGui.addBar(SpellstrikeBar);

        WorkbenchStatsGui.addBar(AirAttunementBar);
        HoloStatsGui.addBar(AirAttunementBar);

        WorkbenchStatsGui.addBar(EarthAttunementBar);
        HoloStatsGui.addBar(EarthAttunementBar);

        WorkbenchStatsGui.addBar(FireAttunementBar);
        HoloStatsGui.addBar(FireAttunementBar);

        WorkbenchStatsGui.addBar(WaterAttunementBar);
        HoloStatsGui.addBar(WaterAttunementBar);

        ArsArmiger.LOGGER.info("Finished initialization of new Tetra UI bars.");
    }

}
