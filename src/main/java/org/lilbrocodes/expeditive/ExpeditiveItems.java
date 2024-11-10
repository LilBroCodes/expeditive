package org.lilbrocodes.expeditive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.lilbrocodes.expeditive.bambooflute.*;

public class ExpeditiveItems {
    private static final ArmorMaterial STRIDER_BOOTS_M = new StriderBoots();
    public static final Item STRIDER_BOOTS =
            register("strider_boots", new StriderBootsItem(STRIDER_BOOTS_M, new FabricItemSettings()));
    public static final Item STRIDERS_FOOT =
            register("striders_foot", new StridersFoot(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE =
            register("bamboo_flute", new BambooFlute(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_BLACK =
            register("bamboo_flute_black", new BambooFluteBlack(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_BLUE =
            register("bamboo_flute_blue", new BambooFluteBlue(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_BROWN =
            register("bamboo_flute_brown", new BambooFluteBrown(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_CYAN =
            register("bamboo_flute_cyan", new BambooFluteCyan(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_GRAY =
            register("bamboo_flute_gray", new BambooFluteGray(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_GREEN =
            register("bamboo_flute_green", new BambooFluteGreen(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_LIGHT_BLUE =
            register("bamboo_flute_light_blue", new BambooFluteLightBlue(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_LIGHT_GRAY =
            register("bamboo_flute_light_gray", new BambooFluteLightGray(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_LIME =
            register("bamboo_flute_lime", new BambooFluteLime(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_MAGENTA =
            register("bamboo_flute_magenta", new BambooFluteMagenta(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_ORANGE =
            register("bamboo_flute_orange", new BambooFluteOrange(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_PINK =
            register("bamboo_flute_pink", new BambooFlutePink(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_PURPLE =
            register("bamboo_flute_purple", new BambooFlutePurple(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_RED =
            register("bamboo_flute_red", new BambooFluteRed(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_WHITE =
            register("bamboo_flute_white", new BambooFluteWhite(new FabricItemSettings().maxCount(1)));
    public static final Item BAMBOO_FLUTE_YELLOW =
            register("bamboo_flute_yellow", new BambooFluteYellow(new FabricItemSettings().maxCount(1)));

    public static final Item[] BAMBOO_FLUTES = new Item[] {
            BAMBOO_FLUTE,
            BAMBOO_FLUTE_BLACK,
            BAMBOO_FLUTE_BLUE,
            BAMBOO_FLUTE_BROWN,
            BAMBOO_FLUTE_CYAN,
            BAMBOO_FLUTE_GRAY,
            BAMBOO_FLUTE_GREEN,
            BAMBOO_FLUTE_LIGHT_BLUE,
            BAMBOO_FLUTE_LIGHT_GRAY,
            BAMBOO_FLUTE_LIME,
            BAMBOO_FLUTE_MAGENTA,
            BAMBOO_FLUTE_ORANGE,
            BAMBOO_FLUTE_PINK,
            BAMBOO_FLUTE_PURPLE,
            BAMBOO_FLUTE_RED,
            BAMBOO_FLUTE_WHITE,
            BAMBOO_FLUTE_YELLOW
    };

    public static <T extends Item> T register(String path, T item) {
        return Registry.register(Registries.ITEM, new Identifier("expeditive", path), item);
    }

    private static void registerBambooFlutePredicate(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("playing"),
                ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() ? 1.0F : 0.0F));
    }

    public static void initialize() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
            for (Item item : BAMBOO_FLUTES) {
                registerBambooFlutePredicate(item);
            }
        }
    }
}
