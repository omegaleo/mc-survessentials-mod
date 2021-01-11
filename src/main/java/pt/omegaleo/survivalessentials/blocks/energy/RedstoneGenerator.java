package pt.omegaleo.survivalessentials.blocks.energy;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.ContainerRedstoneGenerator;
import pt.omegaleo.survivalessentials.tileentity.TileEntityRedstoneGenerator;

public class RedstoneGenerator extends Block
{
    TileEntityRedstoneGenerator tileEntity;
    public RedstoneGenerator() 
    {
        super(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(15f, 30f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.METAL));
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
            Hand handIn, BlockRayTraceResult hit) 
    {
        if(!worldIn.isRemote)
        {
            
            player.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, p) -> 
                    {
                        ContainerRedstoneGenerator container = new ContainerRedstoneGenerator(id, playerInventory);
                        container.setTileentity(tileEntity);
                        return container;
                    },
                    new TranslationTextComponent("container.survivalessentials.redstonegenerator")
            ));
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) 
    {
        tileEntity = new TileEntityRedstoneGenerator();
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean hasTileEntity(BlockState state) 
    {
        return super.hasTileEntity(state);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) 
    {
        return new TileEntityRedstoneGenerator();
    }

    /*@Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) 
    {
        TileEntityRedstoneGenerator tileEntity = (TileEntityRedstoneGenerator) worldIn.getTileEntity(pos);
        player.addItemStackToInventory(tileEntity.handler.getStackInSlot(0));
        super.onBlockHarvested(worldIn, pos, state, player);
    }*/
}
