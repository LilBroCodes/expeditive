package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.util.DyeColor;

public class BambooFluteRed extends BambooFluteCommonItem {
    public BambooFluteRed(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.RED;
    }
}
