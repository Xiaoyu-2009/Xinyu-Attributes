package net.xiaoyu.xinyu_attributes.client.renderer;

import com.mojang.blaze3d.vertex.*;
import net.xiaoyu.xinyu_attributes.network.*;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class OreVisionRenderer {
    private static final Map<String, float[]> COLOR_CACHE = new HashMap<>();
    private static final RenderType TRANSLUCENT_LINES = RenderType.create("ore_vision_lines",
        DefaultVertexFormat.POSITION_COLOR_NORMAL,
        VertexFormat.Mode.LINES,
        RenderType.SMALL_BUFFER_SIZE,
        false,
        true,
        RenderType.CompositeState.builder()
            .setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
            .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
            .setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
            .setOutputState(RenderType.ITEM_ENTITY_TARGET)
            .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
            .setCullState(RenderType.NO_CULL)
            .setDepthTestState(RenderType.NO_DEPTH_TEST)
            .createCompositeState(false)
    );
    
    static {
        COLOR_CACHE.put("coal", new float[] { 0.2F, 0.2F, 0.2F }); // 煤炭矿 - 深灰色
        COLOR_CACHE.put("iron", new float[] { 1.0F, 0.7F, 0.4F }); // 铁矿 - 棕黄色
        COLOR_CACHE.put("copper", new float[] { 1.0F, 0.5F, 0.2F }); // 铜矿 - 橙红色
        COLOR_CACHE.put("gold", new float[] { 1.0F, 0.8F, 0.0F }); // 金矿 - 金黄色
        COLOR_CACHE.put("diamond", new float[] { 0.2F, 0.8F, 1.0F }); // 钻石矿 - 蓝色
        COLOR_CACHE.put("emerald", new float[] { 0.0F, 0.8F, 0.2F }); // 绿宝石矿 - 绿色
        COLOR_CACHE.put("lapis", new float[] { 0.2F, 0.4F, 1.0F }); // 青金石矿 - 深蓝色
        COLOR_CACHE.put("redstone", new float[] { 1.0F, 0.0F, 0.0F }); // 红石矿 - 红色
        COLOR_CACHE.put("quartz", new float[] { 0.9F, 0.9F, 0.9F }); // 下界石英矿 - 浅白色
        COLOR_CACHE.put("netherite_scrap", new float[] { 0.4F, 0.3F, 0.3F }); // 下界合金矿 - 深灰色
    }

    public static void renderBlockOutlines(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (!player.hasEffect(MobEffectsRegistry.ORE_VISION)) return;

        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        Level level = player.level();
        
        for (BlockPos pos : OreVisionDataPacket.getClientOrePositions()) {
            BlockState blockState = level.getBlockState(pos);
            VoxelShape shape = blockState.getCollisionShape(level, pos, CollisionContext.empty());
            
            if (shape.isEmpty()) continue;

            AABB aabb = shape.bounds().move(pos);
            float red, green, blue;
            float[] color = null;

            for (Map.Entry<String, float[]> entry : COLOR_CACHE.entrySet()) {
                if (level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(blockState.getBlock()).getPath().contains(entry.getKey())) {
                    color = entry.getValue();
                    break;
                }
            }
            if (color != null) {
                red = color[0];
                green = color[1];
                blue = color[2];
            } else {
                red = 0.0F; green = 1.0F; blue = 0.0F;
            }
            
            LevelRenderer.renderLineBox(
                event.getPoseStack(), 
                mc.renderBuffers().bufferSource().getBuffer(TRANSLUCENT_LINES), 
                new AABB(
                    aabb.minX - cameraPos.x,
                    aabb.minY - cameraPos.y,
                    aabb.minZ - cameraPos.z,
                    aabb.maxX - cameraPos.x,
                    aabb.maxY - cameraPos.y,
                    aabb.maxZ - cameraPos.z
                ), red, green, blue, 0.7f
            );
        }
    }
}