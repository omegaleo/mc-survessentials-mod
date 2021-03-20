package pt.omegaleo.survivalessentials.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import pt.omegaleo.survivalessentials.ModItems;

public class CorundumOre extends OreBlock 
{

    List<ItemStack> drops;

    public CorundumOre() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(5.0f)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(3));
    }

    
}
