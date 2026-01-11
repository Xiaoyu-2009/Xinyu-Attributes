package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    
    @Shadow 
    public abstract Entity getOwner();

    @ModifyVariable(method = "shoot(DDDFF)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyVelocity(float originalVelocity) {
        Entity owner = this.getOwner();

        if (owner instanceof LivingEntity livingEntity) {
            double projectileSpeedBonus = livingEntity.getAttributeValue(AttributesRegistry.PROJECTILE_SPEED);

            if (!livingEntity.getAttribute(AttributesRegistry.PROJECTILE_SPEED).getModifiers().isEmpty() && projectileSpeedBonus != 0) {
                return (float) (/*originalVelocity + */projectileSpeedBonus);
            }
        }

        return originalVelocity;
    }

    /*@Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void skipBlockCollisionIfTagged(HitResult hitResult, CallbackInfo ci) {
        Projectile projectile = (Projectile) (Object) this;
        
        if (hitResult.getType() == HitResult.Type.BLOCK && projectile.getPersistentData().getBoolean("xinyu_pass_through_block")) {
            ci.cancel();
        }
    }*/
}