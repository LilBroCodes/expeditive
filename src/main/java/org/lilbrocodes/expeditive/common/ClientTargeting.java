package org.lilbrocodes.expeditive.common;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ClientTargeting extends Targeting {
    @Nullable
    public static Entity getCurrentlyTargeting(PlayerEntity player, boolean ignoreTamed, boolean ignoreNonLiving) {
        double distanceCap = 128f * 128f;
        assert MinecraftClient.getInstance().cameraEntity != null;
        Vec3d cameraPos = MinecraftClient.getInstance().cameraEntity.getCameraPosVec(1.0f);
        Vec3d cameraRot = MinecraftClient.getInstance().cameraEntity.getRotationVec(1.0f);
        Vec3d cameraTarget = cameraPos.add(cameraRot.multiply(distanceCap));

        Box box = player.getBoundingBox().stretch(cameraTarget).expand(1.0, 1.0, 1.0);
        Predicate<Entity> predicate = entity -> isValidTarget(entity, player, ignoreTamed, ignoreNonLiving);

        EntityHitResult entityHitResult = raycast(player, box, predicate);
        if (entityHitResult != null) {
            Entity target = entityHitResult.getEntity();
            if (target instanceof LivingEntity || ignoreNonLiving) {
                return target;
            }
        }
        return null;
    }
}
