//package com.vyklade.ars_armiger.item;
//
//import com.hollingsworth.arsnouveau.client.renderer.item.TransformAnimatedModel;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import net.minecraft.client.model.Model;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.resources.ResourceLocation;
//
//import java.util.NoSuchElementException;
//import java.util.function.Function;
//
//public class GauntletModel extends Model {
//    private final ModelPart root;
//    public GauntletModel(ModelPart modelPart) {
//        super(RenderType::entityTranslucent);
//        this.root = modelPart;
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
//
//    }
//
//    public ModelPart getModel(String part){
//        try {
//            return root.getChild(part);
//        } catch (NoSuchElementException e) {
//        }
//        return null;
//    }
//
//    public ModelPart getRoot() {
//        return root;
//    }
//}
