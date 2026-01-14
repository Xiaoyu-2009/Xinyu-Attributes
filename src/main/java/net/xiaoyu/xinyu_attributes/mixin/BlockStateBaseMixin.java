package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.registry.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.*;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Inject(
        method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("HEAD"), 
        cancellable = true
    )
    private void onGetCollisionShape(BlockGetter blockGetter, BlockPos blockPos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityCollisionContext entityContext) {
            if (entityContext.getEntity() instanceof LivingEntity livingEntity) {
                if (livingEntity.hasEffect(MobEffectRegistry.PHASING)) {
                    cir.setReturnValue(Shapes.empty());
                }
                if (livingEntity.hasEffect(MobEffectRegistry.WATER_WALKING) &&
                    !blockGetter.getFluidState(blockPos).isEmpty() &&
                    !livingEntity.isCrouching() &&
                    !(livingEntity.isEyeInFluidType(blockGetter.getFluidState(blockPos).getFluidType()) || 
                    livingEntity.isInFluidType(blockGetter.getFluidState(blockPos).getFluidType()))) {
                    var fluidState = blockGetter.getFluidState(blockPos);
                    float fluidHeight = fluidState.getType().getHeight(fluidState, blockGetter, blockPos);
                    cir.setReturnValue(Shapes.box(0, 0, 0, 1, Math.min(fluidHeight, 0.9F), 1));
                }
            }
        }
    }
}