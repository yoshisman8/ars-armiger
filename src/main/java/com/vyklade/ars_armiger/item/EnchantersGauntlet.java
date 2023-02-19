//package com.vyklade.ars_armiger.item;
//
//import com.hollingsworth.arsnouveau.api.item.ICasterTool;
//import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
//import com.hollingsworth.arsnouveau.api.spell.*;
//import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
//import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenu;
//import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenuSlot;
//import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
//import com.hollingsworth.arsnouveau.common.items.SpellBook;
//import com.hollingsworth.arsnouveau.common.network.Networking;
//import com.hollingsworth.arsnouveau.common.network.PacketSetBookMode;
//import com.vyklade.ars_armiger.ExampleConfig;
//import net.minecraft.client.Minecraft;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.registries.ObjectHolder;
//import org.intellij.lang.annotations.JdkConstants;
//import org.jetbrains.annotations.NotNull;
//import se.mickelus.mutil.network.PacketHandler;
//import se.mickelus.tetra.data.DataManager;
//import se.mickelus.tetra.effect.ItemEffect;
//import se.mickelus.tetra.items.modular.ItemModularHandheld;
//import se.mickelus.tetra.items.modular.ModularItem;
//import se.mickelus.tetra.module.SchematicRegistry;
//import se.mickelus.tetra.module.schematic.RemoveSchematic;
//import se.mickelus.tetra.module.schematic.RepairSchematic;
//import se.mickelus.tetra.module.schematic.UpgradeSchematic;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class EnchantersGauntlet extends ItemModularHandheld implements ICasterTool, IRadialProvider {
//    public static final String bodyKey = "gauntlet/body";
//    public static final String inlayKey = "gauntlet/inlay";
//    public static final String fociKey = "gauntlet/foci";
//    public static final String identifier = "modular_gauntlet";
//
//    @ObjectHolder(registryName = "item", value = "ars_armiger:modular_gauntlet")
//    public static EnchantersGauntlet instance;
//
//
//    public EnchantersGauntlet(Properties properties) {
//        super(properties);
//        this.majorModuleKeys = new String[] { bodyKey, inlayKey };
//        this.minorModuleKeys = new String[] { fociKey };
//        this.requiredModules = new String[] { bodyKey, inlayKey };
//        updateConfig((Integer)ExampleConfig.honeGauntletBase.get().intValue(), (Integer)ExampleConfig.honeGauntletMultiplier.get().intValue());
//        SchematicRegistry.instance.registerSchematic((UpgradeSchematic) new RepairSchematic(this,identifier));
//        RemoveSchematic.registerRemoveSchematics(this,identifier);
//    }
//    public void commonInit(PacketHandler packetHandler){
//        DataManager.instance.synergyData.onReload(() -> this.synergies = DataManager.instance.getSynergyData("gauntlet/"));
//    }
//
//    public void updateConfig(int honeBase, int honeIntegrityMultiplier) {
//        this.honeBase = honeBase;
//        this.honeIntegrityMultiplier = honeIntegrityMultiplier;
//    }
//
//    @Override
//    public @NotNull ISpellCaster getSpellCaster(ItemStack stack) {
//        return new GauntletCaster(stack);
//    }
//    @Override
//    public ISpellCaster getSpellCaster() {
//        return new GauntletCaster(new CompoundTag());
//    }
//    @Override
//    public ISpellCaster getSpellCaster(CompoundTag tag) {
//        return new GauntletCaster(tag);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void onRadialKeyPressed(ItemStack stack, Player player) {
//        Minecraft.getInstance().setScreen(new GuiRadialMenu<>(getRadialMenuProviderForSpellpart(stack)));
//    }
//
//    public RadialMenu<AbstractSpellPart> getRadialMenuProviderForSpellpart(ItemStack itemStack) {
//        return new RadialMenu<>((int slot) -> {
//            EnchantersGauntlet.GauntletCaster caster = new EnchantersGauntlet.GauntletCaster(itemStack);
//            caster.setCurrentSlot(slot);
//            Networking.INSTANCE.sendToServer(new PacketSetBookMode(itemStack.getTag()));
//        },
//                getRadialMenuSlotsForSpellpart(itemStack),
//                RenderUtils::drawSpellPart,
//                0);
//    }
//
//    public List<RadialMenuSlot<AbstractSpellPart>> getRadialMenuSlotsForSpellpart(ItemStack itemStack) {
//        EnchantersGauntlet.GauntletCaster spellCaster = new EnchantersGauntlet.GauntletCaster(itemStack);
//        List<RadialMenuSlot<AbstractSpellPart>> radialMenuSlots = new ArrayList<>();
//        for (int i = 0; i < spellCaster.getMaxSlots(); i++) {
//            Spell spell = spellCaster.getSpell(i);
//            AbstractSpellPart primaryIcon = null;
//            List<AbstractSpellPart> secondaryIcons = new ArrayList<>();
//            for (AbstractSpellPart p : spell.recipe) {
//                if (p instanceof AbstractCastMethod) {
//                    secondaryIcons.add(p);
//                }
//
//                if (p instanceof AbstractEffect) {
//                    primaryIcon = p;
//                    break;
//                }
//            }
//            radialMenuSlots.add(new RadialMenuSlot<>(spellCaster.getSpellName(i), primaryIcon, secondaryIcons));
//        }
//        return radialMenuSlots;
//    }
//
//    public static class GauntletCaster extends SpellCaster {
//        public GauntletCaster(ItemStack stack) {
//            super(stack);
//        }
//        public GauntletCaster(CompoundTag tag){
//            super(tag);
//        }
//        @Override
//        public int getMaxSlots() {
//            return 1 + ((ModularItem)this.stack.getItem()).getEffectLevel(this.stack, ItemEffect.get("spell_capacity"));
//        }
//    }
//}
