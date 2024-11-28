package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.lilbrocodes.composer.api.targeting.entity.IEntityTargetingEntity;
import org.lilbrocodes.expeditive.common.Misc;
import org.lilbrocodes.expeditive.accessor.WolfEntityMethodAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.lilbrocodes.expeditive.common.Misc.distanceTo2D;

public class BambooFluteCommonItem extends Item {
    private DyeColor color;
    private int fluteLastPlayed = 0;

    public BambooFluteCommonItem(Settings settings) {
        super(settings);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static int mapAngle(float pitch) {
        pitch = Math.max(-90, Math.min(90, pitch)) * -1;

        int actionIndex;
        if (pitch >= 40) {
            actionIndex = 16;
        } else if (pitch <= -40) {
            actionIndex = 1;
        } else {
            float normalizedPitch = (pitch + 40) / 80.0f;
            actionIndex = 2 + Math.round(normalizedPitch * 14);
        }

        return actionIndex;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    public static float getPitchFromSeg(int segment) {
        segment = Math.max(1, Math.min(16, segment));
        return 0.5f + (segment - 1) * (1.5f / 15);
    }

    public void playSound(World world, Entity entity, SoundEvent soundEvent, float p) {
        world.playSoundFromEntity((PlayerEntity) entity, entity, soundEvent, SoundCategory.PLAYERS, 10, p);
        world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, entity.getPos(), GameEvent.Emitter.of(entity));
    }

    public static List<WolfEntity> getPlayerOwnedWolves(World world, PlayerEntity player) {
        List<WolfEntity> ownedWolves = new ArrayList<>();

        Box worldBox = new Box(player.getBlockPos()).expand(1024);

        for (WolfEntity wolf : world.getEntitiesByClass(WolfEntity.class, worldBox, LivingEntity::isAlive)) {
            if (wolf.isTamed()) {
                UUID wolfOwnerUuid = wolf.getOwnerUuid();
                if (wolfOwnerUuid != null && wolfOwnerUuid.equals(player.getUuid())) {
                    ownedWolves.add(wolf);
                }
            }
        }

        return ownedWolves;
    }

    public static List<WolfEntity> getPlayerOwnedWolves(World world, PlayerEntity player, DyeColor color) {
        List<WolfEntity> ownedWolves = getPlayerOwnedWolves(world, player);
        ownedWolves.removeIf(wolf -> wolf.getCollarColor() != color);
        return ownedWolves;
    }


    public void executePlayedAction(PlayerEntity player, int segment, BambooFluteCommonItem stack) {
        if (player.age == fluteLastPlayed) return;
        this.fluteLastPlayed = player.age;
        List<WolfEntity> wolvesToCommand;
        if (stack.getColor() != null) {
            wolvesToCommand = getPlayerOwnedWolves(player.getWorld(), player, stack.getColor());
        } else {
            wolvesToCommand = getPlayerOwnedWolves(player.getWorld(), player);
        }
        if (segment == 1) {
            for (WolfEntity wolf : wolvesToCommand) {
                if (!wolf.isLeashed()) sendWolfTo(wolf, player, 1.0F, false);
            }
        } else {
            Entity target = null;
            if (player instanceof IEntityTargetingEntity targeting) {
                target = targeting.composer$getLastEntityTarget();
            }
            if (target == null) return;
            double distance = distanceTo2D(player, target);
            if (!(target instanceof LivingEntity)) {
                if (target instanceof BoatEntity) {
                    for (WolfEntity wolf : wolvesToCommand) {
                        if (wolf instanceof WolfEntityMethodAccessor accessor) {
                            accessor.expeditive$setTargetIsBoat(true);
                            accessor.expeditive$setBoatTarget((BoatEntity) target);
                            sendWolfTo(wolf, target, 1.0F, false);
                        }
                    }
                } else if (target instanceof ArmorStandEntity) {
                    for (WolfEntity wolf : wolvesToCommand) {
                        if (wolf instanceof WolfEntityMethodAccessor accessor) {
                            accessor.expeditive$setTargetIsArmorStand(true);
                            accessor.expeditive$setArmorStandTarget((ArmorStandEntity) target);
                            sendWolfTo(wolf, target, 1.0F, false);
                        }
                    }
                }
            } else if (target instanceof WolfEntity wolf && wolvesToCommand.contains(wolf) && distance <= 64) {
                wolf.setSitting(!wolf.isSitting());
                wolf.setInSittingPose(wolf.isSitting());
            } else if (distance <= 64) {
                if (target instanceof WolfEntity && ((WolfEntity) target).getOwnerUuid() == player.getUuid()) return;
                for (WolfEntity wolf : wolvesToCommand) {
                    if (!wolf.isSitting() && !wolf.isLeashed()) {
                        sendWolfTo(wolf, target, 1.0F, true);
                    }
                }
            }
        }
    }

    // Send to entity
    public void sendWolfTo(WolfEntity wolf, Entity target, float speed, boolean attack) {
        wolf.getNavigation().stop();
        wolf.setTarget(null);
        wolf.setAngerTime(0);

        if (wolf.isSitting()) {
            wolf.setSitting(false);
            wolf.setInSittingPose(false);
        }

        if (!attack) {
            wolf.getNavigation().startMovingTo(target, speed);
        } else {
            wolf.setTarget((LivingEntity) target);
            wolf.setAngryAt(target.getUuid());
            wolf.setAngerTime(-1);
            wolf.setAttacking(true);
        }
    }

    // Send to block
    public void sendWolfTo(WolfEntity wolf, BlockPos target, float speed) {
        wolf.getNavigation().stop();
        wolf.setTarget(null);
        wolf.setAngerTime(0);

        if (wolf.isSitting()) {
            wolf.setSitting(false);
            wolf.setInSittingPose(false);
        }

        wolf.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), speed);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        user.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));

        int seg = mapAngle(user.getPitch());
        float pitch = getPitchFromSeg(seg);

        playSound(world, user, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value(), pitch);

        executePlayedAction(user, seg, this);

        return TypedActionResult.consume(itemStack);
    }
}
