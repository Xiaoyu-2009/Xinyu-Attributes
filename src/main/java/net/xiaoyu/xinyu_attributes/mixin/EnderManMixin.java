package net.xiaoyu.xinyu_attributes.mixin;

import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    private void IsLookingAtMe(Player player, CallbackInfoReturnable<Boolean> cir) {

        if (!player.getAttribute(AttributesRegistry.STARE_IMMUNITY).getModifiers().isEmpty() && 
            player.getAttributeValue(AttributesRegistry.STARE_IMMUNITY) >= 0) {
            cir.setReturnValue(false);
        }
    }
}