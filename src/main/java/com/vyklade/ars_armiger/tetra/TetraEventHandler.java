package com.vyklade.ars_armiger.tetra;


import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.vyklade.ars_armiger.ArsArmiger;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.ThrownModularItemEntity;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = ArsArmiger.MODID)
public class TetraEventHandler {

    @SubscribeEvent
    public static void onAttackEntityEvent(@NotNull AttackEntityEvent event){
        Entity source = event.getEntity();
        Entity target = event.getTarget();

        if(source == null || target == null) return;
        if(!(source instanceof Player)) return;
        if(!(source instanceof LivingEntity)) return;

        Level level = target.getCommandSenderWorld();

        if(!(level instanceof ServerLevel)) return;

        for(ItemStack hand : source.getHandSlots()) {
            if(!(hand.getItem() instanceof ModularItem)) return;

            int sourceLeech = ((ModularItem) hand.getItem()).getEffectLevel(hand, TetraIntegrations.SourceLeech);
            if(sourceLeech > 0) {
                LazyOptional<IManaCap> manaCap = CapabilityRegistry.getMana((Player) source);
                if (manaCap.isPresent()) {
                    IManaCap mana = manaCap.resolve().get();
                    int maxMana = mana.getMaxMana();
                    double recover = Math.floor((sourceLeech*100)/maxMana);
                    mana.addMana(recover);
                }
            }

            int spellstriker = ((ModularItem) hand.getItem()).getEffectLevel(hand, TetraIntegrations.Spellstrike);
            if(spellstriker > 0) {
                SpellCaster caster = new SpellCaster(hand);
                PlayerCaster player = new PlayerCaster((Player)source);
                Spell spell = caster.modifySpellBeforeCasting(level, source, InteractionHand.MAIN_HAND, caster.getSpell());

                List<AbstractSpellPart> parts = spell.recipe;
                if (spellstriker > 1) parts.add(AugmentAmplify.INSTANCE);
                if (spellstriker > 2) parts.add(AugmentAmplify.INSTANCE);
                spell.recipe = parts;
                SpellContext context = new SpellContext(level,spell, (LivingEntity) source, player);
                SpellResolver resolver = new SpellResolver(context);

                resolver.onCastOnEntity(hand, target, InteractionHand.MAIN_HAND);
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileImpactEvent(@NotNull ProjectileImpactEvent event){
        Projectile proj = event.getProjectile();
        HitResult result = event.getRayTraceResult();

        if(proj == null || result == null) return;

        Level level = proj.getLevel();
        if(!(level instanceof ServerLevel)) return;

        if(result.getType() == HitResult.Type.MISS) return;

        if(!(proj instanceof ThrownModularItemEntity)) return;
        ItemStack item = ((ThrownModularItemEntity) proj).getThrownStack();

        if(item == null) return;
        if(!(item.getItem() instanceof ModularItem)) return;
        var data = proj.getPersistentData();

        Entity owner = proj.getOwner();
        if(owner == null) return;

        int spellstriker = ((ModularItem) item.getItem()).getEffectLevel(item, TetraIntegrations.Spellstrike);

        SpellCaster caster = new SpellCaster(item);
        PlayerCaster player = new PlayerCaster((Player)owner);
        Spell spell = caster.modifySpellBeforeCasting(level, owner, InteractionHand.MAIN_HAND, caster.getSpell());

        List<AbstractSpellPart> parts = spell.recipe;
        if (spellstriker > 1) parts.add(AugmentAmplify.INSTANCE);
        if (spellstriker > 2) parts.add(AugmentAmplify.INSTANCE);
        spell.recipe = parts;
        SpellContext context = new SpellContext(level,spell, (LivingEntity) owner, player);
        SpellResolver resolver = new SpellResolver(context);

        switch (result.getType()){
            case ENTITY:
                if(!(result instanceof  EntityHitResult)) return;
                byte pierce = ((ThrownModularItemEntity) proj).getPierceLevel();
                if(!data.contains("ars_armiger:collided_entities")){
                    data.putInt("ars_armiger:collided_entities",0);
                }
                int collisions = data.getInt("ars_armiger:collided_entities");
                if(data.contains("ars_armiger:hit_entity")){
                    if(collisions > pierce && data.getBoolean("ars_armiger:hit_entity")) return;
                }

                Entity target = ((EntityHitResult)result).getEntity();
                if(target == null) return;

                resolver.onCastOnEntity(item, target, InteractionHand.MAIN_HAND);
                data.putBoolean("ars_armiger:hit_entity",true);
                data.putInt("ars_armiger:collided_entities",data.getInt("ars_armiger:collided_entities") +1 );
                break;
            case BLOCK:
                if(!(result instanceof BlockHitResult)) return;
                if(data.contains("ars_armiger:hit_block")){
                    if(data.getBoolean("ars_armiger:hit_block")) return;
                }
                if(data.contains("ars_armiger:hit_entity")){
                    if(data.getBoolean("ars_armiger:hit_entity")) return;
                }

                resolver.onCastOnBlock(((BlockHitResult)result));
                data.putBoolean("ars_armiger:hit_block",true);
                break;
        }
    }
}
