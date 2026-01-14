package net.xiaoyu.xinyu_attributes;

import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.network.*;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.network.event.*;
import org.slf4j.*;

@Mod(XinYuAttributes.MOD_ID)
public class XinYuAttributes {
    public static final String MOD_ID = "xinyu_attributes";
    public static final String MODID = MOD_ID;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public XinYuAttributes(ModContainer modContainer) {
        AttributesRegistry.ATTRIBUTES.register(modContainer.getEventBus());
        MobEffectRegistry.MOB_EFFECTS.register(modContainer.getEventBus());
        EntityRegistry.ENTITIES.register(modContainer.getEventBus());
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modContainer.getEventBus().addListener(XinYuAttributes::registerNetwork);
    }

    private static void registerNetwork(RegisterPayloadHandlersEvent event) {
        event.registrar(MOD_ID).playBidirectional(OreVisionDataPacket.TYPE, OreVisionDataPacket.STREAM_CODEC, OreVisionDataPacket::handle);
    }
}