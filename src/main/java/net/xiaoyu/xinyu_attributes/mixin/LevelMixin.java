package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.Config;
import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin {

    /**
     * 最底层的时间推进拦截：覆盖advanceDaytime方法
     * 确保时间增量为0，从根本上防止时间流逝
     */
    @Inject(
        method = "advanceDaytime", 
        at = @At("HEAD"),
        cancellable = true
    )
    private void onAdvanceDaytime(CallbackInfoReturnable<Long> cir) {
        Level level = (Level) (Object) this;
        
        // 检查是否存在需要锁定时间的实体（仅在服务端级别）
        if (level instanceof net.minecraft.server.level.ServerLevel) {
            net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) level;
            
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    if ((!livingEntity.getAttribute(AttributesRegistry.LOCK_DAYTIME).getModifiers().isEmpty() &&
                        livingEntity.getAttributeValue(AttributesRegistry.LOCK_DAYTIME) >= 0) ||
                        (!livingEntity.getAttribute(AttributesRegistry.LOCK_NIGHTTIME).getModifiers().isEmpty() &&
                        livingEntity.getAttributeValue(AttributesRegistry.LOCK_NIGHTTIME) >= 0)) {
                            // 如果需要锁定时间，返回0表示时间不前进
                            cir.setReturnValue(0L);
                            return;
                    }
                }
            }
        }
        
        // 否则正常执行原方法
    }
}