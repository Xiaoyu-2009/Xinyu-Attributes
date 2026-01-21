package net.xiaoyu.xinyu_attributes.fabric.client.registry.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.resources.ResourceLocation;
import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.client.renderer.ShaderRegistry;

public class ShaderRegistryImpl {
    public static void initialize() {
        CoreShaderRegistrationCallback.EVENT.register(registrationContext -> {
            registrationContext.register(
                ResourceLocation.fromNamespaceAndPath(XinyuAttributes.MOD_ID, "black_hole_ring"),
                DefaultVertexFormat.POSITION_COLOR,
                shaderInstance -> ShaderRegistry.BLACK_HOLE_RING_SHADER = shaderInstance
            );
        });
    }
}