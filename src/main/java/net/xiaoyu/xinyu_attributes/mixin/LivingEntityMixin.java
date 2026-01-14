package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.*;
import net.xiaoyu.xinyu_attributes.registry.*;
import net.xiaoyu.xinyu_attributes.util.ResistanceUtil;
import net.xiaoyu.xinyu_attributes.client.renderer.*;
import net.xiaoyu.xinyu_attributes.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import org.spongepowered.asm.mixin.*;
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

        if (entity.hasEffect(MobEffectRegistry.CLIMBING_SPEED)) {
            return Config.CLIMBING_UP_SPEED_VALUE.get();
        }

        return vanillaClimbSpeed;
    }
    
    @ModifyArg(
        method = "handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "INVOKE", 
            target = "Ljava/lang/Math;max(DD)D", 
            ordinal = 0
        ),
        index = 1
    )
    private double modifyClimbingDownSpeed(double vanillaDownSpeed) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(MobEffectRegistry.CLIMBING_SPEED)) {
            return -Config.CLIMBING_DOWN_SPEED_VALUE.get();
        }

        return vanillaDownSpeed;
    }
    
    @SuppressWarnings("deprecation")
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickCheckProjectileBounce(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.getAttribute(AttributesRegistry.NEGATIVE_EFFECT_IMMUNITY).getModifiers().isEmpty()) {
            List<MobEffectInstance> negativeEffectsToRemove = new ArrayList<>();
            List<? extends String> whitelist = Config.NEGATIVE_EFFECT_IMMUNITY_WHITELIST.get();
            List<? extends String> blacklist = Config.NEGATIVE_EFFECT_IMMUNITY_BLACKLIST.get();

            for (MobEffectInstance effect : entity.getActiveEffects()) {
                if (!effect.getEffect().value().isBeneficial()) {
                    String effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value()).toString();
                    boolean shouldRemove = whitelist.isEmpty();

                    if (!whitelist.isEmpty()) {
                        shouldRemove = whitelist.contains(effectId);
                    }

                    if (blacklist.contains(effectId)) {
                        shouldRemove = false;
                    }
                    
                    if (shouldRemove) {
                        negativeEffectsToRemove.add(effect);
                    }
                }
            }
            for (MobEffectInstance effect : negativeEffectsToRemove) {
                entity.removeEffect(effect.getEffect());
            }
        }

        if (!entity.getAttribute(AttributesRegistry.POSITIVE_EFFECT_IMMUNITY).getModifiers().isEmpty()) {
            List<MobEffectInstance> positiveEffectsToRemove = new ArrayList<>();
            List<? extends String> whitelist = Config.POSITIVE_EFFECT_IMMUNITY_WHITELIST.get();
            List<? extends String> blacklist = Config.POSITIVE_EFFECT_IMMUNITY_BLACKLIST.get();

            for (MobEffectInstance effect : entity.getActiveEffects()) {
                if (effect.getEffect().value().isBeneficial()) {
                    String effectId = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value()).toString();
                    boolean shouldRemove = whitelist.isEmpty();

                    if (!whitelist.isEmpty()) {
                        shouldRemove = whitelist.contains(effectId);
                    }

                    if (blacklist.contains(effectId)) {
                        shouldRemove = false;
                    }
                    
                    if (shouldRemove) {
                        positiveEffectsToRemove.add(effect);
                    }
                }
            }
            for (MobEffectInstance effect : positiveEffectsToRemove) {
                entity.removeEffect(effect.getEffect());
            }
        }
        
        if (entity.hasEffect(MobEffectRegistry.CROP_GROWTH)) {
            if (Config.CROP_GROWTH_TICK_DELAY.get() == 0 || entity.level().getGameTime() % Config.CROP_GROWTH_TICK_DELAY.get() == 0) {
                Level level = entity.level();

                if (level instanceof ServerLevel serverLevel) {
                    int range = entity.getEffect(MobEffectRegistry.CROP_GROWTH).getAmplifier() + 1;
 
                    for (int x = -range; x <= range; x++) {
                        for (int y = -range; y <= range; y++) {
                            for (int z = -range; z <= range; z++) {
                                BlockPos pos =  entity.blockPosition().offset(x, y, z);

                                if ( x * x + y * y + z * z <= range * range) {
                                    BlockState blockState = level.getBlockState(pos);

                                    if (blockState.is(BlockTags.CROPS) || blockState.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "crops")))) {
                                        blockState.randomTick(serverLevel, pos, serverLevel.getRandom());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (entity.hasEffect(MobEffectRegistry.AUTO_HARVEST)) {
            Level level = entity.level();

            if (level instanceof ServerLevel serverLevel) {
                int range = entity.getEffect(MobEffectRegistry.AUTO_HARVEST).getAmplifier() + 1;

                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        for (int z = -range; z <= range; z++) {
                            BlockPos pos = entity.blockPosition().offset(x, y, z);

                            if (x * x + y * y + z * z <= range * range) {
                                BlockState blockState = level.getBlockState(pos);

                                if (blockState.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(blockState)) {
                                    serverLevel.destroyBlock(pos, true, entity);
                                    for (ItemStack drop : Block.getDrops(blockState, (ServerLevel) level, pos, null)) {
                                        if (drop.getItem() instanceof BlockItem blockItem) {
                                            Block dropBlock = blockItem.getBlock();

                                            if (dropBlock instanceof CropBlock) {
                                                if (serverLevel.getBlockState(pos.below()).getBlock() instanceof FarmBlock) {
                                                    serverLevel.setBlock(pos, dropBlock.defaultBlockState(), 3);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (entity.hasEffect(MobEffectRegistry.PROJECTILE_BOUNCE)) {
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
        
        if (!entity.getAttribute(AttributesRegistry.AUTO_DESTROY).getModifiers().isEmpty()) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                int range = (int) entity.getAttributeValue(AttributesRegistry.AUTO_DESTROY);
                
                for (int x = -range/2; x < -range/2 + range; x++) {
                    for (int y = 0; y < range; y++) {
                        for (int z = -range/2; z < -range/2 + range; z++) {
                            serverLevel.destroyBlock(entity.blockPosition().offset(x, y, z), true, entity);
                        }
                    }
                }
            }
        }

        if (entity.hasEffect(MobEffectRegistry.BLACK_HOLE_ABSORPTION)) {
            Level level = entity.level();

            if (level instanceof ServerLevel serverLevel) {
                int range = entity.getEffect(MobEffectRegistry.BLACK_HOLE_ABSORPTION).getAmplifier() + 1;

                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        for (int z = -range; z <= range; z++) {
                            BlockPos blockPos = entity.blockPosition().offset(x, y, z);

                            if (entity.position().distanceToSqr(Vec3.atCenterOf(blockPos)) <= range * range) {
                                BlockState blockState = level.getBlockState(blockPos);

                                if (blockState.isAir() || blockState.liquid() || blockState.hasBlockEntity() || 
                                    blockState.is(Blocks.END_PORTAL_FRAME)
                                ) continue;
                                level.removeBlock(blockPos, false);
                                
                                AbsorbedBlockEntity absorbedBlockEntity = new AbsorbedBlockEntity(
                                    EntityRegistry.ABSORBED_BLOCK_ENTITY.get(),
                                    level,
                                    blockPos,
                                    blockState,
                                    entity.position()
                                );
                                
                                absorbedBlockEntity.setAbsorptionTime(Config.BLACK_HOLE_ABSORPTION_TIME.get());
                                serverLevel.addFreshEntity(absorbedBlockEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "actuallyHurt", at = @At("TAIL"))
    private void onActuallyHurt(DamageSource source, float amount, CallbackInfo ci) {
        if (source.getEntity() instanceof LivingEntity attacker && source.getEntity() != (Object) this &&
            !attacker.getAttribute(AttributesRegistry.LIFE_ABSORPTION).getModifiers().isEmpty()) {
            attacker.heal(amount * (float) attacker.getAttributeValue(AttributesRegistry.LIFE_ABSORPTION));
        }
    }

    @Inject(method = "canDisableShield", at = @At("HEAD"), cancellable = true)
    private void canDisableShield(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.getAttribute(AttributesRegistry.SHIELD_BREAK).getModifiers().isEmpty()) {
            cir.setReturnValue(true);
        }
    }
}