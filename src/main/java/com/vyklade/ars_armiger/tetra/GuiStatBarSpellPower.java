package com.vyklade.ars_armiger.tetra;

import com.mojang.realmsclient.client.Request;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.bar.GuiStatIndicator;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;

public class GuiStatBarSpellPower extends GuiStatBar {
    public static final IStatGetter AirAttunementGetter = new StatGetterEffectLevel(ItemEffect.get("air_attunement"),1.0d);
    public static final IStatGetter EarthAttunementGetter = new StatGetterEffectLevel(ItemEffect.get("earth_attunement"),1.0d);
    public static final IStatGetter FireAttunementGetter = new StatGetterEffectLevel(ItemEffect.get("fire_attunement"),1.0d);
    public static final IStatGetter WaterAttunementGetter = new StatGetterEffectLevel(ItemEffect.get("water_attunement"),1.0d);
    public GuiStatBarSpellPower(int x, int y, int width) {
        super(x, y, width, I18n.get("tetra.stats.spellstrike_power"), 0, 6,
                true, TetraIntegrations.SpellstrikePowerGetter, LabelGetterBasic.integerLabel, new TooltipGetterInteger("tetra.stats.spellstrike_power.tooltip", TetraIntegrations.SpellstrikePowerGetter));

        var air = new GuiElementIndicator(0, 0, "tetra.stats.air_attunement", 1, AirAttunementGetter, new TooltipGetterInteger("tetra.stats.air_attunement.tooltip", AirAttunementGetter));
        var earth = new GuiElementIndicator(0, 0, "tetra.stats.earth_attunement", 3, EarthAttunementGetter, new TooltipGetterInteger("tetra.stats.earth_attunement.tooltip", EarthAttunementGetter));
        var fire = new GuiElementIndicator(0, 0, "tetra.stats.fire_attunement", 0, FireAttunementGetter, new TooltipGetterInteger("tetra.stats.fire_attunement.tooltip", FireAttunementGetter));
        var water = new GuiElementIndicator(0, 0, "tetra.stats.water_attunement", 2, WaterAttunementGetter, new TooltipGetterInteger("tetra.stats.water_attunement.tooltip", WaterAttunementGetter));

        setIndicators(air,earth,fire,water);
    }

    @Override
    public boolean shouldShow(Player player, ItemStack currentStack, ItemStack previewStack, String slot, String improvement) {
        boolean isAir = AirAttunementGetter.getValue(player,currentStack) > 0 || AirAttunementGetter.getValue(player, previewStack) > 0;
        boolean isEarth = EarthAttunementGetter.getValue(player,currentStack) > 0 || EarthAttunementGetter.getValue(player, previewStack) > 0;
        boolean isFire = FireAttunementGetter.getValue(player,currentStack) > 0 || FireAttunementGetter.getValue(player, previewStack) > 0;
        boolean isWater = WaterAttunementGetter.getValue(player,currentStack) > 0 || WaterAttunementGetter.getValue(player, previewStack) > 0;
        return isAir || isEarth || isFire || isWater || super.shouldShow(player,currentStack,previewStack,slot,improvement);
    }
}
