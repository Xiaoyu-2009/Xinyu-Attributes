package net.xiaoyu.xinyu_attributes.network;

import net.xiaoyu.xinyu_attributes.XinYuAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.*;
import java.util.concurrent.*;

public record OreVisionDataPacket(List<BlockPos> orePositions) implements CustomPacketPayload {
    private static final List<BlockPos> clientOrePositions = new CopyOnWriteArrayList<>();
    public static final Type<OreVisionDataPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(XinYuAttributes.MOD_ID, "ore_vision_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OreVisionDataPacket> STREAM_CODEC = CustomPacketPayload.codec(OreVisionDataPacket::write, OreVisionDataPacket::new);

    public OreVisionDataPacket(List<BlockPos> orePositions) {
        this.orePositions = orePositions != null ? orePositions : new ArrayList<>();
    }

    public OreVisionDataPacket(FriendlyByteBuf buf) {
        this(buf.readList(b -> new BlockPos(b.readInt(), b.readInt(), b.readInt())));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(this.orePositions, (b, pos) -> {
            b.writeInt(pos.getX());
            b.writeInt(pos.getY());
            b.writeInt(pos.getZ());
        });
    }

    public static void handle(OreVisionDataPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            clientOrePositions.clear();
            clientOrePositions.addAll(packet.orePositions());
        });
    }
    
    public static List<BlockPos> getClientOrePositions() {
        return clientOrePositions;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}