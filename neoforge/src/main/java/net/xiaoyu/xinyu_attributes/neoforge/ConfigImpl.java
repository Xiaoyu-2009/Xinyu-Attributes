package net.xiaoyu.xinyu_attributes.neoforge;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.xiaoyu.xinyu_attributes.config.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class ConfigImpl implements ClientConfig {
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
    public static final ModConfigSpec.IntValue LOCKED_DAYTIME_VALUE;
    public static final ModConfigSpec.IntValue LOCKED_NIGHTTIME_VALUE;

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
        
        LOCKED_DAYTIME_VALUE = BUILDER
            .defineInRange("locked_daytime_value", 1000, 0, 13000);
            
        LOCKED_NIGHTTIME_VALUE = BUILDER
            .defineInRange("locked_nighttime_value", 18000, 13000, 24000);
        
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @Override
    public double getClimbingUpSpeedValue() {
        return CLIMBING_UP_SPEED_VALUE.get();
    }

    @Override
    public double getClimbingDownSpeedValue() {
        return CLIMBING_DOWN_SPEED_VALUE.get();
    }

    @Override
    public double getProjectileBounceRange() {
        return PROJECTILE_BOUNCE_RANGE.get();
    }

    @Override
    public double getGravitySuppressionForce() {
        return GRAVITY_SUPPRESSION_FORCE.get();
    }

    @Override
    public int getCropGrowthTickDelay() {
        return CROP_GROWTH_TICK_DELAY.get();
    }

    @Override
    public int getOreVisionUpdateTickInterval() {
        return ORE_VISION_UPDATE_TICK_INTERVAL.get();
    }

    @Override
    public List<String> getOreVisionBlockBlacklist() {
        List<? extends String> rawList = ORE_VISION_BLOCK_BLACKLIST.get();
        List<String> stringList = new ArrayList<>();
        for (String s : rawList) {
            stringList.add(s);
        }
        return stringList;
    }

    @Override
    public List<String> getNegativeEffectImmunityWhitelist() {
        List<? extends String> rawList = NEGATIVE_EFFECT_IMMUNITY_WHITELIST.get();
        List<String> stringList = new ArrayList<>();
        for (String s : rawList) {
            stringList.add(s);
        }
        return stringList;
    }

    @Override
    public List<String> getNegativeEffectImmunityBlacklist() {
        List<? extends String> rawList = NEGATIVE_EFFECT_IMMUNITY_BLACKLIST.get();
        List<String> stringList = new ArrayList<>();
        for (String s : rawList) {
            stringList.add(s);
        }
        return stringList;
    }

    @Override
    public List<String> getPositiveEffectImmunityWhitelist() {
        List<? extends String> rawList = POSITIVE_EFFECT_IMMUNITY_WHITELIST.get();
        List<String> stringList = new ArrayList<>();
        for (String s : rawList) {
            stringList.add(s);
        }
        return stringList;
    }

    @Override
    public List<String> getPositiveEffectImmunityBlacklist() {
        List<? extends String> rawList = POSITIVE_EFFECT_IMMUNITY_BLACKLIST.get();
        List<String> stringList = new ArrayList<>();
        for (String s : rawList) {
            stringList.add(s);
        }
        return stringList;
    }

    @Override
    public int getBlackHoleAbsorptionTime() {
        return BLACK_HOLE_ABSORPTION_TIME.get();
    }

    @Override
    public int getLockedDaytimeValue() {
        return LOCKED_DAYTIME_VALUE.get();
    }

    @Override
    public int getLockedNighttimeValue() {
        return LOCKED_NIGHTTIME_VALUE.get();
    }

    public static void initializeConfig() {
        Config.CONFIG = new ConfigImpl();
    }
}