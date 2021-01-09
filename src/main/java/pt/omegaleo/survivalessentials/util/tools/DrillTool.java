package pt.omegaleo.survivalessentials.util.tools;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
                try {
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
                                            worldIn.destroyBlock(blocksToDestroy[i], false);
                                        }
                                        else
                                        {
                                            worldIn.destroyBlock(blocksToDestroy[i], true);
                                        }
                                    }
                                }
                                else
                                {
                                    worldIn.destroyBlock(blocksToDestroy[i], true);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
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
