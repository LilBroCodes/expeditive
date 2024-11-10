package org.lilbrocodes.expeditive.accessor;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.vehicle.BoatEntity;

public interface WolfEntityMethodAccessor {
    void expeditive$setTargetIsBoat(boolean value);
    boolean expeditive$getTargetIsBoat();

    void expeditive$setBoatTarget(BoatEntity value);
    BoatEntity expeditive$getBoatTarget();

    void expeditive$setTargetIsArmorStand(boolean value);
    boolean expeditive$getTargetIsArmorStand();

    void expeditive$setArmorStandTarget(ArmorStandEntity value);
    ArmorStandEntity expeditive$getArmorStandTarget();
}
