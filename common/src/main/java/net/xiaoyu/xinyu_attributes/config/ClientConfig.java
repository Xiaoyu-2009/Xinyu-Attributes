package net.xiaoyu.xinyu_attributes.config;

import java.util.List;

public interface ClientConfig {
    double getClimbingUpSpeedValue();
    double getClimbingDownSpeedValue();
    double getProjectileBounceRange();
    double getGravitySuppressionForce();
    int getCropGrowthTickDelay();
    int getOreVisionUpdateTickInterval();
    List<String> getOreVisionBlockBlacklist();
    List<String> getNegativeEffectImmunityWhitelist();
    List<String> getNegativeEffectImmunityBlacklist();
    List<String> getPositiveEffectImmunityWhitelist();
    List<String> getPositiveEffectImmunityBlacklist();
    int getBlackHoleAbsorptionTime();
    int getLockedDaytimeValue();
    int getLockedNighttimeValue();
}