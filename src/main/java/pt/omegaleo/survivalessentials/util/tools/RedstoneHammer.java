package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class RedstoneHammer extends HammerTool 
{
    public RedstoneHammer(IItemTier tier, int attackDamageIn, float attackSpeedIn) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);
    }
}
