package net.xiaoyu.xinyu_attributes;

import net.neoforged.neoforge.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.*;

public class AttributesRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, XinYuAttributes.MOD_ID);

    // 飞行速度
    public static final DeferredHolder<Attribute, Attribute> FLYING_SPEED = ATTRIBUTES.register("flying_speed",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.flying_speed", 0.05F, 0.0, Double.MAX_VALUE)
            .setSyncable(true)
    );

    // 火焰抗性
    public static final DeferredHolder<Attribute, Attribute> FIRE_RESISTANCE = ATTRIBUTES.register("fire_resistance",
        () -> new RangedAttribute("attribute.name.xinyu_attributes.fire_resistance", 0.0F, 0.0, 1.0)
            .setSyncable(true)
    );
}