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

    public static final IStatGetter SourceLeechGetter = new StatGetterEffectLevel(SourceLeech,1.0d);
    public static final GuiStatBar SourceLeechBar = new GuiStatBar(0,0,59,"tetra.stats.source_leech",0D,100D, false, SourceLeechGetter,LabelGetterBasic.percentageLabel, new TooltipGetterPercentageDecimal("tetra.stats.source_leech.tooltip", SourceLeechGetter));
    public static final IStatGetter SpellstrikeGetter = new StatGetterEffectLevel(Spellstrike,1.0D);
    public static final GuiStatBar SpellstrikeBar = new GuiStatBar(0,0,59,"tetra.stats.spellstrike",0d,3d,true, SpellstrikeGetter,LabelGetterBasic.integerLabel, new TooltipGetterInteger("tetra.stats.spellstrike.tooltip", SpellstrikeGetter));
    public static void RegisterNewBars() {
        ArsArmiger.LOGGER.info("Initializing new Tetra UI bars.");
        WorkbenchStatsGui.addBar(SourceLeechBar);
        HoloStatsGui.addBar(SourceLeechBar);
        ArsArmiger.LOGGER.info("Registered Source Leech bar.");
        WorkbenchStatsGui.addBar(SpellstrikeBar);
        HoloStatsGui.addBar(SpellstrikeBar);
        ArsArmiger.LOGGER.info("Registered Spellstrike bar.");
        ArsArmiger.LOGGER.info("Finished initialization of new Tetra UI bars.");
    }

}
