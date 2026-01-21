package net.xiaoyu.xinyu_attributes.neoforge.client;

import net.xiaoyu.xinyu_attributes.registry.EntityRegistry;
import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.xiaoyu.xinyu_attributes.entity.AbsorbedBlockEntity;
import net.xiaoyu.xinyu_attributes.entity.BlackHoleEntity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends AbsorbedBlockEntity>) EntityRegistry.ABSORBED_BLOCK_ENTITY.get(), AbsorbedBlockRenderer::new);
        event.registerEntityRenderer((EntityType<? extends BlackHoleEntity>) EntityRegistry.BLACK_HOLE_ENTITY.get(), BlackHoleRenderer::new);
    }
}