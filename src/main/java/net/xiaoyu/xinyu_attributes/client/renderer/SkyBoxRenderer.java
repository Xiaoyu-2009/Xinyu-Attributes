package net.xiaoyu.xinyu_attributes.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.xiaoyu.xinyu_attributes.registry.client.renderer.ShaderRegistry;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

public class SkyBoxRenderer {
    public static void renderSkyBoxEffect(RenderLevelStageEvent event) {
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        RenderSystem.setShader(() -> ShaderRegistry.BLACK_HOLE_RING_SHADER);

        PoseStack poseStack = new PoseStack();
        poseStack.mulPose(event.getModelViewMatrix());

        for (int i = 0; i < 6; i++) {
            poseStack.pushPose();

            if (i == 1) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            } else if (i == 2) {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            } else if (i == 3) {
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            } else if (i == 4) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            } else if (i == 5) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F);
            bufferbuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 1.0F);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(1.0F, 1.0F);
            bufferbuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(1.0F, 0.0F);

            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            poseStack.popPose();
        }
    }
}