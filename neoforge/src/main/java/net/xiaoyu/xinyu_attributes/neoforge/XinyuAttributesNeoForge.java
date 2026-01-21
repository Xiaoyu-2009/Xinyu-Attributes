package net.xiaoyu.xinyu_attributes.neoforge;

import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.neoforged.fml.common.Mod;

@Mod(XinyuAttributes.MOD_ID)
public class XinyuAttributesNeoForge {
    
    public XinyuAttributesNeoForge() {
        AttributesRegistry.ATTRIBUTES.register();
        MobEffectRegistry.MOB_EFFECTS.register();
        EntityRegistry.ENTITIES.register();
    }
}