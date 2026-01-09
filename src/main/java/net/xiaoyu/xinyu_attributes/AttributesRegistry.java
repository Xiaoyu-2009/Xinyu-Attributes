package net.xiaoyu.xinyu_attributes;

import net.neoforged.neoforge.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.*;

public class AttributesRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, XinYuAttributes.MOD_ID);

    // 火焰抗性
    public static final DeferredHolder<Attribute, Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("fire_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".fire_resistance", 0.0, 0.0, 1.0)
    );

    // 冰冻抗性
    public static final DeferredHolder<Attribute, Attribute> FREEZE_RESISTANCE = ATTRIBUTES.register("freeze_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".freeze_resistance", 0.0, 0.0, 1.0)
    );

    // 毒素抗性
    public static final DeferredHolder<Attribute, Attribute> POISON_RESISTANCE = ATTRIBUTES.register("poison_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".poison_resistance", 0.0, 0.0, 1.0)
    );

    // 凋零抗性
    public static final DeferredHolder<Attribute, Attribute> WITHER_RESISTANCE = ATTRIBUTES.register("wither_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".wither_resistance", 0.0, 0.0, 1.0)
    );

    // 声波抗性
    public static final DeferredHolder<Attribute, Attribute> SONIC_RESISTANCE = ATTRIBUTES.register("sonic_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".sonic_resistance", 0.0, 0.0, 1.0)
    );

    // 弹射物抗性
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_RESISTANCE = ATTRIBUTES.register("projectile_resistance",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".projectile_resistance", 0.0, 0.0, 1.0)
    );

    // 单次伤害上限
    public static final DeferredHolder<Attribute, Attribute> SINGLE_DAMAGE_LIMIT = ATTRIBUTES.register("single_damage_limit",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".single_damage_limit", 0.0, 0.0, Double.MAX_VALUE)
    );

    // 单次百分比伤害上限
    public static final DeferredHolder<Attribute, Attribute> SINGLE_PERCENTAGE_DAMAGE_LIMIT = ATTRIBUTES.register("single_percentage_damage_limit",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".single_percentage_damage_limit", 0.0, 0.0, 1.0)
    );

    // 穿墙
    public static final DeferredHolder<Attribute, Attribute> PHASING = ATTRIBUTES.register("phasing",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".phasing", 0.0, 0.0, 0.0)
    );

    // 弹射物速度
    public static final DeferredHolder<Attribute, Attribute> PROJECTILE_SPEED = ATTRIBUTES.register("projectile_speed",
        () -> new RangedAttribute("attribute.name."+ XinYuAttributes.MOD_ID +".projectile_speed", 0.0, -Double.MAX_VALUE, Double.MAX_VALUE)
    );
}