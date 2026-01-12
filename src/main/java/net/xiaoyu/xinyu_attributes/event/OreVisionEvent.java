package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.xiaoyu.xinyu_attributes.Config;
import net.xiaoyu.xinyu_attributes.network.*;
import net.xiaoyu.xinyu_attributes.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.*;

import java.util.*;

@EventBusSubscriber
public class OreVisionEvent {
    private static final Map<UUID, Integer> playerLastUpdateTick = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        long currentTick = event.getEntity().level().getGameTime();
        UUID playerId = player.getUUID();
        Integer lastUpdate = playerLastUpdateTick.get(playerId);
        
        if (lastUpdate != null && (currentTick - lastUpdate) < Config.ORE_VISION_UPDATE_TICK_INTERVAL.get()) {
            if (!player.hasEffect(MobEffectsRegistry.ORE_VISION)) {
                if (lastUpdate != -1) {
                    player.connection.send(new OreVisionDataPacket(new ArrayList<>()));
                    playerLastUpdateTick.put(playerId, -1);
                }
            }
            return;
        }

        if (!player.hasEffect(MobEffectsRegistry.ORE_VISION)) {
            player.connection.send(new OreVisionDataPacket(new ArrayList<>()));
            playerLastUpdateTick.put(playerId, -1);
            return;
        }

        playerLastUpdateTick.put(playerId, (int) currentTick);

        int renderDistance = player.getEffect(MobEffectsRegistry.ORE_VISION).getAmplifier() + 1;
        if (renderDistance < 0) renderDistance = 0;

        List<BlockPos> orePositions = new ArrayList<>();

        BlockPos playerPos = player.blockPosition();
        int playerX = playerPos.getX();
        int playerY = playerPos.getY();
        int playerZ = playerPos.getZ();

        for (int x = -renderDistance; x <= renderDistance; x++) {
            for (int y = -renderDistance; y <= renderDistance; y++) {
                for (int z = -renderDistance; z <= renderDistance; z++) {
                    BlockPos pos = new BlockPos(playerX + x, playerY + y, playerZ + z);
                    BlockState blockState = player.level().getBlockState(pos);

                    if (Config.ORE_VISION_BLOCK_BLACKLIST.get().contains(BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString())) continue;

                    if (blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "ores"))) || 
                        blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores")))) {
                        orePositions.add(pos);
                    }
                }
            }
        }

        player.connection.send(new OreVisionDataPacket(orePositions));
    }

    @SubscribeEvent @OnlyIn(Dist.CLIENT)
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
}