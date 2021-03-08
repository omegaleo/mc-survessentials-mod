package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class ImprovedAxeItem extends ImprovedAxe 
{
    public ImprovedAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);
    }
}
