package net.xiaoyu.xinyu_attributes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.xiaoyu.xinyu_attributes.entity.AbsorbedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.data.ModelData;

public class AbsorbedBlockRenderer extends EntityRenderer<AbsorbedBlockEntity> {
    public AbsorbedBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    
    @Override
    public void render(AbsorbedBlockEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        
        float scale = 1 - entity.getAbsorptionProgress() * 0.7F;
        scale = Math.max(0.1F, scale);
        
        poseStack.scale(scale, scale, scale);
        
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        
        poseStack.translate(-0.5, -0.5, -0.5);
        
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
            entity.getBlockState(), 
            poseStack, 
            bufferSource, 
            packedLight, 
            OverlayTexture.NO_OVERLAY, 
            ModelData.EMPTY,
            null
        );
        
        poseStack.popPose();
    }
    
    @Override
    public ResourceLocation getTextureLocation(AbsorbedBlockEntity entity) {
        return null;
    }
}