package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class FlyingSpeedEvent {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.getAbilities().flying) {
            AttributeInstance flyingSpeedAttribute = player.getAttribute(AttributesRegistry.FLYING_SPEED);

            if (flyingSpeedAttribute.getModifiers().isEmpty()) {
                player.getAbilities().setFlyingSpeed(0.05F);
            } else {
                player.getAbilities().setFlyingSpeed((float) flyingSpeedAttribute.getValue());
            }

            player.onUpdateAbilities();
        }
    }
}