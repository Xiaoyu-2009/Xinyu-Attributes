package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.xiaoyu.xinyu_attributes.util.ResistanceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "hurt", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyDamageAmount(float originalAmount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;

        float amountAfterResistance = ResistanceUtil.applyResistance(entity, source, originalAmount);
        double damageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_DAMAGE_LIMIT);
        
        if (!entity.getAttribute(AttributesRegistry.SINGLE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            damageLimit > 0 && amountAfterResistance > damageLimit) {
            return (float) damageLimit;
        }
        
        return amountAfterResistance;
    }
    
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        float amountAfterResistance = ResistanceUtil.applyResistance(entity, source, amount);
        double damageLimit = entity.getAttributeValue(AttributesRegistry.SINGLE_DAMAGE_LIMIT);

        if (!entity.getAttribute(AttributesRegistry.SINGLE_DAMAGE_LIMIT).getModifiers().isEmpty() && 
            damageLimit <= 0.0 || amountAfterResistance <= 0.0) {
            cir.cancel();
        }
    }
}