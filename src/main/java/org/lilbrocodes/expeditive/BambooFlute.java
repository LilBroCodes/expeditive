package org.lilbrocodes.expeditive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BambooFlute extends Item {
    public BambooFlute(Settings settings) {
        super(settings);
    }

    public static String getColor(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains("BambooFluteColor")) {
            return stack.getNbt().getString("BambooFluteColor");
        }
        return "default";
    }

    public static void setColor(ItemStack stack, String color) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }
        stack.getNbt().putString("BambooFluteColor", color);
    }

    private static int mapAngle(float pitch) {
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

    private static float getPitchFromSeg(int segment) {
        segment = Math.max(1, Math.min(16, segment));
        return 0.5f + (segment - 1) * (1.5f / 15);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        int seg = mapAngle(user.getPitch());
        float pitch = getPitchFromSeg(seg);

        this.playSound(world, user, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value(), pitch);

        return TypedActionResult.consume(itemStack);
    }

//    Commented out because it doesn't work anyway, may re-implement at some point since it has no mechanical purpose anyway.
//    @Override
//    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
//        if (!user.getWorld().isClient) {
//            int usedTicks = this.getMaxUseTime(stack) - remainingUseTicks;
//            if (usedTicks % 5 == 0) {
//                int seg = mapAngle(user.getPitch());
//                float pitch = getPitchFromSeg(seg);
//
//                world.playSound((PlayerEntity) user, user.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value(), SoundCategory.PLAYERS, 10, pitch);
//                world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, user.getPos(), GameEvent.Emitter.of(user));
//            }
//        }
//    }

    private void playSound(World world, Entity entity, SoundEvent soundEvent, float p) {
        world.playSoundFromEntity((PlayerEntity) entity, entity, soundEvent, SoundCategory.PLAYERS, 10, p);
        world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, entity.getPos(), GameEvent.Emitter.of(entity));
    }
}
