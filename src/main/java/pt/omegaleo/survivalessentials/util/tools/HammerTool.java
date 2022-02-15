package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import javax.lang.model.util.ElementScanner6;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.minecraft.world.item.PickaxeItem;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;


public class HammerTool extends PickaxeItem
{
    public HammerTool(Tier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) 
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

                BlockPos[] blocksToDestroy = getAOEPositions(pos, player.getDirection());
                for(int i = 0; i < blocksToDestroy.length; i++)
                {
                    if(!worldIn.isEmptyBlock(blocksToDestroy[i]) && getBlock(blocksToDestroy[i], worldIn) != Blocks.BEDROCK)
                    {
                        worldIn.destroyBlock(blocksToDestroy[i], true);
                    }

                    stack.setDamageValue(stack.getDamageValue() + 1);
                    if(!worldIn.isEmptyBlock(blocksToDestroy[i]))
                    {
                        worldIn.destroyBlock(blocksToDestroy[i], true);
                    }
                }

                if (!player.isCreative())
                {
                    player.getMainHandItem().setDamageValue(player.getMainHandItem().getDamageValue() - damagePerUse);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    private Block getBlock(BlockPos pos, Level world) {
        BlockState ibs = world.getBlockState(pos);
        Block block = ibs.getBlock();
        return block;
    }

    public BlockPos[] getAOEPositions(BlockPos initialBlockPos, Direction facing)
    {
        //Return a 3x3 area for now
        BlockPos[] result = new BlockPos[9];
        if(facing == Direction.DOWN || facing == Direction.UP)
        {
            result[0] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY(),initialBlockPos.getZ() + 1);
            result[1] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY(),initialBlockPos.getZ() + 1);
            result[2] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY(),initialBlockPos.getZ() + 1);
    
            result[3] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY(),initialBlockPos.getZ());
            result[4] = initialBlockPos;
            result[5] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY(),initialBlockPos.getZ());
    
            result[6] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY(),initialBlockPos.getZ() - 1);
            result[7] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY(),initialBlockPos.getZ() - 1);
            result[8] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY(),initialBlockPos.getZ() - 1);
        }
        else if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            result[0] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY()+1,initialBlockPos.getZ()); //Top Left
            result[1] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY()+1,initialBlockPos.getZ()); //Top Center
            result[2] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY()+1,initialBlockPos.getZ()); //Top Right
    
            result[3] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY(),initialBlockPos.getZ()); //Middle Left
            result[4] = initialBlockPos; // Middle Center
            result[5] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY(),initialBlockPos.getZ()); //Middle Right
    
            result[6] = new BlockPos(initialBlockPos.getX() - 1,initialBlockPos.getY()-1,initialBlockPos.getZ()); //Top Left
            result[7] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY()-1,initialBlockPos.getZ()); //Top Center
            result[8] = new BlockPos(initialBlockPos.getX() + 1,initialBlockPos.getY()-1,initialBlockPos.getZ()); //Top Right
        }
        else
        {
            result[0] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() - 1,initialBlockPos.getZ() + 1);
            result[1] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY(),initialBlockPos.getZ() + 1);
            result[2] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() + 1,initialBlockPos.getZ() + 1);
    
            result[3] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() - 1,initialBlockPos.getZ());
            result[4] = initialBlockPos;
            result[5] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() + 1,initialBlockPos.getZ());
    
            result[6] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() - 1,initialBlockPos.getZ() - 1);
            result[7] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY(),initialBlockPos.getZ() - 1);
            result[8] = new BlockPos(initialBlockPos.getX(),initialBlockPos.getY() + 1,initialBlockPos.getZ() - 1);
        }
        
        return result;
    }
}
