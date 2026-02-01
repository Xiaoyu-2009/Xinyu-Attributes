package net.xiaoyu.xinyu_attributes.util;

import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;

public class ResistanceUtil {
    public static float applyFireResistance(LivingEntity entity, DamageSource source, float originalAmount) {
        // 火焰抗性
        if (source.is(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XinyuAttributes.MOD_ID, "is_fire"))) ||
            source.is(DamageTypeTags.IS_FIRE)) {
            double fireResistance = entity.getAttributeValue(AttributesRegistry.FIRE_RESISTANCE);
            if(fireResistance >= 1.0) {
                entity.clearFire();
            }
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, fireResistance)));
        }
        
        return originalAmount;
    }
}