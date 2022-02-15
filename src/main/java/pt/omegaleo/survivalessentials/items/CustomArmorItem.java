package pt.omegaleo.survivalessentials.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class CustomArmorItem extends ArmorItem
{
    boolean isEnchanted = false;

    public CustomArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn, boolean isEnchanted) 
    {
        super(materialIn, slot, builderIn);
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted;
    }
    
}
