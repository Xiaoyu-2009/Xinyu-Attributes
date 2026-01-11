package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.Config;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.util.ResistanceUtil;
import net.minecraft.world.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "hurt", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamageAmount(float originalAmount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;

        float amountAfterResistance = ResistanceUtil.applyResistance(entity, source, originalAmount);
        double fixedDamageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_DAMAGE_LIMIT);
        double percentageDamageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_PERCENTAGE_DAMAGE_LIMIT);

        if (!entity.getAttribute(AttributesRegistry.SINGLE_DAMAGE_LIMIT).getModifiers().isEmpty() &&
            fixedDamageLimit >= 0 && amountAfterResistance > fixedDamageLimit) {
            return (float) fixedDamageLimit;
        }

        if (!entity.getAttribute(AttributesRegistry.SINGLE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            fixedDamageLimit > entity.getMaxHealth()) {
            entity.setHealth(0);
            //entity.setRemoved(Entity.RemovalReason.KILLED);
        }
        
        if (!entity.getAttribute(AttributesRegistry.SINGLE_PERCENTAGE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            percentageDamageLimit >= 0 && percentageDamageLimit <= 1) {
            float maxDamageByPercentage = (float) (entity.getMaxHealth() * percentageDamageLimit);
            
            if (amountAfterResistance > maxDamageByPercentage) {
                amountAfterResistance = maxDamageByPercentage;
            }
        }
        
        return amountAfterResistance;
    }
    
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        float amountAfterResistance = ResistanceUtil.applyResistance(entity, source, amount);
        double damageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_DAMAGE_LIMIT);
        double percentageDamageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_PERCENTAGE_DAMAGE_LIMIT);

        if (!entity.getAttribute(AttributesRegistry.SINGLE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            damageLimit <= 0) {
            cir.cancel();
        }

        if (!entity.getAttribute(AttributesRegistry.SINGLE_PERCENTAGE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            percentageDamageLimit <= 0) {
            cir.cancel();
        }

        if (amountAfterResistance <= 0) {
            cir.cancel();
        }
    }
    
    @ModifyArg(
        method = "handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V", 
            ordinal = 0
        ),
        index = 1
    )
    private double modifyClimbingUpSpeed(double vanillaClimbSpeed) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(MobEffectsRegistry.CLIMBING_SPEED)) {
            return Config.CLIMBING_SPEED_VALUE.get();
        }

        return vanillaClimbSpeed;
    }
}