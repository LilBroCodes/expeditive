package org.lilbrocodes.expeditive.common;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.lilbrocodes.expeditive.ExpeditiveItems;

import java.util.Objects;

public class Misc {
    public static double distanceTo3D(Entity from, Entity to) {
        Vec3d pos1 = from.getPos();
        Vec3d pos2 = to.getPos();
        double a = (pos1.x - pos2.x) * (pos1.x - pos2.x);
        double b = (pos1.y - pos2.y) * (pos1.y - pos2.y);
        double c = (pos1.z - pos2.z) * (pos1.z - pos2.z);
        return Math.sqrt(a + b + c);
    }

    public static double distanceTo2D(Entity from, Entity to) {
        Vec3d pos1 = from.getPos();
        Vec3d pos2 = to.getPos();
        double a = (pos1.x - pos2.x) * (pos1.x - pos2.x);
        double b = (pos1.z - pos2.z) * (pos1.z - pos2.z);
        return Math.sqrt(a + b);
    }

    public static void grantAdvancement(ServerPlayerEntity player, Identifier identifier) {
        Advancement advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(identifier);
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                progress.getUnobtainedCriteria().forEach(criterion ->
                        player.getAdvancementTracker().grantCriterion(advancement, criterion));
            }
        }
    }

    public static boolean getCompletedBeautifulMelody(ServerPlayerEntity player) {
        for (Item fluteType : ExpeditiveItems.BAMBOO_FLUTES) {
            Stat<Item> stat = Stats.USED.getOrCreateStat(fluteType);
            int timesUsed = player.getStatHandler().getStat(stat);

            if (timesUsed < 1) return false;
        }
        return true;
    }
}
