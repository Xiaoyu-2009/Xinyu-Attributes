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
                // No modifiers, use default flying speed
                player.getAbilities().setFlyingSpeed(0.05F);
            } else {
                // Has modifiers, use custom flying speed
                player.getAbilities().setFlyingSpeed((float) flyingSpeedAttribute.getValue());
            }

            player.onUpdateAbilities();
        }
    }
}