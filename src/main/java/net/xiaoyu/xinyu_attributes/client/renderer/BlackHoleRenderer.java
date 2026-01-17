package net.xiaoyu.xinyu_attributes.client.renderer;

import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.*;
import net.xiaoyu.xinyu_attributes.entity.BlackHoleEntity;
import net.xiaoyu.xinyu_attributes.registry.client.renderer.ShaderRegistry;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {
    public BlackHoleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BlackHoleEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.15, 0);

        float originalRed = RenderSystem.getShaderColor()[0];
        float originalGreen = RenderSystem.getShaderColor()[1];
        float originalBlue = RenderSystem.getShaderColor()[2];
        float originalAlpha = RenderSystem.getShaderColor()[3];

        RenderSystem.enableDepthTest();
        
        /*RenderSystem.setShader(GameRenderer::getPositionColorShader);*/
        RenderSystem.setShader(() -> ShaderRegistry.BLACK_HOLE_RING_SHADER);

        RenderSystem.setShaderTexture(0, ResourceLocation.withDefaultNamespace("textures/misc/white.png"));

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        float radius = 15f;
        int segments = 128;
        int rings = 128;
        
        for (int ring = 0; ring < rings; ring++) {
            float phi1 = (float) (ring * Math.PI / rings);
            float phi2 = (float) ((ring + 1) * Math.PI / rings);

            for (int seg = 0; seg < segments; seg++) {
                float theta1 = (float) (seg * 2 * Math.PI / segments);
                float theta2 = (float) ((seg + 1) * 2 * Math.PI / segments);

                float x11 = radius * Mth.sin(phi1) * Mth.cos(theta1);
                float y11 = radius * Mth.cos(phi1);
                float z11 = radius * Mth.sin(phi1) * Mth.sin(theta1);

                float x12 = radius * Mth.sin(phi1) * Mth.cos(theta2);
                float y12 = radius * Mth.cos(phi1);
                float z12 = radius * Mth.sin(phi1) * Mth.sin(theta2);

                float x21 = radius * Mth.sin(phi2) * Mth.cos(theta1);
                float y21 = radius * Mth.cos(phi2);
                float z21 = radius * Mth.sin(phi2) * Mth.sin(theta1);

                float x22 = radius * Mth.sin(phi2) * Mth.cos(theta2);
                float y22 = radius * Mth.cos(phi2);
                float z22 = radius * Mth.sin(phi2) * Mth.sin(theta2);

                buffer.addVertex(matrix, x11, y11, z11).setColor(0, 0, 0, 255);
                buffer.addVertex(matrix, x12, y12, z12).setColor(0, 0, 0, 255);
                buffer.addVertex(matrix, x22, y22, z22).setColor(0, 0, 0, 255);
                
                buffer.addVertex(matrix, x11, y11, z11).setColor(0, 0, 0, 255);
                buffer.addVertex(matrix, x22, y22, z22).setColor(0, 0, 0, 255);
                buffer.addVertex(matrix, x21, y21, z21).setColor(0, 0, 0, 255);
            }
        }

        BufferUploader.drawWithShader(buffer.buildOrThrow());

        /*RenderSystem.setShader(GameRenderer::getPositionColorShader);*/
        RenderSystem.setShader(() -> ShaderRegistry.BLACK_HOLE_RING_SHADER);
        
        BufferBuilder ringBuffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        float majorRadius = 0.35f;
        float minorRadius = 0.05f;
        int sides = 64;
        int torusRings = 64;

/*        for (int i = 0; i <= torusRings; i++) {
            float u = (float) i / torusRings;
            float theta = u * Mth.TWO_PI;

            for (int j = 0; j <= sides; j++) {
                float v = (float) j / sides;
                float phi = v * Mth.TWO_PI;

                float x1 = (majorRadius + minorRadius * Mth.cos(phi)) * Mth.cos(theta);
                float y1 = minorRadius * Mth.sin(phi);
                float z1 = (majorRadius + minorRadius * Mth.cos(phi)) * Mth.sin(theta);
                
                float x2 = (majorRadius + minorRadius * Mth.cos(phi)) * Mth.cos(theta + Mth.TWO_PI / torusRings);
                float y2 = minorRadius * Mth.sin(phi);
                float z2 = (majorRadius + minorRadius * Mth.cos(phi)) * Mth.sin(theta + Mth.TWO_PI / torusRings);

                ringBuffer.addVertex(matrix, x1, y1, z1).setColor(128, 0, 128, 255);
                ringBuffer.addVertex(matrix, x2, y2, z2).setColor(128, 0, 128, 255);
            }
        }

        BufferUploader.drawWithShader(ringBuffer.buildOrThrow());*/
        
        RenderSystem.setShaderColor(originalRed, originalGreen, originalBlue, originalAlpha);
        /*RenderSystem.disableDepthTest();*/

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackHoleEntity entity) {
        return null;
    }
}