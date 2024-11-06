package org.lilbrocodes.expeditive;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Expeditive implements ModInitializer {
    public static final RegistryKey<ItemGroup> EXPEDITIVE_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier("expeditive", "item_group"));
    public static final ItemGroup EXPEDITIVE_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ExpeditiveItems.STRIDER_BOOTS))
            .displayName(Text.translatable("itemGroup.expeditive"))
            .build();

    @Override
    public void onInitialize() {
        ExpeditiveItems.initialize();
        ExpeditiveCommands.initialize();

        Registry.register(Registries.ITEM_GROUP, EXPEDITIVE_ITEM_GROUP_KEY, EXPEDITIVE_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(EXPEDITIVE_ITEM_GROUP_KEY).register(content -> {
            content.add(ExpeditiveItems.STRIDER_BOOTS);
            content.add(ExpeditiveItems.STRIDERS_FOOT);
            content.add(ExpeditiveItems.BAMBOO_FLUTE);
        });
    }
}
