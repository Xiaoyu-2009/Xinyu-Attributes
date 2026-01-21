package net.xiaoyu.xinyu_attributes.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu.xinyu_attributes.config.Config;
import net.xiaoyu.xinyu_attributes.registry.*;

public class XinyuAttributesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigImpl.class, Toml4jConfigSerializer::new);
        Config.CONFIG = AutoConfig.getConfigHolder(ConfigImpl.class).getConfig();
        
        AttributesRegistry.ATTRIBUTES.register();
        MobEffectRegistry.MOB_EFFECTS.register();
        EntityRegistry.ENTITIES.register();
    }
}