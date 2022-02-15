package pt.omegaleo.survivalessentials.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class CustomItem extends Item
{
    boolean isEnchanted = false;
    public CustomItem(Properties properties, boolean isEnchanted) {
        super(properties);
        //TODO Auto-generated constructor stub

        this.isEnchanted = isEnchanted;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnchanted;
    }
    
}
