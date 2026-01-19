package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.Config;
import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Inject(
        method = "tickTime", 
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V", 
            ordinal = 0
        ), 
        cancellable = true
    )
    private void onSetDayTime(CallbackInfo ci) {
        ServerLevel serverLevel = (ServerLevel) (Object) this;
        
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.getAttribute(AttributesRegistry.LOCK_DAYTIME).getModifiers().isEmpty() &&
                    livingEntity.getAttributeValue(AttributesRegistry.LOCK_DAYTIME) >= 0) {
                    serverLevel.setDayTime(Config.LOCKED_DAYTIME_VALUE.get());
                    ci.cancel();
                    break;
                }
                if (!livingEntity.getAttribute(AttributesRegistry.LOCK_NIGHTTIME).getModifiers().isEmpty() &&
                    livingEntity.getAttributeValue(AttributesRegistry.LOCK_NIGHTTIME) >= 0) {
                    serverLevel.setDayTime(Config.LOCKED_NIGHTTIME_VALUE.get());
                    ci.cancel();
                    break;
                }
            }
        }
    }
}