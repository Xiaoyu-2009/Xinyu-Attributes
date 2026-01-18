package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.*;

@EventBusSubscriber
public class KillDropMultiplierEvent {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity killer) {
            double killDropMultiplier = killer.getAttributeValue(AttributesRegistry.KILL_DROP_MULTIPLIER);

            if (killDropMultiplier > 0) {
                List<ItemEntity> extraDrops = new ArrayList<>();
                
                for (ItemEntity originalDrop : event.getDrops()) {
                    for (int i = 0; i < (int) killDropMultiplier; i++) {
                        extraDrops.add(new ItemEntity(
                            originalDrop.level(),
                            originalDrop.getX(),
                            originalDrop.getY(),
                            originalDrop.getZ(),
                            originalDrop.getItem().copy()
                        ));
                    }
                }

                event.getDrops().addAll(extraDrops);
            }
        }
    }
}