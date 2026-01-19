package net.xiaoyu.xinyu_attributes.registry;

import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.neoforged.neoforge.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.*;

public class AttributesRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, XinYuAttributes.MOD_ID);

    // 火焰抗性
    public static final DeferredHolder<Attribute, Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("fire_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".fire_resistance", 0, 0, 1)
            .setSyncable(true));

    // 冰冻抗性
    public static final DeferredHolder<Attribute, Attribute> FREEZE_RESISTANCE = ATTRIBUTES.register("freeze_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".freeze_resistance", 0, 0, 1)
            .setSyncable(true));

    // 毒素抗性
    public static final DeferredHolder<Attribute, Attribute> POISON_RESISTANCE = ATTRIBUTES.register("poison_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".poison_resistance", 0, 0, 1)
            .setSyncable(true));

    // 凋零抗性
    public static final DeferredHolder<Attribute, Attribute> WITHER_RESISTANCE = ATTRIBUTES.register("wither_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".wither_resistance", 0, 0, 1)
        .setSyncable(true));

    // 声波抗性
    public static final DeferredHolder<Attribute, Attribute> SONIC_RESISTANCE = ATTRIBUTES.register("sonic_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".sonic_resistance", 0, 0, 1)
            .setSyncable(true));

    // 弹射物抗性
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_RESISTANCE = ATTRIBUTES.register("projectile_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".projectile_resistance", 0, 0, 1)
            .setSyncable(true));

    // 单次伤害上限
    public static final DeferredHolder<Attribute, Attribute> SINGLE_DAMAGE_LIMIT = ATTRIBUTES.register("single_damage_limit",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".single_damage_limit", 0, 0, Double.MAX_VALUE)
            .setSyncable(true));

    // 单次百分比伤害上限
    public static final DeferredHolder<Attribute, Attribute> SINGLE_PERCENTAGE_DAMAGE_LIMIT = ATTRIBUTES.register("single_percentage_damage_limit",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".single_percentage_damage_limit", 0, 0, 1)
            .setSyncable(true));

    // 弹射物速度
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_SPEED = ATTRIBUTES.register("projectile_speed",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".projectile_speed", 0, -Double.MAX_VALUE, Double.MAX_VALUE)
            .setSyncable(true));

    // 负面效果免疫
    public static final DeferredHolder<Attribute, Attribute> NEGATIVE_EFFECT_IMMUNITY = ATTRIBUTES.register("negative_effect_immunity",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".negative_effect_immunity", 0, 0, 1)
            .setSyncable(true));

    // 正面效果免疫
    public static final DeferredHolder<Attribute, Attribute> POSITIVE_EFFECT_IMMUNITY = ATTRIBUTES.register("positive_effect_immunity",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".positive_effect_immunity", 0, 0, 1)
            .setSyncable(true));

    // 破盾
    public static final DeferredHolder<Attribute, Attribute> SHIELD_BREAK = ATTRIBUTES.register("shield_break",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".shield_break", 0, 0, 1)
            .setSyncable(true));

    // 自动破坏
    public static final DeferredHolder<Attribute, Attribute> AUTO_DESTROY = ATTRIBUTES.register("auto_destroy",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".auto_destroy", 0, 0, Double.MAX_VALUE)
            .setSyncable(true));

    // 生命吸取
    public static final DeferredHolder<Attribute, Attribute> LIFE_ABSORPTION = ATTRIBUTES.register("life_absorption",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".life_absorption", 0, 0, 1)
            .setSyncable(true));

    // 击杀掉落翻倍
    public static final DeferredHolder<Attribute, Attribute> KILL_DROP_MULTIPLIER = ATTRIBUTES.register("kill_drop_multiplier",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".kill_drop_multiplier", 0, 0, Double.MAX_VALUE)
            .setSyncable(true));

    // 挖掘掉落翻倍
    public static final DeferredHolder<Attribute, Attribute> MINING_DROP_MULTIPLIER = ATTRIBUTES.register("mining_drop_multiplier",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".mining_drop_multiplier", 0, 0, Double.MAX_VALUE)
            .setSyncable(true));

    // 重力压制
    public static final DeferredHolder<Attribute, Attribute> GRAVITY_SUPPRESSION = ATTRIBUTES.register("gravity_suppression",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".gravity_suppression", 0, 0, Double.MAX_VALUE)
            .setSyncable(true));

    // 永久着火
    public static final DeferredHolder<Attribute, Attribute> PERMANENT_BURNING = ATTRIBUTES.register("permanent_burning",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".permanent_burning", 0, 0, 0)
            .setSyncable(true));

    // 锁定白天
    public static final DeferredHolder<Attribute, Attribute> LOCK_DAYTIME = ATTRIBUTES.register("lock_daytime",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".lock_daytime", 0, 0, 0)
            .setSyncable(true));

    // 锁定黑夜
    public static final DeferredHolder<Attribute, Attribute> LOCK_NIGHTTIME = ATTRIBUTES.register("lock_nighttime",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".lock_nighttime", 0, 0, 0)
            .setSyncable(true));
}