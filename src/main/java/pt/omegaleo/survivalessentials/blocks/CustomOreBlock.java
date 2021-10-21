package pt.omegaleo.survivalessentials.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public class CustomOreBlock extends OreBlock 
{
    public CustomOreBlock() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(5.0f)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(3));
    }
}
