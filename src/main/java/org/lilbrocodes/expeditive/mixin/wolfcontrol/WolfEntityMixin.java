package org.lilbrocodes.expeditive.mixin.wolfcontrol;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.lilbrocodes.expeditive.accessor.WolfEntityMethodAccessor;
import org.lilbrocodes.expeditive.common.Misc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public class WolfEntityMixin implements WolfEntityMethodAccessor {
    @Unique
    private boolean targetIsBoat = false;

    @Unique
    private BoatEntity boatTarget = null;

    @Unique
    private boolean targetIsArmorStand = false;

    @Unique
    private ArmorStandEntity armorStandTarget = null;

    @Unique
    public void expeditive$setTargetIsBoat(boolean value) {
        this.targetIsBoat = value;
    }

    @Unique
    public boolean expeditive$getTargetIsBoat() {
        return this.targetIsBoat;
    }

    @Unique
    public void expeditive$setBoatTarget(BoatEntity value) {
        this.boatTarget = value;
    }

    @Unique
    public BoatEntity expeditive$getBoatTarget() {
        return this.boatTarget;
    }

    @Unique
    public void expeditive$setTargetIsArmorStand(boolean value) {
        this.targetIsArmorStand = value;
    }

    @Unique
    public boolean expeditive$getTargetIsArmorStand() {
        return this.targetIsArmorStand;
    }

    @Unique
    public void expeditive$setArmorStandTarget(ArmorStandEntity value) {
        this.armorStandTarget = value;
    }

    @Unique
    public ArmorStandEntity expeditive$getArmorStandTarget() {
        return this.armorStandTarget;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        WolfEntity wolf = (WolfEntity) (Object) this;
        if (wolf.getTarget() != null && this.expeditive$getTargetIsBoat()) {
            BoatEntity target = this.expeditive$getBoatTarget();
            assert target != null;
            if (Misc.distanceTo2D(wolf, target) < 2 && !target.hasPassengers()) {
                wolf.startRiding(target);
                wolf.getNavigation().stop();
                wolf.setTarget(null);
            } else {
                wolf.getNavigation().stop();
                wolf.getNavigation().startMovingTo(wolf.getOwner(), 1.0F);
            }
            this.expeditive$setBoatTarget(null);
            this.expeditive$setTargetIsBoat(false);
        } else if (wolf.getTarget() != null && this.expeditive$getTargetIsArmorStand()) {
            ArmorStandEntity target = this.expeditive$getArmorStandTarget();
            assert target != null;
            if(Misc.distanceTo2D(wolf, target) < 2) {
                wolf.getNavigation().stop();
                wolf.getNavigation().startMovingTo(wolf.getOwner(), 1.0f);
            }
            this.expeditive$setArmorStandTarget(null);
            this.expeditive$setTargetIsArmorStand(false);
        }
    }
}
