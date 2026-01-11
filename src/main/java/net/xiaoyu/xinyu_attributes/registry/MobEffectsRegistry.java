package net.xiaoyu.xinyu_attributes.registry;

import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.minecraft.core.registries.*;
import net.neoforged.neoforge.registries.*;
import net.minecraft.world.effect.*;

public class MobEffectsRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, XinYuAttributes.MOD_ID);

    // 穿墙
    public static final DeferredHolder<MobEffect, MobEffect> PHASING = MOB_EFFECTS.register("phasing", 
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x808080) {});

    // 水面行走
    public static final DeferredHolder<MobEffect, MobEffect> WATER_WALKING = MOB_EFFECTS.register("water_walking", 
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x3399ff) {});

    // 攀爬速度
    public static final DeferredHolder<MobEffect, MobEffect> CLIMBING_SPEED = MOB_EFFECTS.register("climbing_speed",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xffd700) {});

    // 弹射物反弹
    public static final DeferredHolder<MobEffect, MobEffect> PROJECTILE_BOUNCE = MOB_EFFECTS.register("projectile_bounce",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x808080) {});

    // 作物生长
    public static final DeferredHolder<MobEffect, MobEffect> CROP_GROWTH = MOB_EFFECTS.register("crop_growth",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x7cfc00) {});
    // 矿物透视
    public static final DeferredHolder<MobEffect, MobEffect> ORE_VISION = MOB_EFFECTS.register("ore_vision",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x7cfc00) {});
}