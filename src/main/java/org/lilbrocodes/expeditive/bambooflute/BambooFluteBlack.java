package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.util.DyeColor;

public class BambooFluteBlack extends BambooFluteCommonItem {
    public BambooFluteBlack(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.BLACK;
    }
}
