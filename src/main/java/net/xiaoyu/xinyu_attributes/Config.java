package net.xiaoyu.xinyu_attributes;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.*;

@SuppressWarnings("deprecation")
public class Config {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    
    public static final ModConfigSpec.DoubleValue CLIMBING_UP_SPEED_VALUE;
    public static final ModConfigSpec.DoubleValue CLIMBING_DOWN_SPEED_VALUE;
    public static final ModConfigSpec.DoubleValue PROJECTILE_BOUNCE_RANGE;
    public static final ModConfigSpec.DoubleValue GRAVITY_SUPPRESSION_FORCE;
    public static final ModConfigSpec.IntValue CROP_GROWTH_TICK_DELAY;
    public static final ModConfigSpec.IntValue ORE_VISION_UPDATE_TICK_INTERVAL;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ORE_VISION_BLOCK_BLACKLIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> NEGATIVE_EFFECT_IMMUNITY_WHITELIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> NEGATIVE_EFFECT_IMMUNITY_BLACKLIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> POSITIVE_EFFECT_IMMUNITY_WHITELIST;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> POSITIVE_EFFECT_IMMUNITY_BLACKLIST;
    public static final ModConfigSpec.IntValue BLACK_HOLE_ABSORPTION_TIME;
    
    static {
        BUILDER.push("Buff Config");

        CLIMBING_UP_SPEED_VALUE = BUILDER
            .defineInRange("climbing_up_speed_value", 0.5, -Double.MAX_VALUE, Double.MAX_VALUE);

        CLIMBING_DOWN_SPEED_VALUE = BUILDER
            .defineInRange("climbing_down_speed_value", 0.5, -Double.MAX_VALUE, Double.MAX_VALUE);

        PROJECTILE_BOUNCE_RANGE = BUILDER
            .defineInRange("projectile_bounce_range", 2, 0, Double.MAX_VALUE);

        CROP_GROWTH_TICK_DELAY = BUILDER
            .defineInRange("crop_growth_tick_delay", 32, 0, Integer.MAX_VALUE);

        ORE_VISION_UPDATE_TICK_INTERVAL = BUILDER
            .defineInRange("ore_vision_update_tick_interval", 20, 0, Integer.MAX_VALUE);

        ORE_VISION_BLOCK_BLACKLIST = BUILDER
            .defineListAllowEmpty("ore_vision_block_blacklist", new ArrayList<>(), obj -> obj instanceof String);
        
        NEGATIVE_EFFECT_IMMUNITY_WHITELIST = BUILDER
            .defineListAllowEmpty("negative_effect_immunity_whitelist", new ArrayList<>(), obj -> obj instanceof String);

        NEGATIVE_EFFECT_IMMUNITY_BLACKLIST = BUILDER
            .defineListAllowEmpty("negative_effect_immunity_blacklist", new ArrayList<>(), obj -> obj instanceof String);

        POSITIVE_EFFECT_IMMUNITY_WHITELIST = BUILDER
            .defineListAllowEmpty("positive_effect_immunity_whitelist", new ArrayList<>(), obj -> obj instanceof String);

        POSITIVE_EFFECT_IMMUNITY_BLACKLIST = BUILDER
            .defineListAllowEmpty("positive_effect_immunity_blacklist", new ArrayList<>(), obj -> obj instanceof String);
        
        BLACK_HOLE_ABSORPTION_TIME = BUILDER
            .defineInRange("black_hole_absorption_time", 40, 0, Integer.MAX_VALUE);

        GRAVITY_SUPPRESSION_FORCE = BUILDER
            .defineInRange("gravity_suppression_force", 0.08, -Double.MAX_VALUE, Double.MAX_VALUE);
        
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}