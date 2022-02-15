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

import java.util.ArrayList;

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

                if (IsWoodBlock(state.getBlock()))
                {
                    DestroyWoodBlocks(worldIn,pos, state.getBlock(), new ArrayList<>(),player);
                }
                else
                {
                    DestroyBlock(worldIn,pos,player);
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
    private void DestroyWoodBlocks(Level world, BlockPos pos, Block block, ArrayList<BlockPos> list, Player player) {
        if (world.getBlockState(pos.north()).getBlock() == block) {
            list.add(pos.north());
        }
        if (world.getBlockState(pos.north().east()).getBlock() == block) {
            list.add(pos.north().east());
        }
        if (world.getBlockState(pos.north().west()).getBlock() == block) {
            list.add(pos.north().west());
        }
        if (world.getBlockState(pos.north().east().below()).getBlock() == block) {
            list.add(pos.north().east().below());
        }
        if (world.getBlockState(pos.north().west().below()).getBlock() == block) {
            list.add(pos.north().west().below());
        }
        if (world.getBlockState(pos.south()).getBlock() == block) {
            list.add(pos.south());
        }
        if (world.getBlockState(pos.south().east()).getBlock() == block) {
            list.add(pos.south().east());
        }
        if (world.getBlockState(pos.south().west()).getBlock() == block) {
            list.add(pos.south().west());
        }
        if (world.getBlockState(pos.south().east().below()).getBlock() == block) {
            list.add(pos.south().east().below());
        }
        if (world.getBlockState(pos.south().west().below()).getBlock() == block) {
            list.add(pos.south().west().below());
        }
        if (world.getBlockState(pos.east()).getBlock() == block) {
            list.add(pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block) {
            list.add(pos.west());
        }
        if (world.getBlockState(pos.below()).getBlock() == block) {
            list.add(pos.below());
        }
        if (world.getBlockState(pos.below().north()).getBlock() == block) {
            list.add(pos.below().north());
        }
        if (world.getBlockState(pos.below().south()).getBlock() == block) {
            list.add(pos.below().south());
        }
        if (world.getBlockState(pos.below().east()).getBlock() == block) {
            list.add(pos.below().east());
        }
        if (world.getBlockState(pos.below().west()).getBlock() == block) {
            list.add(pos.below().west());
        }
        if (world.getBlockState(pos.north().east().above()).getBlock() == block) {
            list.add(pos.north().east().above());
        }
        if (world.getBlockState(pos.north().west().above()).getBlock() == block) {
            list.add(pos.north().west().above());
        }
        if (world.getBlockState(pos.south().east().above()).getBlock() == block) {
            list.add(pos.south().east().above());
        }
        if (world.getBlockState(pos.south().west().above()).getBlock() == block) {
            list.add(pos.south().west().above());
        }
        if (world.getBlockState(pos.above()).getBlock() == block) {
            list.add(pos.above());
        }
        if (world.getBlockState(pos.above().north()).getBlock() == block) {
            list.add(pos.above().north());
        }
        if (world.getBlockState(pos.above().south()).getBlock() == block) {
            list.add(pos.above().south());
        }
        if (world.getBlockState(pos.above().east()).getBlock() == block) {
            list.add(pos.above().east());
        }
        if (world.getBlockState(pos.above().west()).getBlock() == block) {
            list.add(pos.above().west());
        }
        if (list.size() <= 0 || list == null) {
            list = null;
            DestroyBlock(world,pos,player);
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            BlockPos pos1 = list.get(i);
            if (IsWoodBlock(world.getBlockState(pos1).getBlock()))
            {
                DestroyBlock(world,pos1,player);
                DestroyWoodBlocks(world, list.get(i), block, new ArrayList<BlockPos>(), player);
            }
        }
    }

    void DestroyBlock(Level world, BlockPos pos, Player player)
    {
        if (player.getMainHandItem().getDamageValue() < damagePerUse) return;

        world.destroyBlock(pos, true);
        if (!player.isCreative())
        {
            player.getMainHandItem().setDamageValue(player.getMainHandItem().getDamageValue() + damagePerUse);
        }
    }

    public boolean IsWoodBlock(Block block)
    {
        return block == Blocks.ACACIA_LOG || block == Blocks.BIRCH_LOG || block == Blocks.DARK_OAK_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG;
    }
}
