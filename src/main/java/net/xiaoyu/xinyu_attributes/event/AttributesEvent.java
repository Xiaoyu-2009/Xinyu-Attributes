package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.registry.*;
import net.minecraft.world.entity.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.*;

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
            // 弹射物抗性
            event.add(entityType, AttributesRegistry.PROJECTILE_RESISTANCE);
            // 单次伤害上限
            event.add(entityType, AttributesRegistry.SINGLE_DAMAGE_LIMIT);
            // 单次百分比伤害上限
            event.add(entityType, AttributesRegistry.SINGLE_PERCENTAGE_DAMAGE_LIMIT);
            // 弹射物速度
            event.add(entityType, AttributesRegistry.PROJECTILE_SPEED);
            // 负面效果免疫
            event.add(entityType, AttributesRegistry.NEGATIVE_EFFECT_IMMUNITY);
            // 正面效果免疫
            event.add(entityType, AttributesRegistry.POSITIVE_EFFECT_IMMUNITY);
            // 破盾
            event.add(entityType, AttributesRegistry.SHIELD_BREAK);
            // 自动破坏
            event.add(entityType, AttributesRegistry.AUTO_DESTROY);
            // 生命吸取
            event.add(entityType, AttributesRegistry.LIFE_DRAIN);
        }
    }
}