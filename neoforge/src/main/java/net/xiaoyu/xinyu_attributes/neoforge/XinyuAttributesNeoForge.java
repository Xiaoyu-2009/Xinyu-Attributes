package net.xiaoyu.xinyu_attributes.neoforge;

import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

@Mod(XinyuAttributes.MOD_ID)
public class XinyuAttributesNeoForge {
    
    public XinyuAttributesNeoForge(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ConfigImpl.SPEC);
        ConfigImpl.initializeConfig();
        AttributesRegistry.ATTRIBUTES.register();
        MobEffectRegistry.MOB_EFFECTS.register();
        EntityRegistry.ENTITIES.register();
    }
}