package org.lilbrocodes.expeditive.mixin.advancement;

import net.minecraft.server.network.ServerPlayerEntity;
import org.lilbrocodes.expeditive.ExpeditiveAdvancements;
import org.lilbrocodes.expeditive.common.Misc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (Misc.getCompletedBeautifulMelody(player)) Misc.grantAdvancement(player, ExpeditiveAdvancements.BEAUTIFUL_MELODY);
    }
}
