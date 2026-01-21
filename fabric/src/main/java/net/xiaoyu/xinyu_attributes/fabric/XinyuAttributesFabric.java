package net.xiaoyu.xinyu_attributes.fabric;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.*;

public class XinyuAttributesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AttributesRegistry.ATTRIBUTES.register();
        MobEffectRegistry.MOB_EFFECTS.register();
        EntityRegistry.ENTITIES.register();
    }
}