package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class Hammer extends HammerTool 
{
    public Hammer(IItemTier tier, int attackDamageIn, float attackSpeedIn) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);
    }
}
