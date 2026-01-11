package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.xiaoyu.xinyu_attributes.network.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

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
        
        if (lastUpdate != null && (currentTick - lastUpdate) < 20) {
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

                    if (blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"))) || 
                        blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "ores")))) {
                        orePositions.add(pos);
                    }
                }
            }
        }

        player.connection.send(new OreVisionDataPacket(orePositions));
    }
}