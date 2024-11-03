package org.lilbrocodes.expeditive.mixin.striderboots.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import org.lilbrocodes.expeditive.StriderBootsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow public abstract boolean isSneaking();

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z", ordinal = 0))
    private boolean expeditive$fluidWalking0(boolean original) {
        if (this.getWorld().getBlockState(this.getBlockPos().up()).isOf(Blocks.LAVA)) {
            return false;
        }
        return original && !(this.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof StriderBootsItem);
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z", ordinal = 1))
    private boolean expeditive$fluidWalking1(boolean original) {
        if (this.getWorld().getBlockState(this.getBlockPos().up()).isOf(Blocks.LAVA)) {
            return false;
        }
        return original && !(this.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof StriderBootsItem);
    }
}
