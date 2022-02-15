package pt.omegaleo.survivalessentials.blocks;

import java.util.List;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class CustomOreBlock extends OreBlock 
{
    public CustomOreBlock() {
        super(Block.Properties.of(Material.STONE).sound(SoundType.STONE));
    }
}
