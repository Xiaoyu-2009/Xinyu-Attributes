package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.AttributesRegistry;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class FireResistanceEvent {

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setAmount((float) (event.getAmount() * (1.0 - event.getEntity().getAttributeValue(AttributesRegistry.FIRE_RESISTANCE))));
        }
    }
}