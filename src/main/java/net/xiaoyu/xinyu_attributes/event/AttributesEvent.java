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
            // 火焰抗性
            event.add(entityType, AttributesRegistry.FIRE_RESISTANCE);
            // 冰冻抗性
            event.add(entityType, AttributesRegistry.FREEZE_RESISTANCE);
            // 毒素抗性
            event.add(entityType, AttributesRegistry.POISON_RESISTANCE);
            // 凋零抗性
            event.add(entityType, AttributesRegistry.WITHER_RESISTANCE);
            // 声波抗性
            event.add(entityType, AttributesRegistry.SONIC_RESISTANCE);
            // 单次伤害上限
            event.add(entityType, AttributesRegistry.SINGLE_DAMAGE_LIMIT);
        }
    }
}