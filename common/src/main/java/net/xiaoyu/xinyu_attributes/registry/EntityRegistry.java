package net.xiaoyu.xinyu_attributes.registry;

import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.entity.*;
import dev.architectury.registry.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(XinyuAttributes.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<?>> ABSORBED_BLOCK_ENTITY = ENTITIES.register("absorbed_block_entity",
        () -> EntityType.Builder.of(AbsorbedBlockEntity::new, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .clientTrackingRange(32)
            .build("absorbed_block_entity"));
    
    public static final RegistrySupplier<EntityType<?>> BLACK_HOLE_ENTITY = ENTITIES.register("black_hole_entity",
        () -> EntityType.Builder.of(BlackHoleEntity::new, MobCategory.MISC)
            .sized(3f, 3f)
            .clientTrackingRange(32)
            .build("black_hole_entity"));
}