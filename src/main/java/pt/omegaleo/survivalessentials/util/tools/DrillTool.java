package pt.omegaleo.survivalessentials.util.tools;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.DrillContainer;
import pt.omegaleo.survivalessentials.items.ItemFilterUpgrade;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;

import net.minecraft.world.item.Item.Properties;

public class DrillTool extends PickaxeItem{
    // Can install upgrades
    // Has different mining modes, 1x1, 3x3, 5x5, 7x7
    // Will have a void filter

    private int[] miningRadius = new int[] { 1, 3, 5, 7 };

    private int currentSelectedRadius = 0;

    private int damagePerUse = 10;

    public DrillTool(Tier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().tab(SurvivalEssentialsMod.TOOLS_TAB));
        this.damagePerUse = damagePerUse;
    }

    public int getInventorySize(ItemStack stack) {
        return 6;
    }

    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }

    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if (itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }

    @Override
    public Component getDescription() {
        return new TextComponent(
                "Current radius: " + miningRadius[currentSelectedRadius] + "x" + miningRadius[currentSelectedRadius] +"\r\n" +"Hold " + "\u00A76" + "Sneak" + "\u00A76" + " while right-clicking to open drill menu");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if(hasUpgrade((DrillUpgrade)ModItems.REDSTONE_TIER.get(), stack))
        {
            return 800;
        }
        else if(hasUpgrade((DrillUpgrade)ModItems.DIAMOND_TIER.get(), stack))
        {
            return 1561;
        }
        else if(hasUpgrade((DrillUpgrade)ModItems.NETHERITE_TIER.get(), stack))
        {
            return 2031;
        }
        else if(hasUpgrade((DrillUpgrade)ModItems.MYTHRIL_TIER.get(), stack))
        {
            return 3000;
        }

        return super.getMaxDamage(stack);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        var worldIn = player.level;
        if ((stack.getMaxDamage() - stack.getDamageValue()) > 1) {
            if (miningRadius[currentSelectedRadius] > 1) {

                BlockPos[] blocksToDestroy = getAOEPositions(pos, player.getDirection(), miningRadius[currentSelectedRadius]);
                System.out.print(blocksToDestroy);
                for (int i = 0; i < blocksToDestroy.length; i++) {
                    if (!worldIn.isEmptyBlock(blocksToDestroy[i]))
                    {
                        if(hasUpgrade((DrillUpgrade)ModItems.ITEM_FILTER.get(), stack))
                        {
                            Block block = getBlock(blocksToDestroy[i], worldIn);
                            System.out.println(block);
                            if(block != null)
                            {
                                if(blockInFilter(block, stack))
                                {
                                    DestroyBlock(stack, blocksToDestroy[i], false, worldIn, player);
                                }
                                else
                                {
                                    DestroyBlock(stack, blocksToDestroy[i], true, worldIn, player);
                                }
                            }
                        }
                        else
                        {
                            DestroyBlock(stack, blocksToDestroy[i], true, worldIn, player);
                        }
                    }
                }
            }
            else
            {
                if(hasUpgrade((DrillUpgrade)ModItems.ITEM_FILTER.get(), stack))
                {
                    Block block = getBlock(pos, worldIn);
                    if(block != null)
                    {
                        if(blockInFilter(block, stack))
                        {
                            DestroyBlock(stack, pos, false, worldIn, player);
                        }
                        else
                        {
                            DestroyBlock(stack, pos, true, worldIn, player);
                        }
                    }
                }
                else
                {
                    DestroyBlock(stack, pos, true, worldIn, player);
                }
            }
        }
        return super.onBlockStartBreak(stack, pos, player);
    }

    @Override
    public void onDestroyed(ItemEntity p_150887_) {
        //Always prevent destruction
        return;
    }

    void DestroyBlock(ItemStack stack, BlockPos pos, boolean keepDrop, Level world, Player player)
    {
        Inventory inv = player.getInventory();
        Block block = getBlock(pos, world);
        if(block == Blocks.BEDROCK)
        {
            return; //We can't destroy Bedrock
        }

        if(keepDrop)
        {
            if(hasUpgrade((DrillUpgrade)ModItems.MAGNET.get(),stack))
            {
                if(block != null)
                {
                    ItemStack drop = getDrop(block,stack);
                    if(drop != null)
                    {
                        inv.add(drop);
                        world.destroyBlock(pos, false);
                    }
                    else
                    {
                        world.destroyBlock(pos, keepDrop);
                    }
                }
                else
                {
                    world.destroyBlock(pos, keepDrop);
                }
            }
            else
            {
                ItemStack stackToSpawn = getDrop(block,stack);
                world.destroyBlock(pos, false);
                world.addFreshEntity(new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),stackToSpawn));
            }
        }
        else
        {
            world.destroyBlock(pos, keepDrop);
        }

        stack.setDamageValue(stack.getDamageValue() + 1);

        int expAmount = getExpAmount(block);
        if(stack.getDamageValue()>0 && hasUpgrade((DrillUpgrade)ModItems.REPAIR.get(), stack))
        {
            int amountToRepair = (int) ((damagePerUse * expAmount) * 0.75);
            if(stack.getDamageValue() - amountToRepair < 0)
            {
                amountToRepair -= stack.getDamageValue();
                stack.setDamageValue(0);
            }
            else
            {
                stack.setDamageValue(stack.getDamageValue() - amountToRepair);
            }
        }
        else
        {
            player.experienceLevel += expAmount;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        if (playerIn.isCrouching()) {
            playerIn.openMenu(new SimpleMenuProvider(
                    (id, playerInventory, player) -> new DrillContainer(id, playerInventory),
                    new TranslatableComponent("container.survivalessentials.drill")));
        }
        return super.use(worldIn, playerIn, handIn);
    }

    public int GetCurrentRadius(ItemStack stack) {
        // Change Radius
        int radius = stack.getOrCreateTag().getInt("radius");
        for (int i = 0; i < miningRadius.length; i++) {
            if (miningRadius[i] == radius) {
                return i;
            }
        }
        return 0;
    }

    public void SetCurrentRadius(int radius, ItemStack stack) {
        // Change Radius

        for (int i = 0; i < miningRadius.length; i++) {
            if (miningRadius[i] == radius) {
                currentSelectedRadius = i;
                break;
            }
        }

        stack.getOrCreateTag().putInt("radius", currentSelectedRadius);
    }

    BlockPos[] getAOEPositions(BlockPos initialPosition, Direction facing, int radius) {
        BlockPos[] positions = new BlockPos[radius * radius]; // Example: 3x3 = 9, 5x5 = 25
        int currentPositionIndex = 0;

        int offSet = radius / 2;

        if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            for (int y = -radius / 2; y < radius / 2 + 1; y++) {
                for (int x = -radius / 2; x < radius / 2 + 1; x++) {
                    int blockX = initialPosition.getX() + x;
                    int blockY = initialPosition.getY() + y;
                    positions[currentPositionIndex] = new BlockPos(blockX,
                    blockY, initialPosition.getZ());
                    currentPositionIndex++;
                }
            }
        }
        else if(facing == Direction.UP || facing == Direction.DOWN)
        {
            for (int x = -radius / 2; x < radius / 2 + 1; x++) {
                for (int z = -radius / 2; z < radius / 2 + 1; z++) {
                    int blockX = initialPosition.getX() + x;
                    int blockZ = initialPosition.getZ() + z;
                    positions[currentPositionIndex] = new BlockPos(blockX,
                            initialPosition.getY(), blockZ);
                    System.out.println(positions[currentPositionIndex]);
                    currentPositionIndex++;
                }
            }
        }
        else if(facing == Direction.WEST || facing == Direction.EAST)
        {
            for (int y = -radius / 2; y < radius / 2 + 1; y++) {
                for (int z = -radius / 2; z < radius / 2 + 1; z++) {
                    int blockY = initialPosition.getY() + y;
                    int blockZ = initialPosition.getZ() + z;
                    positions[currentPositionIndex] = new BlockPos(initialPosition.getX(),
                    blockY, blockZ);
                    System.out.println(positions[currentPositionIndex]);
                    currentPositionIndex++;
                }
            }
        }

        return positions;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        if(hasUpgrade((DrillUpgrade)ModItems.REPAIR.get(), stack))
        {
            if(stack.getDamageValue() > 0)
            {
                stack.setDamageValue(stack.getDamageValue() - damagePerUse);
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    private boolean hasUpgrade(DrillUpgrade upgrade, ItemStack stack) {
        IItemHandler itemHandler = getInventory(stack);
        if (itemHandler != null) {
            for (int i = 0; i < 6; i++) {
                if (itemHandler.getStackInSlot(i).getItem() instanceof DrillUpgrade) {
                    DrillUpgrade upgradeInSlot = (DrillUpgrade) itemHandler.getStackInSlot(i).getItem();
                    if (upgradeInSlot == upgrade) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private ItemStack getDrop(Block block, ItemStack stack)
    {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

        int BLOCK_FORTUNE = 0;
        if(enchantments.containsKey(Enchantments.BLOCK_FORTUNE))
        {
            BLOCK_FORTUNE = enchantments.get(Enchantments.BLOCK_FORTUNE).intValue();
        }

        if(block == Blocks.DIAMOND_ORE)
        {
            return new ItemStack(Items.DIAMOND, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.COAL_ORE)
        {
            return new ItemStack(Items.COAL, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.REDSTONE_ORE)
        {
            return new ItemStack(Items.REDSTONE, getDropAmount(4, 5, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.LAPIS_ORE)
        {
            return new ItemStack(Items.LAPIS_LAZULI, getDropAmount(4, 8, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.NETHER_QUARTZ_ORE)
        {
            return new ItemStack(Items.QUARTZ, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.NETHER_GOLD_ORE)
        {
            return new ItemStack(Items.GOLD_NUGGET, getDropAmount(2, 6, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.SEA_LANTERN)
        {
            return new ItemStack(Items.PRISMARINE_CRYSTALS, getDropAmount(2, 3, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.STONE)
        {
            if(hasUpgrade((DrillUpgrade)ModItems.SMELT_UPGRADE.get(), stack))
            {
                return getSmeltResult(block,stack);
            }
            else
            {
                return new ItemStack(Items.COBBLESTONE,1);
            }
        }
        else if(block == Blocks.GLOWSTONE)
        {
            return new ItemStack(Items.GLOWSTONE, getDropAmount(2, 4, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.COBWEB)
        {
            return new ItemStack(Items.STRING, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.BOOKSHELF)
        {
            return new ItemStack(Items.BOOK, getDropAmount(3, 3, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.ANCIENT_DEBRIS)
        {
            return new ItemStack(Items.ANCIENT_DEBRIS, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.EMERALD_ORE)
        {
            return new ItemStack(Items.EMERALD, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.IRON_ORE)
        {
            if(hasUpgrade((DrillUpgrade)ModItems.SMELT_UPGRADE.get(), stack))
            {
                return getSmeltResult(block,stack);
            }
            else
            {
                return new ItemStack(block.asItem(),1);
            }
        }
        else if(block == Blocks.GOLD_ORE)
        {
            if(hasUpgrade((DrillUpgrade)ModItems.SMELT_UPGRADE.get(), stack))
            {
                return getSmeltResult(block,stack);
            }
            else
            {
                return new ItemStack(block.asItem(),1);
            }
        }
        else
        {
            return new ItemStack(block.asItem(),1);
        }
    }

    private ItemStack getSmeltResult(Block block, ItemStack stack)
    {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

        int BLOCK_FORTUNE = 0;
        if(enchantments.containsKey(Enchantments.BLOCK_FORTUNE))
        {
            BLOCK_FORTUNE = enchantments.get(Enchantments.BLOCK_FORTUNE).intValue();
        }

        if(block == Blocks.IRON_ORE)
        {
            return new ItemStack(Items.IRON_INGOT, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else if(block == Blocks.GOLD_ORE)
        {
            return new ItemStack(Items.GOLD_INGOT, getDropAmount(1, 1, enchantments.containsKey(Enchantments.BLOCK_FORTUNE),BLOCK_FORTUNE));
        }
        else
        {
            return new ItemStack(block.asItem(),1);
        }
    }

    private int getExpAmount(Block block)
    {
        if(block == Blocks.COAL_ORE)
        {
            return 2;
        }
        else if(block == Blocks.DIAMOND_ORE)
        {
            return 7;
        }
        else if(block == Blocks.EMERALD_ORE)
        {
            return 7;
        }
        else if(block == Blocks.LAPIS_ORE)
        {
            return 5;
        }
        else if(block == Blocks.NETHER_QUARTZ_ORE)
        {
            return 5;
        }
        else if(block == Blocks.NETHER_GOLD_ORE)
        {
            return 2;
        }
        else if(block == Blocks.REDSTONE_ORE)
        {
            return 5;
        }
        else
        {
            return 0;
        }
    }

    private int getDropAmount(int min, int max, boolean hasBLOCK_FORTUNE, int BLOCK_FORTUNE)
    {
        Random rand = new Random();
        if(hasBLOCK_FORTUNE)
        {
            if((max + 3)-(min + 1) > 0)
            {
                return rand.nextInt((max + 3)-(min + 1)) + (min + 1) + (BLOCK_FORTUNE * 2);
            }
            else
            {
                return (min + 1) + (BLOCK_FORTUNE * 2);
            }
        }
        else
        {
            if(max - min > 0)
            {
                return rand.nextInt(max-min) + min;
            }
            else
            {
                return min;
            }
        }
    }

    private Block getBlock(BlockPos pos, Level world) {
        BlockState ibs = world.getBlockState(pos);
        Block block = ibs.getBlock();
        return block;
    }

    private boolean blockInFilter(Block block, ItemStack stack)
    {
        IItemHandler itemHandler = getInventory(stack);
        if (itemHandler != null) {
            for (int i = 0; i < 6; i++) {
                if (itemHandler.getStackInSlot(i).getItem() instanceof ItemFilterUpgrade) {
                    ItemFilterUpgrade upgradeInSlot = (ItemFilterUpgrade) itemHandler.getStackInSlot(i).getItem();
                    
                    List<ItemStack> itemsInUpgrade = upgradeInSlot.getItems(itemHandler.getStackInSlot(i));
                    for(int j = 0; j < itemsInUpgrade.size(); j++)
                    {
                        System.out.println(block.asItem() + " == " + itemsInUpgrade.get(j).getItem());
                        if(block.asItem() == itemsInUpgrade.get(j).getItem())
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        //Prevent drill from being destroyed
        if(stack.getMaxDamage() - damage > 1)
        {
            super.setDamage(stack, damage);
        }
    }
}
