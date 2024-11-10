package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.util.DyeColor;

public class BambooFluteWhite extends BambooFluteCommonItem {
    public BambooFluteWhite(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }
}
