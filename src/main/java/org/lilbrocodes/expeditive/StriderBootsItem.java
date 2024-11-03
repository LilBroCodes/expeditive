package org.lilbrocodes.expeditive;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StriderBootsItem extends ArmorItem {
    public StriderBootsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.BOOTS, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("itemTooltip.expeditive.strider_boots_l0").formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("itemTooltip.expeditive.strider_boots_l1").formatted(Formatting.GOLD));
    }
}
