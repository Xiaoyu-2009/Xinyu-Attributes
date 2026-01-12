package net.xiaoyu.xinyu_attributes.data;

import com.google.gson.*;
import net.xiaoyu.xinyu_attributes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class OreVisionColorData {
    private static final Map<ResourceLocation, float[]> COLOR_CACHE = new ConcurrentHashMap<>();
    private static boolean loaded = false;
    
    public static void loadConfig() {
        Minecraft mc = Minecraft.getInstance();
        ResourceManager resourceManager = mc.getResourceManager();

        Optional<Resource> optionalResource = resourceManager.getResource(
            ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "config/ore_vision_color.json")
        );
        
        COLOR_CACHE.clear();
        
        try (Reader reader = new InputStreamReader(optionalResource.get().open())) {
            JsonObject valuesObject = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonObject("values");
            
            for (String blockId : valuesObject.keySet()) {
                float[] color = new float[3];
                for (int i = 0; i < 3; i++) {
                    color[i] = valuesObject.getAsJsonArray(blockId).get(i).getAsFloat();
                }
                
                if (blockId.startsWith("#")) {
                    ResourceLocation tagLocation = ResourceLocation.tryParse(blockId.substring(1));
                    if (tagLocation != null) {
                        HolderSet.Named<Block> tag = mc.level.registryAccess().registryOrThrow(Registries.BLOCK).getTag(TagKey.create(Registries.BLOCK, tagLocation)).orElse(null);
                        if (tag != null) {
                            for (Holder<Block> blockHolder : tag) {
                                ResourceLocation blockLoc = BuiltInRegistries.BLOCK.getKey(blockHolder.value());
                                COLOR_CACHE.put(blockLoc, color);
                            }
                        }
                    }
                }/* else if (blockId.startsWith("@")) {
                    BuiltInRegistries.BLOCK.entrySet()
                        .stream()
                        .filter(
                            entry -> entry.getKey().location().getNamespace().equals(blockId.substring(1))
                        )
                        .forEach(entry -> {
                            COLOR_CACHE.put(entry.getKey().location(), color);
                        });
                }*/ else {
                    COLOR_CACHE.put(ResourceLocation.parse(blockId), color);
                }
            }
        } catch (Exception e) {
            XinYuAttributes.LOGGER.error("Error loading ore vision color config: {}", e.getMessage());
        }
    }

    public static float[] getColorForBlock(ResourceLocation blockId) {
        if (!loaded) {
            loadConfig();
            loaded = true;
        }
        return COLOR_CACHE.get(blockId);
    }
}