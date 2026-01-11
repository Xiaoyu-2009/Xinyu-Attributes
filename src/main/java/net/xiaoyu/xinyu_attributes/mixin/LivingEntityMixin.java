package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.*;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.util.ResistanceUtil;
import net.xiaoyu.xinyu_attributes.client.renderer.EvasionAnimationRenderer;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.*;
import net.minecraft.sounds.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

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

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickCheckProjectileBounce(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.getAttribute(AttributesRegistry.NEGATIVE_EFFECT_IMMUNITY).getModifiers().isEmpty() &&
            entity.getAttributeValue(AttributesRegistry.NEGATIVE_EFFECT_IMMUNITY) == 0) {
            List<MobEffectInstance> negativeEffectsToRemove = new ArrayList<>();

            for (MobEffectInstance effect : entity.getActiveEffects()) {
                if (!effect.getEffect().value().isBeneficial()) {
                    negativeEffectsToRemove.add(effect);
                }
            }
            for (MobEffectInstance effect : negativeEffectsToRemove) {
                entity.removeEffect(effect.getEffect());
            }
        }

        if (!entity.getAttribute(AttributesRegistry.POSITIVE_EFFECT_IMMUNITY).getModifiers().isEmpty() &&
            entity.getAttributeValue(AttributesRegistry.POSITIVE_EFFECT_IMMUNITY) == 0) {
            List<MobEffectInstance> positiveEffectsToRemove = new ArrayList<>();
            
            for (MobEffectInstance effect : entity.getActiveEffects()) {
                if (effect.getEffect().value().isBeneficial()) {
                    positiveEffectsToRemove.add(effect);
                }
            }
            for (MobEffectInstance effect : positiveEffectsToRemove) {
                entity.removeEffect(effect.getEffect());
            }
        }

        if (entity.hasEffect(MobEffectsRegistry.PROJECTILE_BOUNCE)) {
            List<Projectile> nearbyProjectiles = entity.level().getEntitiesOfClass(
                Projectile.class,
                entity.getBoundingBox().inflate(Config.PROJECTILE_BOUNCE_RANGE.get()),
                projectile -> projectile.getOwner() != entity
            );

            for (Projectile projectile : nearbyProjectiles) {
                if (projectile.getPersistentData().getBoolean("xinyu_bounced")) {
                    continue;
                }

                Entity owner = projectile.getOwner();
                if (owner != null && owner != entity) {
                    Vec3 direction = new Vec3(owner.getX(), owner.getY(), owner.getZ()).subtract(
                        new Vec3(entity.getX(), entity.getY(), entity.getZ())
                    ).normalize();

                    projectile.setDeltaMovement(direction.scale(projectile.getDeltaMovement().length()));

                    projectile.getPersistentData().putBoolean("xinyu_bounced", true);
                    /*projectile.getPersistentData().putBoolean("xinyu_pass_through_block", true);*/
                    
                    double x = direction.x;
                    double y = direction.y;
                    double z = direction.z;

                    projectile.setYRot((float) (Math.atan2(z, x) * 180 / Math.PI - 90));
                    projectile.setXRot((float) -(Math.atan2(y, Math.sqrt(x * x + z * z)) * 180 / Math.PI));
                    
                    EvasionAnimationRenderer.triggerEvasionAnimation(entity);

                    entity.level().playSound(
                        null, entity.getX(), entity.getY(), entity.getZ(), 
                        SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "evasion")), 
                        SoundSource.PLAYERS, 0.4f, 1
                    );
                }
            }
        }
    }
}