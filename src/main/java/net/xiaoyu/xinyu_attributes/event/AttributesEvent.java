package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber
public class AttributesEvent {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
            event.add(entityType, AttributesRegistry.FLYING_SPEED);
        }
    }
}