package pt.omegaleo.survivalessentials.util.enums;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.template.RuleTest;

public class OreGenBlock 
{
    public RuleTest fillerType;
    public BlockState state;
    public int veinSize;
    public int minHeight;
    public int maxHeight;
    public int amount;

    public OreGenBlock(RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount)
    {
        this.fillerType = fillerType;
        this.state = state;
        this.veinSize =  veinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.amount = amount;
    }
}
