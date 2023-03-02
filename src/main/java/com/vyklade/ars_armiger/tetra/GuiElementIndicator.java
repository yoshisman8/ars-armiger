package com.vyklade.ars_armiger.tetra;

import com.vyklade.ars_armiger.ArsArmiger;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import se.mickelus.tetra.gui.stats.bar.GuiStatIndicator;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.ITooltipGetter;

public class GuiElementIndicator extends GuiStatIndicator {
    public GuiElementIndicator(int x, int y, String label, int textureIndex, IStatGetter statGetter, ITooltipGetter tooltipGetter) {
        super(x, y, label, textureIndex, statGetter, tooltipGetter);
        this.label = I18n.get(label);
        this.statGetter = statGetter;
        this.tooltipGetter = tooltipGetter;
        this.textureLocation = new ResourceLocation("tetra", "textures/gui/ars_glyphs.png");
    }
}
