package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

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
}