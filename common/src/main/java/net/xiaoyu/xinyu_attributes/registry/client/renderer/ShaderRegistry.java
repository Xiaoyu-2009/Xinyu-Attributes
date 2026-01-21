package net.xiaoyu.xinyu_attributes.registry.client.renderer;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;

public class ShaderRegistry {
    public static ShaderInstance BLACK_HOLE_RING_SHADER;

    @ExpectPlatform
    public static void registerShaders(ResourceProvider resourceProvider) {
        throw new AssertionError();
    }
}