package pt.omegaleo.survivalessentials.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomArmorItem extends ArmorItem
{
    boolean isEnchanted = false;

    public CustomArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, boolean isEnchanted) 
    {
        super(materialIn, slot, builderIn);
        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnchanted;
    }
    
}
