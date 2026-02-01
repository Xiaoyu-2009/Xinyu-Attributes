package net.xiaoyu.xinyu_attributes.fabric;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.*;
import net.xiaoyu.xinyu_attributes.XinyuAttributes;
import net.xiaoyu.xinyu_attributes.config.ClientConfig;

import java.util.*;

@Config(name = XinyuAttributes.MOD_ID)
public class ConfigImpl implements ConfigData, ClientConfig {

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public double climbing_up_speed_value = 0.5;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public double climbing_down_speed_value = 0.5;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public double projectile_bounce_range = 2.0;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public double gravity_suppression_force = 0.08;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public int crop_growth_tick_delay = 32;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public int ore_vision_update_tick_interval = 20;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public int black_hole_absorption_time = 40;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public int locked_daytime_value = 1000;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public int locked_nighttime_value = 18000;

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public List<String> ore_vision_block_blacklist = new ArrayList<>();

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public List<String> negative_effect_immunity_whitelist = new ArrayList<>();

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public List<String> negative_effect_immunity_blacklist = new ArrayList<>();

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public List<String> positive_effect_immunity_whitelist = new ArrayList<>();

    @ConfigEntry.Category("Buff Config")
    @ConfigEntry.Gui.Tooltip
    public List<String> positive_effect_immunity_blacklist = new ArrayList<>();

    @Override
    public double getClimbingUpSpeedValue() {
        return climbing_up_speed_value;
    }

    @Override
    public double getClimbingDownSpeedValue() {
        return climbing_down_speed_value;
    }

    @Override
    public double getProjectileBounceRange() {
        return projectile_bounce_range;
    }

    @Override
    public double getGravitySuppressionForce() {
        return gravity_suppression_force;
    }

    @Override
    public int getCropGrowthTickDelay() {
        return crop_growth_tick_delay;
    }

    @Override
    public int getOreVisionUpdateTickInterval() {
        return ore_vision_update_tick_interval;
    }

    @Override
    public List<String> getOreVisionBlockBlacklist() {
        return ore_vision_block_blacklist;
    }

    @Override
    public List<String> getNegativeEffectImmunityWhitelist() {
        return negative_effect_immunity_whitelist;
    }

    @Override
    public List<String> getNegativeEffectImmunityBlacklist() {
        return negative_effect_immunity_blacklist;
    }

    @Override
    public List<String> getPositiveEffectImmunityWhitelist() {
        return positive_effect_immunity_whitelist;
    }

    @Override
    public List<String> getPositiveEffectImmunityBlacklist() {
        return positive_effect_immunity_blacklist;
    }

    @Override
    public int getBlackHoleAbsorptionTime() {
        return black_hole_absorption_time;
    }

    @Override
    public int getLockedDaytimeValue() {
        return locked_daytime_value;
    }

    @Override
    public int getLockedNighttimeValue() {
        return locked_nighttime_value;
    }
}