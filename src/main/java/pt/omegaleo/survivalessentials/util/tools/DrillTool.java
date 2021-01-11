package pt.omegaleo.survivalessentials.util.tools;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.DrillContainer;
import pt.omegaleo.survivalessentials.items.ItemFilterUpgrade;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;

public class DrillTool extends PickaxeItem{
    // Can install upgrades
    // Has different mining modes, 1x1, 3x3, 5x5, 7x7
    // Will have a void filter

    private int[] miningRadius = new int[] { 1, 3, 5, 7 };

    private int currentSelectedRadius = 0;

    private int damagePerUse = 10;

    public DrillTool(IItemTier tier, int attackDamageIn, float attackSpeedIn, int damagePerUse) {
        super(tier, attackDamageIn, attackSpeedIn, new Properties().group(SurvivalEssentialsMod.TOOLS_TAB));
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
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent(
                "Current radius: " + miningRadius[currentSelectedRadius] + "x" + miningRadius[currentSelectedRadius]));
        tooltip.add(new TranslationTextComponent(
                "Hold " + "\u00A76" + "Sneak" + "\u00A76" + " while right-clicking to open drill menu"));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
            LivingEntity entityLiving) {
        if ((stack.getMaxDamage() - stack.getDamage()) > 1) {
            if (miningRadius[currentSelectedRadius] > 1) {
                if (entityLiving instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entityLiving;

                    BlockRayTraceResult mop = Item.rayTrace(worldIn, player, RayTraceContext.FluidMode.ANY);

                    BlockPos[] blocksToDestroy = getAOEPositions(pos, mop.getFace(), miningRadius[currentSelectedRadius]);
                    System.out.print(blocksToDestroy);
                    for (int i = 0; i < blocksToDestroy.length; i++) {
                        if (worldIn.isBlockPresent(blocksToDestroy[i])) 
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
            }
            else
            {
                PlayerEntity player = (PlayerEntity) entityLiving;
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
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    void DestroyBlock(ItemStack stack, BlockPos pos, boolean keepDrop, World world, PlayerEntity player)
    {
        PlayerInventory inv = player.inventory;
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
                        inv.addItemStackToInventory(drop);
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
                world.addEntity(new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),stackToSpawn));
            }
        }
        else
        {
            world.destroyBlock(pos, keepDrop);
        }

        int expAmount = getExpAmount(block);
        if(stack.getDamage()>0)
        {
            int amountToRepair = (int) ((damagePerUse * expAmount) * 0.75);
            if(stack.getDamage() - amountToRepair < 0)
            {
                amountToRepair -= stack.getDamage();
                stack.setDamage(0);
            }
            else
            {
                stack.setDamage(stack.getDamage() - amountToRepair);
            }
        }
        else
        {
            player.experienceTotal += expAmount;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isSneaking()) {
            if (!worldIn.isRemote) {
                playerIn.openContainer(new SimpleNamedContainerProvider(
                        (id, playerInventory, player) -> new DrillContainer(id, playerInventory),
                        new TranslationTextComponent("container.survivalessentials.drill")));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
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
            if(stack.getDamage() > 0)
            {
                stack.setDamage(stack.getDamage() - damagePerUse);
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

        int fortune = 0;
        if(enchantments.containsKey(Enchantments.FORTUNE))
        {
            fortune = enchantments.get(Enchantments.FORTUNE).intValue();
        }

        if(block == Blocks.DIAMOND_ORE)
        {
            return new ItemStack(Items.DIAMOND, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.COAL_ORE)
        {
            return new ItemStack(Items.COAL, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.REDSTONE_ORE)
        {
            return new ItemStack(Items.REDSTONE, getDropAmount(4, 5, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.LAPIS_ORE)
        {
            return new ItemStack(Items.LAPIS_LAZULI, getDropAmount(4, 8, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.NETHER_QUARTZ_ORE)
        {
            return new ItemStack(Items.QUARTZ, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.NETHER_GOLD_ORE)
        {
            return new ItemStack(Items.GOLD_NUGGET, getDropAmount(2, 6, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.SEA_LANTERN)
        {
            return new ItemStack(Items.PRISMARINE_CRYSTALS, getDropAmount(2, 3, enchantments.containsKey(Enchantments.FORTUNE),fortune));
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
            return new ItemStack(Items.GLOWSTONE, getDropAmount(2, 4, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.COBWEB)
        {
            return new ItemStack(Items.STRING, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.BOOKSHELF)
        {
            return new ItemStack(Items.BOOK, getDropAmount(3, 3, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.ANCIENT_DEBRIS)
        {
            return new ItemStack(Items.ANCIENT_DEBRIS, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.EMERALD_ORE)
        {
            return new ItemStack(Items.EMERALD, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
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

        int fortune = 0;
        if(enchantments.containsKey(Enchantments.FORTUNE))
        {
            fortune = enchantments.get(Enchantments.FORTUNE).intValue();
        }

        if(block == Blocks.IRON_ORE)
        {
            return new ItemStack(Items.IRON_INGOT, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
        }
        else if(block == Blocks.GOLD_ORE)
        {
            return new ItemStack(Items.GOLD_INGOT, getDropAmount(1, 1, enchantments.containsKey(Enchantments.FORTUNE),fortune));
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

    private int getDropAmount(int min, int max, boolean hasFortune, int fortune)
    {
        Random rand = new Random();
        if(hasFortune)
        {
            if((max + 3)-(min + 1) > 0)
            {
                return rand.nextInt((max + 3)-(min + 1)) + (min + 1) + (fortune * 2);
            }
            else
            {
                return (min + 1) + (fortune * 2);
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

    private Block getBlock(BlockPos pos, World world) {
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
}
