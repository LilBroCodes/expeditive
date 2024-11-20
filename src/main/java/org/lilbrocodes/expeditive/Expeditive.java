package org.lilbrocodes.expeditive;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lilbrocodes.expeditive.common.Misc;
import org.lilbrocodes.expeditive.common.TargetingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Expeditive implements ModInitializer {
    public static final RegistryKey<ItemGroup> EXPEDITIVE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier("expeditive", "item_group"));
    public static final ItemGroup EXPEDITIVE_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ExpeditiveItems.STRIDER_BOOTS))
            .displayName(Text.translatable("itemGroup.expeditive"))
            .build();
    public static final Logger logger = LoggerFactory.getLogger("Expeditive");
    public static final String MOD_ID = "expeditive";
    public static HashMap<PlayerEntity, Entity> TARGETS = new HashMap<>();

    @Override
    public void onInitialize() {
        ExpeditiveItems.initialize();

        Registry.register(Registries.ITEM_GROUP, EXPEDITIVE_ITEM_GROUP_KEY, EXPEDITIVE_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(EXPEDITIVE_ITEM_GROUP_KEY).register(content -> {
            content.add(ExpeditiveItems.STRIDER_BOOTS);
            content.add(ExpeditiveItems.STRIDERS_FOOT);
            content.add(ExpeditiveItems.BAMBOO_FLUTE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_BLACK);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_BLUE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_BROWN);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_CYAN);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_GRAY);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_GREEN);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_LIGHT_BLUE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_LIGHT_GRAY);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_LIME);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_MAGENTA);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_ORANGE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_PINK);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_PURPLE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_RED);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_WHITE);
            content.add(ExpeditiveItems.BAMBOO_FLUTE_YELLOW);
        });

        if (Misc.isServer()) {
            ServerPlayNetworking.registerGlobalReceiver(TargetingPacket.ID, (server, player, handler, buf, responseSender) -> {
                TargetingPacket packet = TargetingPacket.read(buf);

                Entity entity = player.getWorld().getEntityById(packet.targetingEntityId);
                TARGETS.put(player, entity);
            });
        }
    }
}
