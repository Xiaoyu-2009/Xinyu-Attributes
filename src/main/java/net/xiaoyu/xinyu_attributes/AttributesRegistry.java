package net.xiaoyu.xinyu_attributes;

import net.neoforged.neoforge.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.*;

public class AttributesRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, XinYuAttributes.MOD_ID);

    // 火焰抗性
    public static final DeferredHolder<Attribute, Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("fire_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.fire_resistance", 0.0, 0.0, 1.0)
    );

    // 冰冻抗性
    public static final DeferredHolder<Attribute, Attribute> FREEZE_RESISTANCE = ATTRIBUTES.register("freeze_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.freeze_resistance", 0.0, 0.0, 1.0)
    );

    // 毒素抗性
    public static final DeferredHolder<Attribute, Attribute> POISON_RESISTANCE = ATTRIBUTES.register("poison_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.poison_resistance", 0.0, 0.0, 1.0)
    );

    // 凋零抗性
    public static final DeferredHolder<Attribute, Attribute> WITHER_RESISTANCE = ATTRIBUTES.register("wither_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.wither_resistance", 0.0, 0.0, 1.0)
    );

    // 声波抗性
    public static final DeferredHolder<Attribute, Attribute> SONIC_RESISTANCE = ATTRIBUTES.register("sonic_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.sonic_resistance", 0.0, 0.0, 1.0)
    );

    // 单次伤害上限
    public static final DeferredHolder<Attribute, Attribute> SINGLE_DAMAGE_LIMIT = ATTRIBUTES.register("single_damage_limit",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.single_damage_limit", 0.0, 0.0, Double.MAX_VALUE)
    );

    // 穿墙
    public static final DeferredHolder<Attribute, Attribute> PHASING = ATTRIBUTES.register("phasing",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.phasing", 0.0, 0.0, 0.0)
    );
}