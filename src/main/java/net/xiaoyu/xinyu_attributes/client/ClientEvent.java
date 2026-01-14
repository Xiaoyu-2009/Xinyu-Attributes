package net.xiaoyu.xinyu_attributes.client;

import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.xiaoyu.xinyu_attributes.registry.EntityRegistry;
import net.xiaoyu.xinyu_attributes.entity.AbsorbedBlockEntity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        OreVisionRenderer.renderBlockOutlines(event);
    }
    
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends AbsorbedBlockEntity>) EntityRegistry.ABSORBED_BLOCK_ENTITY.get(),
            AbsorbedBlockRenderer::new);
    }
}