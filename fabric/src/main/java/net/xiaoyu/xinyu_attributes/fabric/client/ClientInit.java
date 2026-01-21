package net.xiaoyu.xinyu_attributes.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.xiaoyu.xinyu_attributes.fabric.client.registry.renderer.ShaderRegistryImpl;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.xiaoyu.xinyu_attributes.registry.EntityRegistry;
import net.xiaoyu.xinyu_attributes.entity.*;
import net.minecraft.world.entity.*;

@SuppressWarnings("unchecked")
public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ShaderRegistryImpl.initialize();
        EntityRendererRegistry.register((EntityType<AbsorbedBlockEntity>) EntityRegistry.ABSORBED_BLOCK_ENTITY.get(), AbsorbedBlockRenderer::new);
        EntityRendererRegistry.register((EntityType<BlackHoleEntity>) EntityRegistry.BLACK_HOLE_ENTITY.get(), BlackHoleRenderer::new);
    }
}