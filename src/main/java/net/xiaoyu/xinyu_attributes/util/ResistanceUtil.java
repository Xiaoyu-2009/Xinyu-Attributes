package net.xiaoyu.xinyu_attributes.util;

import net.xiaoyu.xinyu_attributes.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.LivingEntity;

public class ResistanceUtil {;
    public static float applyResistance(LivingEntity entity, DamageSource source, float originalAmount) {
        // 火焰抗性
        if (source.is(DamageTypeTags.IS_FIRE)) {
            double fireResistance = entity.getAttributeValue(AttributesRegistry.FIRE_RESISTANCE);
            if(fireResistance >= 1.0) {
                entity.clearFire();
            }
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, fireResistance)));
        }
        
        // 冰冻抗性
        if (source.is(DamageTypeTags.IS_FREEZING)) {
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, entity.getAttributeValue(AttributesRegistry.FREEZE_RESISTANCE))));
        }
        
        // 毒素抗性
        if (source.is(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "is_poison")))) {
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, entity.getAttributeValue(AttributesRegistry.POISON_RESISTANCE))));
        }
        
        // 声波抗性
        if (source.is(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "is_sonic")))) {
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, entity.getAttributeValue(AttributesRegistry.SONIC_RESISTANCE))));
        }
        
        // 凋零抗性
        if (source.is(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "is_wither")))) {
            originalAmount = (float) (originalAmount * (1.0 - Math.min(1.0, entity.getAttributeValue(AttributesRegistry.WITHER_RESISTANCE))));
        }
        
        return originalAmount;
    }
}