package org.lilbrocodes.expeditive.mixin.striderboots;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.lilbrocodes.expeditive.StriderBootsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract Random getRandom();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "canWalkOnFluid", at = @At("RETURN"))
    protected boolean expeditive$fluidWalking(boolean original, FluidState state) {
        return original;
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void preventLavaDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            if (this.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof StriderBootsItem && !this.getWorld().getBlockState(this.getBlockPos().up()).isOf(Blocks.LAVA)) {
                if (source.isOf(DamageTypes.LAVA) || source.isOf(DamageTypes.HOT_FLOOR)) {
                    if (this.age % 100 == 0) {
                        this.getEquippedStack(EquipmentSlot.FEET).damage(1, this.getRandom(), null);
                    }
                    
                    if (source.isOf(DamageTypes.LAVA)) {
                        this.extinguish();
                        this.setFireTicks(0);
                    }

                    cir.setReturnValue(false);
                }
            }
        }
    }
}