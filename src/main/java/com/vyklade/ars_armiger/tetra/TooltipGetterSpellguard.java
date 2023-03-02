package com.vyklade.ars_armiger.tetra;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.ITooltipGetter;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectEfficiency;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;

public class TooltipGetterSpellguard implements ITooltipGetter {
    private static final IStatGetter efficiencyGetter = new StatGetterEffectEfficiency(TetraIntegrations.Spellguarding, 100);
    private static final IStatGetter levelGetter = new StatGetterEffectLevel(TetraIntegrations.Spellguarding, 1);

    public TooltipGetterSpellguard() {
    }

    @Override
    public String getTooltipBase(Player player, ItemStack itemStack) {
        String level = String.format("%.0f%%", levelGetter.getValue(player, itemStack));
        String efficiency = String.format("%.0f%%", efficiencyGetter.getValue(player, itemStack));

        return I18n.get("tetra.stats.spellguard.tooltip", level, efficiency);
    }
}
