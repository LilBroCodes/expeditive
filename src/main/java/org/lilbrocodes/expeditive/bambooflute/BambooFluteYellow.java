package org.lilbrocodes.expeditive.bambooflute;

import net.minecraft.util.DyeColor;

public class BambooFluteYellow extends BambooFluteCommonItem {
    public BambooFluteYellow(Settings settings) {
        super(settings);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.YELLOW;
    }
}
