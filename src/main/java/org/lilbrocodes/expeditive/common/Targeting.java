package org.lilbrocodes.expeditive.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lilbrocodes.expeditive.Expeditive;

import java.util.function.Predicate;

public class Targeting {
    public static @Nullable Entity getCurrentlyTargeting(PlayerEntity player) {
        return Expeditive.TARGETS.get(player);
    }

    static boolean isValidTarget(@Nullable Entity target, PlayerEntity player, boolean ignoreTamed, boolean ignoreNonLiving) {
        if (!(target instanceof LivingEntity) && !ignoreNonLiving) return false;
        if (player == null) return false;
        if (target == player) return false;
        if (!player.canSee(target)) return false;
        if (target instanceof LivingEntity living) {
            if (living.isDead()) return false;
        }
        assert target != null;
        if (target.isRemoved()) return false;
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) return false;
        if (target instanceof TameableEntity tamed && tamed.isOwner(player) && !ignoreTamed) return false;
        return target.canHit();
    }

    static EntityHitResult raycast(Entity player, Box box, Predicate<Entity> predicate) {
        Entity target = null;
        double closestDistance = Double.MAX_VALUE;
        Vec3d playerDirection = player.getRotationVector();

        for (Entity possibleTarget : player.getWorld().getEntitiesByClass(Entity.class, box, predicate)) {
            if (possibleTarget.getRootVehicle() == player.getRootVehicle()) {
                continue;
            }
            Vec3d toTarget = possibleTarget.getPos().add(0, possibleTarget.getHeight() / 2, 0).subtract(player.getEyePos());
            double dotProduct = toTarget.normalize().dotProduct(playerDirection);
            double distance = 1 - dotProduct;

            if (distance < closestDistance) {
                closestDistance = distance;
                target = possibleTarget;
            }
        }
        return target == null ? null : new EntityHitResult(target, target.getPos());
    }
}
