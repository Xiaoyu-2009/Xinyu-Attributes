package net.xiaoyu.xinyu_attributes.event;

import net.xiaoyu.xinyu_attributes.registry.AttributesRegistry;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.*;

@EventBusSubscriber
public class MiningDropMultiplierEvent {

    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        if (event.getBreaker() instanceof LivingEntity livingBreaker) {
            double miningDropMultiplier = livingBreaker.getAttributeValue(AttributesRegistry.MINING_DROP_MULTIPLIER);
            
            if (miningDropMultiplier > 0) {
                List<ItemEntity> extraDrops = new ArrayList<>();
                
                for (ItemEntity originalDrop : event.getDrops()) {
                    ItemStack originalItem = originalDrop.getItem();
                    int extraCount = (int) (originalItem.getCount() * miningDropMultiplier);
                    
                    if (extraCount > 0) {
                        ItemStack extraItem = originalItem.copy();
                        ItemEntity extraDrop = new ItemEntity(
                            originalDrop.level(),
                            originalDrop.getX(),
                            originalDrop.getY(),
                            originalDrop.getZ(),
                            extraItem
                        );
                        
                        extraItem.setCount(extraCount);
                        extraDrops.add(extraDrop);
                        extraDrop.setPickUpDelay(10);
                    }
                }
                
                event.getDrops().addAll(extraDrops);
            }
        }
    }
}