package pt.omegaleo.survivalessentials.util.enums;

import net.minecraft.item.Item;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class DrillUpgrade extends Item
{

    public DrillUpgrade() 
    {
        super(new Properties().group(SurvivalEssentialsMod.UPGRADES_TAB).maxStackSize(1));
    }
    
}
