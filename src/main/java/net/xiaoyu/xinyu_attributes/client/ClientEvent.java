package net.xiaoyu.xinyu_attributes.client;

import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.xiaoyu.xinyu_attributes.data.*;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.entity.*;
import net.minecraft.client.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.*;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();

        OreVisionRenderer.renderBlockOutlines(event);

        if (event.getStage() == Stage.AFTER_SKY) {
            long timeOfDay = mc.level.getDayTime() % 24000;

            if (timeOfDay >= 13000 && timeOfDay < 23000) {
                SkyBoxRenderer.renderSkyBoxEffect(event);
            }
        }
    }

    @SubscribeEvent
    public static void onResourceReload(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<>() {
            @Override
            protected @NotNull Object prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(@NotNull Object object, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profiler) {
                OreVisionColorData.loadConfig();
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType<? extends AbsorbedBlockEntity>) EntityRegistry.ABSORBED_BLOCK_ENTITY.get(), AbsorbedBlockRenderer::new);
        event.registerEntityRenderer((EntityType<? extends BlackHoleEntity>) EntityRegistry.BLACK_HOLE_ENTITY.get(), BlackHoleRenderer::new);
    }
}