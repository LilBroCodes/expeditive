package org.lilbrocodes.expeditive;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ExpeditiveItems {
    private ExpeditiveItems() {}

    private static final ArmorMaterial STRIDER_BOOTS_M = new StriderBoots();
    public static final Item STRIDER_BOOTS =
            register("strider_boots", new StriderBootsItem(STRIDER_BOOTS_M, new FabricItemSettings()));
    public static final Item STRIDERS_FOOT =
            register("striders_foot", new StridersFoot(new FabricItemSettings()));

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, new Identifier("expeditive", path), item);
    }

    public static void initialize() {

    }
}
