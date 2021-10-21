package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import javax.lang.model.util.ElementScanner6;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.util.enums.IExtendedReach;

public class ImprovedAxe extends AxeItem
{
    /*
     * public HammerTool() { super(new
     * Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
     * 
     * }
     */

    public ImprovedAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) 
    {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().group(SurvivalEssentialsMod.TOOLS_TAB));
        this.damagePerUse = damagePerUse;
    }

    private int damagePerUse = 10;


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) 
    {
        try
        {
            if(entityLiving instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity)entityLiving;

                BlockRayTraceResult mop = Item.rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);

                DestroyWoodBlocks(pos, mop.getFace(), worldIn);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }


    public void DestroyWoodBlocks(BlockPos initialBlockPos, Direction facing, World world)
    {
        //Return a 3x3 area for now

        boolean woodBlockDown = false;
        boolean woodBlockUp = IsWoodBlockUp(initialBlockPos, facing, world);

        world.destroyBlock(initialBlockPos, true); //Destroy the first block

        BlockPos currentPos = new BlockPos(initialBlockPos.getX(), initialBlockPos.getY(), initialBlockPos.getZ());


        while(woodBlockUp)
        {
            if(facing == Direction.DOWN || facing == Direction.UP)
            {

                BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY(),currentPos.getZ() + 1);

                BlockState ibsUp = world.getBlockState(posUp);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockUp = IsWoodBlock(blockUp);

                if(woodBlockUp)
                {
                    world.destroyBlock(posUp, true);
                    currentPos = posUp;
                }
            }
            else if(facing == Direction.NORTH || facing == Direction.SOUTH)
            {
                BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY() + 1,currentPos.getZ());

                BlockState ibsUp = world.getBlockState(posUp);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockUp = IsWoodBlock(blockUp);


                if(woodBlockUp)
                {
                    world.destroyBlock(posUp, true);
                    currentPos = posUp;
                }
            }
            else
            {
                // Z == Y
                BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY() + 1,currentPos.getZ());

                BlockState ibsUp = world.getBlockState(posUp);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockUp = IsWoodBlock(blockUp);

                if(woodBlockUp)
                {
                    world.destroyBlock(posUp, true);
                    currentPos = posUp;
                }
            }
        }

        woodBlockUp = false;
        woodBlockDown = IsWoodBlockDown(initialBlockPos, facing, world);

        while(woodBlockDown)
        {
            if(facing == Direction.DOWN || facing == Direction.UP)
            {
                BlockPos posDown = new BlockPos(currentPos.getX(),currentPos.getY(),currentPos.getZ() - 1);

                BlockState ibsUp = world.getBlockState(posDown);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockDown = IsWoodBlock(blockUp);

                if(woodBlockDown)
                {
                    world.destroyBlock(posDown, true);
                    currentPos = posDown;
                }
            }
            else if(facing == Direction.NORTH || facing == Direction.SOUTH)
            {
                BlockPos posDown = new BlockPos(currentPos.getX(),currentPos.getY() - 1,currentPos.getZ());

                BlockState ibsUp = world.getBlockState(posDown);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockDown = IsWoodBlock(blockUp);

                if(woodBlockDown)
                {
                    world.destroyBlock(posDown, true);
                    currentPos = posDown;
                }
            }
            else
            {
                BlockPos posDown = new BlockPos(currentPos.getX(),currentPos.getY() - 1,currentPos.getZ());

                BlockState ibsUp = world.getBlockState(posDown);
                Block blockUp = ibsUp.getBlock();       
                
                woodBlockDown = IsWoodBlock(blockUp);

                if(woodBlockDown)
                {
                    world.destroyBlock(posDown, true);
                    currentPos = posDown;
                }
            }
        }
    }


    boolean IsWoodBlockDown(BlockPos currentPos, Direction facing, World world)
    {
        if(facing == Direction.DOWN || facing == Direction.UP)
        {
            BlockPos posDown =  new BlockPos(currentPos.getX(),currentPos.getY(),currentPos.getZ() - 1);   
            
            BlockState ibsDown = world.getBlockState(posDown);
            Block blockDown = ibsDown.getBlock();    

            return IsWoodBlock(blockDown);
        }
        else if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            BlockPos posDown =  new BlockPos(currentPos.getX(),currentPos.getY() - 1,currentPos.getZ());     
            
            BlockState ibsDown = world.getBlockState(posDown);
            Block blockDown = ibsDown.getBlock();    

            return IsWoodBlock(blockDown);
        }
        else
        {
            BlockPos posDown =  new BlockPos(currentPos.getX(),currentPos.getY() - 1,currentPos.getZ());      
            
            BlockState ibsDown = world.getBlockState(posDown);
            Block blockDown = ibsDown.getBlock();    

            return IsWoodBlock(blockDown);
        }

    }

    boolean IsWoodBlockUp(BlockPos currentPos, Direction facing, World world)
    {
        if(facing == Direction.DOWN || facing == Direction.UP)
        {
            // Z == Y
            BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY(),currentPos.getZ() + 1);

            BlockState ibsUp = world.getBlockState(posUp);
            Block blockUp = ibsUp.getBlock();        

            return IsWoodBlock(blockUp);
        }
        else if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY() + 1,currentPos.getZ());

            BlockState ibsUp = world.getBlockState(posUp);
            Block blockUp = ibsUp.getBlock();       
            
            return IsWoodBlock(blockUp);
        }
        else
        {
            // Z == Y
            BlockPos posUp = new BlockPos(currentPos.getX(),currentPos.getY() + 1,currentPos.getZ());

            BlockState ibsUp = world.getBlockState(posUp);
            Block blockUp = ibsUp.getBlock();       
            
            return IsWoodBlock(blockUp);
        }

    }


    public boolean IsWoodBlock(Block block)
    {
        return block == Blocks.ACACIA_LOG || block == Blocks.BIRCH_LOG || block == Blocks.DARK_OAK_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG;
    }
}
