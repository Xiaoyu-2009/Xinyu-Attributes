package net.xiaoyu.xinyu_attributes.neoforge.registry.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.client.renderer.ShaderRegistry;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(Dist.CLIENT)
public class ShaderRegistryImpl {
    
    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
            new ShaderInstance(
                event.getResourceProvider(),
                ResourceLocation.fromNamespaceAndPath(XinyuAttributes.MOD_ID, "black_hole_ring"), 
                DefaultVertexFormat.POSITION_COLOR
            ),
            shaderInstance -> ShaderRegistry.BLACK_HOLE_RING_SHADER = shaderInstance
        );
    }
}