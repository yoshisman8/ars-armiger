//package com.vyklade.ars_armiger.item;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.vyklade.ars_armiger.ArsArmiger;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.model.geom.EntityModelSet;
//import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.model.geom.EntityModelSet;
//import net.minecraft.client.model.geom.ModelLayerLocation;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.Sheets;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.renderer.entity.ItemRenderer;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.resources.model.Material;
//import net.minecraft.core.Holder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.PreparableReloadListener;
//import net.minecraft.server.packs.resources.ReloadableResourceManager;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.ShieldItem;
//import net.minecraft.world.level.block.entity.BannerBlockEntity;
//import net.minecraft.world.level.block.entity.BannerPattern;
//import net.minecraft.world.level.block.entity.BannerPatterns;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import se.mickelus.mutil.util.CastOptional;
//import se.mickelus.tetra.items.modular.ModularItem;
//import se.mickelus.tetra.module.data.ModuleModel;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//public class GauntletRenderer extends BlockEntityWithoutLevelRenderer {
//    public static ModelLayerLocation layer = new ModelLayerLocation(new ResourceLocation(ArsArmiger.MODID, "item/enchanters_gauntlet"),"main");
//    private final EntityModelSet modelSet;
//    private GauntletModel model;
//
//    public GauntletRenderer(Minecraft minecraft) {
//        super(minecraft.getBlockEntityRenderDispatcher(),minecraft.getEntityModels());
//        modelSet = minecraft.getEntityModels();
//        model = new GauntletModel(modelSet.bakeLayer(layer));
//    }
//
//    @Override
//    public void onResourceManagerReload(ResourceManager manager) {
//        this.model = new GauntletModel(modelSet.bakeLayer(layer));
//    }
//
//    @Override
//    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
//        matrixStack.pushPose();
//        matrixStack.scale(1.0F, -1.0F, -1.0F);
//
//        if(!(itemStack.getItem() instanceof EnchantersGauntlet)) return;
//
//        var body = ((ModularItem)itemStack.getItem()).getModuleFromSlot(itemStack, EnchantersGauntlet.bodyKey);
//        var foci = ((ModularItem)itemStack.getItem()).getModuleFromSlot(itemStack, EnchantersGauntlet.fociKey);
//        var bodytint = body.getModels(itemStack)[0].tint;
//        var focitint = foci.getModels(itemStack)[0].tint;
//
//        for(ModelPart part : this.model.getRoot().getAllParts().collect(Collectors.toList())){
//            model.getRoot().getChild()
//            Material material = new Material((TextureAtlas.LOCATION_BLOCKS))
//        }
//
//        Material bodyMaterial
//        VertexConsumer vertexBuilder =
//
//        float bodyr = ((bodytint >> 16) & 0xFF) / 255f; // red
//        float bodyg = ((bodytint >> 8) & 0xFF) / 255f; // green
//        float bodyb = ((bodytint >> 0) & 0xFF) / 255f; // blue
//        float bodya = ((bodytint >> 24) & 0xFF) / 255f; // alpha
//
//    }
//}
