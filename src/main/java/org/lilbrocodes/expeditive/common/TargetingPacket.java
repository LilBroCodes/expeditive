package org.lilbrocodes.expeditive.common;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import static org.lilbrocodes.expeditive.Expeditive.MOD_ID;

public class TargetingPacket {
    public static final Identifier ID = new Identifier(MOD_ID, "targeting");

    public int targetingEntityId;

    public TargetingPacket(int targetingEntityId) {
        this.targetingEntityId = targetingEntityId;
    }

    public static TargetingPacket read(PacketByteBuf buf) {
        return new TargetingPacket(buf.readInt());
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(targetingEntityId);
    }
}
