package net.xiaoyu.xinyu_attributes.client.renderer;

import com.mojang.blaze3d.vertex.*;
import net.xiaoyu.xinyu_attributes.data.*;
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
import net.neoforged.neoforge.client.event.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class OreVisionRenderer {
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

    public static void renderBlockOutlines(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (!player.hasEffect(MobEffectRegistry.ORE_VISION)) return;
        
        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();
        Level level = player.level();
        
        for (BlockPos pos : OreVisionDataPacket.getClientOrePositions()) {
            BlockState blockState = level.getBlockState(pos);
            VoxelShape shape = blockState.getCollisionShape(level, pos, CollisionContext.empty());
            
            if (shape.isEmpty()) continue;

            AABB aabb = shape.bounds().move(pos);
            float red, green, blue;
            float[] color = null;

            color = OreVisionColorData.getColorForBlock(level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(blockState.getBlock()));
            
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