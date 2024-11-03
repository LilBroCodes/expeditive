package org.lilbrocodes.expeditive.mixin.striderboots;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.lilbrocodes.expeditive.StriderBootsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean expeditive$fluidWalking(boolean original, FluidState state) {
        if (this.getWorld().getBlockState(this.getBlockPos().up()).isOf(Blocks.LAVA)) {
            return false;
        }
        return super.expeditive$fluidWalking(original, state) || (this.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof StriderBootsItem && state.isIn(FluidTags.LAVA));
    }
}
