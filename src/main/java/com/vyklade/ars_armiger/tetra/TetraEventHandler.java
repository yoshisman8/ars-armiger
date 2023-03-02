package com.vyklade.ars_armiger.tetra;


import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.spell.validation.SpellPhraseValidator;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.vyklade.ars_armiger.ArsArmiger;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Triple;
import org.jetbrains.annotations.NotNull;
import org.openjdk.nashorn.internal.ir.annotations.Reference;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.ThrownModularItemEntity;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import se.mickelus.tetra.items.modular.impl.shield.ModularShieldItem;

import java.nio.file.WatchEvent;
import java.nio.file.Watchable;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

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
            if(hand == ItemStack.EMPTY) return;
            if(!(hand.getItem() instanceof ModularItem)) return;

            int sourceLeech = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("source_leech"));
            if(sourceLeech > 0) {
                LazyOptional<IManaCap> manaCap = CapabilityRegistry.getMana((Player) source);
                if (manaCap.isPresent()) {
                    IManaCap mana = manaCap.resolve().get();
                    int maxMana = mana.getMaxMana();
                    double recover = Math.floor((sourceLeech*100)/maxMana);
                    mana.addMana(recover);
                }
            }

            int spellstriker = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("spellstrike"));
            if(spellstriker > 0) {
                if(((Player) source).getCooldowns().isOnCooldown(hand.getItem())) return;

                double cooldown = ((ModularItem) hand.getItem()).getEffectEfficiency(hand, ItemEffect.get("spellstrike_duration"));
                double cost = ((ModularItem) hand.getItem()).getEffectEfficiency(hand, ItemEffect.get("spellstrike_efficiency"));
                int power = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("spellstrike_power"));

                SpellCaster caster = new BasicReductionCaster(hand, (spell -> { spell.addDiscount(MethodTouch.INSTANCE.getCastingCost()); return  spell;}));
                PlayerCaster player = new PlayerCaster((Player)source);

                Spell spell = caster.modifySpellBeforeCasting(level, source, InteractionHand.MAIN_HAND, caster.getSpell());

                if(spell.isEmpty()) return;

                int air = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("air_attunement"));
                int earth = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("earth_attunement"));
                int fire = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("fire_attunement"));
                int water = ((ModularItem) hand.getItem()).getEffectLevel(hand, ItemEffect.get("water_attunement"));
                if(air > 0)
                    Amplify(spell,SpellSchools.ELEMENTAL_AIR, air >= 2);
                else if (earth > 0)
                    Amplify(spell,SpellSchools.ELEMENTAL_EARTH, earth >= 2);
                else if (fire > 0)
                    Amplify(spell,SpellSchools.ELEMENTAL_FIRE, fire >= 2);
                else if (water > 0)
                    Amplify(spell,SpellSchools.ELEMENTAL_WATER, water >= 2);

                if (power > 0) Power(spell, power);

                spell.addDiscount((int)(spell.getNoDiscountCost() * (1- cost)));

                SpellContext context = new SpellContext(level,spell, (LivingEntity) source, player);
                SpellResolver resolver = new SpellResolver(context);

                resolver.onCastOnEntity(hand, target, InteractionHand.MAIN_HAND);

                ((Player) source).getCooldowns().addCooldown(hand.getItem(), (int)Math.ceil(cooldown * 20));
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
        if(item == ItemStack.EMPTY) return;
        if(!(item.getItem() instanceof ModularItem)) return;
        var data = proj.getPersistentData();

        Entity owner = proj.getOwner();
        if(owner == null) return;

        int sourceLeech = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("source_leech"));
        if(sourceLeech > 0) {
            LazyOptional<IManaCap> manaCap = CapabilityRegistry.getMana((Player) owner);
            if (manaCap.isPresent()) {
                IManaCap mana = manaCap.resolve().get();
                int maxMana = mana.getMaxMana();
                double recover = Math.floor((sourceLeech*100)/maxMana);
                mana.addMana(recover);
            }
        }

        int spellstriker = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("spellstrike"));

        if(spellstriker <= 0) return;

        if(((Player) owner).getCooldowns().isOnCooldown(item.getItem())) return;

        double cooldown = ((ModularItem) item.getItem()).getEffectEfficiency(item, ItemEffect.get("spellstrike_duration"));
        double cost = ((ModularItem) item.getItem()).getEffectEfficiency(item, ItemEffect.get("spellstrike_efficiency"));
        int power = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("spellstrike_power"));

        SpellCaster caster = new BasicReductionCaster(item, (spell -> { spell.addDiscount(MethodTouch.INSTANCE.getCastingCost()); return  spell;}));
        PlayerCaster player = new PlayerCaster((Player)owner);

        Spell spell = caster.modifySpellBeforeCasting(level, owner, InteractionHand.MAIN_HAND, caster.getSpell());

        if(spell.isEmpty()) return;

        int air = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("air_attunement"));
        int earth = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("earth_attunement"));
        int fire = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("fire_attunement"));
        int water = ((ModularItem) item.getItem()).getEffectLevel(item, ItemEffect.get("water_attunement"));
        if(air > 0)
            Amplify(spell,SpellSchools.ELEMENTAL_AIR, air >= 2);
        else if (earth > 0)
            Amplify(spell,SpellSchools.ELEMENTAL_EARTH, earth >= 2);
        else if (fire > 0)
            Amplify(spell,SpellSchools.ELEMENTAL_FIRE, fire >= 2);
        else if (water > 0)
            Amplify(spell,SpellSchools.ELEMENTAL_WATER, water >= 2);

        if (power > 0) Power(spell, power);

        spell.addDiscount((int)(spell.getNoDiscountCost() * (1- cost)));

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
        ((Player) owner).getCooldowns().addCooldown(item.getItem(), (int)Math.ceil(cooldown * 20));
    }

    @SubscribeEvent
    public static void onBlockAttack(@NotNull ShieldBlockEvent event){
        Entity blocker = event.getEntity();
        if(blocker == null) return;
        if(!(blocker instanceof Player)) return;

        Player playerEntity = (Player) blocker;

        Level level = playerEntity.getCommandSenderWorld();

        if(!(level instanceof ServerLevel)) return;

        var source = event.getDamageSource();
        if(source.getEntity() == null) return;
        Entity attacker = source.getEntity();

        var itemStack = playerEntity.getUseItem();
        if(itemStack == ItemStack.EMPTY) return;
        if(!(itemStack.getItem() instanceof ItemModularHandheld)) return;

        if(playerEntity.getCooldowns().isOnCooldown(itemStack.getItem())) return;

        float spellguard = ((ModularItem) itemStack.getItem()).getEffectEfficiency(itemStack, ItemEffect.get("spellguard"));
        int spellguardChance = ((ModularItem) itemStack.getItem()).getEffectLevel(itemStack, ItemEffect.get("spellguard"));

        if(spellguardChance <= 0) return;

        if(new Random().nextInt(101) <= (spellguardChance+1)) {
            SpellCaster caster = new BasicReductionCaster(itemStack, (spell -> { spell.addDiscount(MethodTouch.INSTANCE.getCastingCost()); return  spell;}));
            PlayerCaster player = new PlayerCaster(playerEntity);

            Spell spell = caster.modifySpellBeforeCasting(level, playerEntity, InteractionHand.MAIN_HAND, caster.getSpell());

            if(spell.isEmpty()) return;

            if(spellguard < 1){
                int cost = spell.getNoDiscountCost();
                spell.addDiscount(Math.round(cost * (1 - spellguard)));
            }

            SpellContext context = new SpellContext(level,spell, playerEntity, player);
            SpellResolver resolver = new SpellResolver(context);

            resolver.onCastOnEntity(itemStack, attacker, InteractionHand.MAIN_HAND);

            double baseCooldown = ((ItemModularHandheld) itemStack.getItem()).getCooldownBase(itemStack);
            playerEntity.getCooldowns().addCooldown(itemStack.getItem(),(int) Math.round(Math.max(1, baseCooldown * 20)));
        }
    }
    public static void Amplify(Spell Spell, SpellSchool school, boolean trueAttune){
        for(int i = 0; i < Spell.recipe.size(); i++){
            AbstractSpellPart part = Spell.recipe.get(i);
            if(!(part instanceof AbstractEffect)) continue;
            if(SpellSchools.ELEMENTAL.isPartOfSchool(part)){
                if (school.isPartOfSchool(part)){
                    Spell.add(AugmentAmplify.INSTANCE,1,i+1);
                    Spell.addDiscount(AugmentAmplify.INSTANCE.getCastingCost());
                }
                else {
                    if (trueAttune) continue;
                    Spell.add(AugmentDampen.INSTANCE,1,i+1);
                    Spell.addDiscount(AugmentDampen.INSTANCE.getCastingCost());
                }
            }
        }
    }
    public static void Power(Spell Spell, int level){
        int added = 0;
        for(int i = Spell.recipe.size()-1; i >= 0 ; i--){
            AbstractSpellPart part = Spell.recipe.get(i);
            if(!(part instanceof AbstractEffect)) continue;
            if(added >= level) return;
            Spell.add(AugmentAmplify.INSTANCE,1,i+1);
            Spell.addDiscount(AugmentAmplify.INSTANCE.getCastingCost());
            added++;
        }
    }
}
