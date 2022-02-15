package pt.omegaleo.survivalessentials.util.enums;

import net.minecraft.world.item.Item;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

import net.minecraft.world.item.Item.Properties;

public class DrillUpgrade extends Item
{

    public DrillUpgrade() 
    {
        super(new Properties().tab(SurvivalEssentialsMod.UPGRADES_TAB).stacksTo(1));
    }
    
}
