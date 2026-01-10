package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.*;
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
                if (!livingEntity.getAttribute(AttributesRegistry.PHASING).getModifiers().isEmpty() && 
                    livingEntity.getAttributeValue(AttributesRegistry.PHASING) == 0) {
                    cir.setReturnValue(Shapes.empty());
                }
            }
        }
    }
}