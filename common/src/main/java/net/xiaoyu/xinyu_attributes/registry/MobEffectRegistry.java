package net.xiaoyu.xinyu_attributes.registry;

import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import dev.architectury.registry.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.*;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(XinyuAttributes.MOD_ID, Registries.MOB_EFFECT);

    // 穿墙
    public static final RegistrySupplier<MobEffect> PHASING = MOB_EFFECTS.register("phasing", 
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x808080) {});

    // 水面行走
    public static final RegistrySupplier<MobEffect> WATER_WALKING = MOB_EFFECTS.register("water_walking", 
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x3399ff) {});

    // 攀爬速度
    public static final RegistrySupplier<MobEffect> CLIMBING_SPEED = MOB_EFFECTS.register("climbing_speed",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xffd700) {});

    // 弹射物反弹
    public static final RegistrySupplier<MobEffect> PROJECTILE_BOUNCE = MOB_EFFECTS.register("projectile_bounce",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x808080) {});

    // 作物生长
    public static final RegistrySupplier<MobEffect> CROP_GROWTH = MOB_EFFECTS.register("crop_growth",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x7cfc00) {});

    // 矿物透视
    public static final RegistrySupplier<MobEffect> ORE_VISION = MOB_EFFECTS.register("ore_vision",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x808080) {});

    // 自动收割
    public static final RegistrySupplier<MobEffect> AUTO_HARVEST = MOB_EFFECTS.register("auto_harvest",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x32CD32) {});

    // 黑洞吸取
    public static final RegistrySupplier<MobEffect> BLACK_HOLE_ABSORPTION = MOB_EFFECTS.register("black_hole_absorption",
        () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x000000) {});
}