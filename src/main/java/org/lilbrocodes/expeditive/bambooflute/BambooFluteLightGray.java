package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.util.DyeColor;

public class BambooFluteLightGray extends BambooFluteCommonItem {
    public BambooFluteLightGray(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.LIGHT_GRAY;
    }
}
