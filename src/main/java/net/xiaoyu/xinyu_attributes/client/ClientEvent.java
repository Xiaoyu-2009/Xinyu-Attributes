package net.xiaoyu.xinyu_attributes.client;

import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        OreVisionRenderer.renderBlockOutlines(event);
    }
}