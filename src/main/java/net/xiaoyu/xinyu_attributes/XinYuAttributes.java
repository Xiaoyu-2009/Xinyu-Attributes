package net.xiaoyu.xinyu_attributes;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModLoadingContext;

@Mod(XinYuAttributes.MOD_ID)
public class XinYuAttributes {
    public static final String MOD_ID = "xinyu_attributes";
    public static final String MODID = MOD_ID;

    public XinYuAttributes() {
        AttributesRegistry.ATTRIBUTES.register(ModLoadingContext.get().getActiveContainer().getEventBus());
    }
}