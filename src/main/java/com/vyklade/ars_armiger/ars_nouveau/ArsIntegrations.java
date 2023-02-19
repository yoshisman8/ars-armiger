package com.vyklade.ars_armiger.ars_nouveau;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.vyklade.ars_armiger.ArsArmiger;
import com.vyklade.ars_armiger.tetra.TetraEventHandler;
import com.vyklade.ars_armiger.tetra.TetraIntegrations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import se.mickelus.tetra.items.modular.ModularItem;

import javax.sound.sampled.Port;
import java.util.List;
@Mod.EventBusSubscriber(modid = ArsArmiger.MODID)
public class ArsIntegrations {

    @SubscribeEvent
    public static void onTooltip(@NotNull ItemTooltipEvent event){
        ItemStack item = event.getItemStack();

        if(!(item.getItem() instanceof ModularItem)) return;
        List<Component> tooltip = event.getToolTip();

        int spellstriker = ((ModularItem)item.getItem()).getEffectLevel(item, TetraIntegrations.Spellstrike);
        int sourceLeecch = ((ModularItem)item.getItem()).getEffectLevel(item, TetraIntegrations.SourceLeech);
        if(sourceLeecch > 0){
            MutableComponent leech = Component.translatable("tetra.tooltips.leech", sourceLeecch);
            tooltip.add(leech);
        }
        if(spellstriker > 0) {
            if(item.hasTag()) {
                MutableComponent inscribeable = Component.translatable("tetra.tooltips.inscribable");
                if(item.getTag().contains("ars_nouveau:caster")) {
                    SpellCaster caster = new BasicReductionCaster(item,(spell -> { spell.addDiscount(MethodTouch.INSTANCE.getCastingCost()); return  spell;}));
                    Spell spell = caster.getSpell();
                    if(spellstriker > 1) spell.add(AugmentAmplify.INSTANCE);
                    int air = ((ModularItem) item.getItem()).getEffectLevel(item, TetraIntegrations.AirAttunement);
                    int earth = ((ModularItem) item.getItem()).getEffectLevel(item, TetraIntegrations.EarthAttunement);
                    int fire = ((ModularItem) item.getItem()).getEffectLevel(item, TetraIntegrations.FireAttunement);
                    int water = ((ModularItem) item.getItem()).getEffectLevel(item, TetraIntegrations.WaterAttunement);
                    if(air > 0)
                        TetraEventHandler.Amplify(spell,SpellSchools.ELEMENTAL_AIR, air >= 2);
                    else if (earth > 0)
                       TetraEventHandler.Amplify(spell,SpellSchools.ELEMENTAL_EARTH, earth >= 2);
                    else if (fire > 0)
                        TetraEventHandler.Amplify(spell,SpellSchools.ELEMENTAL_FIRE, fire >= 2);
                    else if (water > 0)
                        TetraEventHandler.Amplify(spell,SpellSchools.ELEMENTAL_WATER, water >= 2);

                    if(spell.isEmpty()) {
                        tooltip.add(inscribeable);
                    }
                    else {
                        tooltip.add(Component.literal(spell.getDisplayString()));
                    }
                }
                else {
                    tooltip.add(inscribeable);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
        ItemStack item = event.getItemStack();
        Player player = event.getEntity();

        if(item == ItemStack.EMPTY || player == null) return;
        if(!item.hasTag()) return;
        if(!item.getTag().contains("ars_nouveau:caster")) return;
        if(!player.isCrouching()) return;

        Level level = event.getLevel();
        if(!(level instanceof ServerLevel)) return;
        if(level.isEmptyBlock(event.getPos())) return;

        BlockEntity block = level.getBlockEntity(event.getPos());
        if(block == null) return;
        if(!(block instanceof ScribesTile)) return;

        ScribesTile table = (ScribesTile)block;
        if(table.getStack() == ItemStack.EMPTY) return;

        ItemStack tableItem = table.getStack();

        if(!(tableItem.getItem() instanceof ModularItem)) return;

        int spellstrike = ((ModularItem)tableItem.getItem()).getEffectLevel(tableItem, TetraIntegrations.Spellstrike);
        if(spellstrike == 0) return;

        ISpellCaster caster = CasterUtil.getCaster(item);
        Spell spell = caster.getSpell();
        SpellCaster itemCaster = new SpellCaster(tableItem);

        if(spell.isEmpty()) {
            itemCaster.setSpell(spell);
            PortUtil.sendMessageNoSpam(player, Component.translatable("ars_armiger.spellstrike.cleared"));
            event.setCanceled(true);
            return;
        }

        List<AbstractSpellPart> parts = spell.recipe;

        if(parts.get(0) instanceof AbstractCastMethod) {
            PortUtil.sendMessageNoSpam(player, Component.translatable("ars_armiger.spellstrike.invalid"));
            event.setCanceled(true);
            return;
        }

        parts.add(0,MethodTouch.INSTANCE);
        spell.recipe = parts;

        itemCaster.setColor(caster.getColor());
        itemCaster.setFlavorText(caster.getFlavorText());
        itemCaster.setSpellName(caster.getSpellName());
        itemCaster.setSound(caster.getCurrentSound());
        itemCaster.setSpell(spell);

        PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.set_spell"));
        event.setCanceled(true);
    }
}
