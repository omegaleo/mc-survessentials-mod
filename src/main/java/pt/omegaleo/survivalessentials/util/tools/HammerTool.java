package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import javax.lang.model.util.ElementScanner6;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class HammerTool extends PickaxeItem
{
    /*
     * public HammerTool() { super(new
     * Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
     * 
     * }
     */

    public HammerTool(IItemTier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) 
    {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().group(SurvivalEssentialsMod.ITEMS_TAB));
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

                BlockPos[] blocksToDestroy = getAOEPositions(pos, mop.getFace());
                for(int i = 0; i < blocksToDestroy.length; i++)
                {
                    if(worldIn.isBlockPresent(blocksToDestroy[i]))
                    {
                        worldIn.destroyBlock(blocksToDestroy[i], true);
                    }
                }
                    
                int damage = damagePerUse;
    
                if((stack.getMaxDamage() - stack.getDamage()) - damagePerUse <= 0)
                {
                    damage = stack.getMaxDamage() - stack.getDamage() - 1;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
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
