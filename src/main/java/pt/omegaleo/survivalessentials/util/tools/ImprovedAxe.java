package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

import net.minecraft.world.item.Item.Properties;

public class ImprovedAxe extends AxeItem
{
    /*
     * public HammerTool() { super(new
     * Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
     * 
     * }
     */

    public ImprovedAxe(Tier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) 
    {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().tab(SurvivalEssentialsMod.TOOLS_TAB));
        this.damagePerUse = damagePerUse;
    }

    private int damagePerUse = 10;

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return onBlockDestroyed(itemstack, player.level, player.level.getBlockState(pos), pos, player);
    }

    public boolean onBlockDestroyed(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) 
    {
        try
        {
            if(entityLiving instanceof Player)
            {
                Player player = (Player)entityLiving;

                DestroyWoodBlocks(pos, player.getDirection(), worldIn);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }


    public void DestroyWoodBlocks(BlockPos initialBlockPos, Direction facing, Level world)
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


    boolean IsWoodBlockDown(BlockPos currentPos, Direction facing, Level world)
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

    boolean IsWoodBlockUp(BlockPos currentPos, Direction facing, Level world)
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
