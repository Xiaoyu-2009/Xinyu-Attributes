package net.xiaoyu.xinyu_attributes;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    
    public static final ModConfigSpec.DoubleValue CLIMBING_UP_SPEED_VALUE;
    public static final ModConfigSpec.DoubleValue CLIMBING_DOWN_SPEED_VALUE;
    public static final ModConfigSpec.DoubleValue PROJECTILE_BOUNCE_RANGE;
    
    static {
        BUILDER.push("Buff Config");
        
        CLIMBING_UP_SPEED_VALUE = BUILDER
            .defineInRange("climbing_up_speed_value", 0.5, -Double.MAX_VALUE, Double.MAX_VALUE);
        
        CLIMBING_DOWN_SPEED_VALUE = BUILDER
            .defineInRange("climbing_down_speed_value", 0.5, -Double.MAX_VALUE, Double.MAX_VALUE);
        
        PROJECTILE_BOUNCE_RANGE = BUILDER
            .defineInRange("projectile_bounce_range", 2, 0, Double.MAX_VALUE);
        
        BUILDER.pop();
        
        SPEC = BUILDER.build();
    }
}