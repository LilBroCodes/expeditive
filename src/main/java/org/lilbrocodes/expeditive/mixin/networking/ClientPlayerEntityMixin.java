package org.lilbrocodes.expeditive.mixin.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import org.lilbrocodes.expeditive.common.ClientTargeting;
import org.lilbrocodes.expeditive.common.TargetingPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tickMovement(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            Entity targetedEntity = ClientTargeting.getCurrentlyTargeting(client.player, true, true);
            int targetEntityId = targetedEntity != null ? targetedEntity.getId() : -1;

            TargetingPacket packet = new TargetingPacket(targetEntityId);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.write(buf);

            ClientPlayNetworking.send(TargetingPacket.ID, buf);
        }
    }
}
