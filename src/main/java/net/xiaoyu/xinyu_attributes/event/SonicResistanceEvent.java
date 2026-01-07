package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class SonicResistanceEvent {
    
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().is(TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "is_sonic")))) {
            event.setAmount((float) (event.getAmount() * (1.0 - event.getEntity().getAttributeValue(AttributesRegistry.SONIC_RESISTANCE))));
        }
    }
}