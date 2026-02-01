package net.xiaoyu.xinyu_attributes.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class Config {
    public static ClientConfig CONFIG;

    @ExpectPlatform
    public static void initializeConfig() {
        throw new AssertionError();
    }
}