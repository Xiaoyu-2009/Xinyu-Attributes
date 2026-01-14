package net.xiaoyu.xinyu_attributes.registry;

import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.xiaoyu.xinyu_attributes.entity.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.registries.*;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, XinYuAttributes.MOD_ID);

    public static final DeferredHolder<EntityType<?>, ?> ABSORBED_BLOCK_ENTITY = ENTITIES.register("absorbed_block_entity",
        () -> EntityType.Builder.of(AbsorbedBlockEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(32)
            .build("absorbed_block_entity"));
}