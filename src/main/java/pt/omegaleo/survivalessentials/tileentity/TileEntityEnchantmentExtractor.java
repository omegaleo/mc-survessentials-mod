package pt.omegaleo.survivalessentials.tileentity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import pt.omegaleo.survivalessentials.ModTileEntities;
import pt.omegaleo.survivalessentials.blocks.BlockEnchantmentExtractor;
import pt.omegaleo.survivalessentials.containers.EnchantmentExtractorContainer;
import pt.omegaleo.survivalessentials.util.enums.FurnaceStateData;
import pt.omegaleo.survivalessentials.util.enums.FurnaceZoneContents;
import pt.omegaleo.survivalessentials.util.enums.SetBlockStateFlag;

public class TileEntityEnchantmentExtractor extends TileEntity implements INamedContainerProvider, ITickableTileEntity 
{

    public static final int INPUT_SLOTS_COUNT = 5;
    public static final int OUTPUT_SLOTS_COUNT = 5;
    public static final int TOTAL_SLOTS_COUNT = INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    private FurnaceZoneContents inputZoneContents;
    private FurnaceZoneContents outputZoneContents;

    private final FurnaceStateData furnaceStateData = new FurnaceStateData();

    public TileEntityEnchantmentExtractor() {
        super(ModTileEntities.enchantment_extractor.get());
        inputZoneContents = FurnaceZoneContents.createForTileEntity(INPUT_SLOTS_COUNT, this::canPlayerAccessInventory,
                this::markDirty);
        outputZoneContents = FurnaceZoneContents.createForTileEntity(OUTPUT_SLOTS_COUNT, this::canPlayerAccessInventory,
                this::markDirty);
    }

    // Return true if the given player is able to use this block. In this case it
    // checks that
    // 1) the world tileentity hasn't been replaced in the meantime, and
    // 2) the player isn't too far away from the centre of the block
    public boolean canPlayerAccessInventory(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this)
            return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET,
                pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    // This method is called every tick to update the tile entity, i.e.
    // - see if the fuel has run out, and if so turn the furnace "off" and slowly
    // uncook the current item (if any)
    // - see if the current smelting input item has finished smelting; if so,
    // convert it to output
    // - burn fuel slots
    // It runs both on the server and the client but we only need to do updates on
    // the server side.
    @Override
    public void tick() {
        if (world.isRemote)
            return; // do nothing on client.
        ItemStack currentlySmeltingItem = getCurrentlySmeltingInputItem();

        // if user has changed the input slots, reset the smelting time
        if (!ItemStack.areItemsEqual(currentlySmeltingItem, currentlySmeltingItemLastTick)) { // == and != don't work!
            furnaceStateData.cookTimeElapsed = 0;
        }
        currentlySmeltingItemLastTick = currentlySmeltingItem.copy();

        if (!currentlySmeltingItem.isEmpty()) {

            // If fuel is available, keep cooking the item, otherwise start "uncooking" it
            // at double speed
            int cookTimeForCurrentItem = getCookTime(this.world, currentlySmeltingItem);
            furnaceStateData.cookTimeForCompletion = cookTimeForCurrentItem;
            // If cookTime has reached maxCookTime smelt the item and reset cookTime
            if (furnaceStateData.cookTimeElapsed >= cookTimeForCurrentItem) {
                smeltFirstSuitableInputItem();
                furnaceStateData.cookTimeElapsed = 0;
            }
        } else {
            furnaceStateData.cookTimeElapsed = 0;
        }
    }

    /**
     * Check if any of the input item are smeltable and there is sufficient space in
     * the output slots
     * 
     * @return the ItemStack of the first input item that can be smelted;
     *         ItemStack.EMPTY if none
     */
    private ItemStack getCurrentlySmeltingInputItem() {
        return smeltFirstSuitableInputItem(false);
    }

    /**
     * Smelt an input item into an output slot, if possible
     */
    private void smeltFirstSuitableInputItem() {
        smeltFirstSuitableInputItem(true);
    }

    /**
     * checks that there is an item to be smelted in one of the input slots and that
     * there is room for the result in the output slots If desired, performs the
     * smelt
     * 
     * @param performSmelt if true, perform the smelt. if false, check whether
     *                     smelting is possible, but don't change the inventory
     * @return a copy of the ItemStack of the input item smelted or to-be-smelted
     */
    private ItemStack smeltFirstSuitableInputItem(boolean performSmelt) {
        Integer firstSuitableInputSlot = null;
        Integer firstSuitableOutputSlot = null;
        ItemStack result = ItemStack.EMPTY;

        // finds the first input slot which is smeltable and whose result fits into an
        // output slot (stacking if possible)
        for (int inputIndex = 0; inputIndex < INPUT_SLOTS_COUNT; inputIndex++) {
            ItemStack itemStackToSmelt = inputZoneContents.getStackInSlot(inputIndex);
            if (!itemStackToSmelt.isEmpty()) {
                result = getSmeltingResultForItem(this.world, itemStackToSmelt);
                if (!result.isEmpty()) {
                    // find the first suitable output slot- either empty, or with identical item
                    // that has enough space
                    for (int outputIndex = 0; outputIndex < OUTPUT_SLOTS_COUNT; outputIndex++) {
                        if (willItemStackFit(outputZoneContents, outputIndex, result)) {
                            firstSuitableInputSlot = inputIndex;
                            firstSuitableOutputSlot = outputIndex;
                            break;
                        }
                    }
                    if (firstSuitableInputSlot != null)
                        break;
                }
            }
        }

        if (firstSuitableInputSlot == null)
            return ItemStack.EMPTY;

        ItemStack returnvalue = inputZoneContents.getStackInSlot(firstSuitableInputSlot).copy();
        /*if (!performSmelt)
            return returnvalue;*/

        // alter input and output
        inputZoneContents.decrStackSize(firstSuitableInputSlot, 1);
        outputZoneContents.increaseStackSize(firstSuitableOutputSlot, result);

        markDirty();
        return returnvalue;
    }

    /**
     * Will the given ItemStack fully fit into the target slot?
     * 
     * @param furnaceZoneContents
     * @param slotIndex
     * @param itemStackOrigin
     * @return true if the given ItemStack will fit completely; false otherwise
     */
    public boolean willItemStackFit(FurnaceZoneContents furnaceZoneContents, int slotIndex, ItemStack itemStackOrigin) {
        ItemStack itemStackDestination = furnaceZoneContents.getStackInSlot(slotIndex);

        if (itemStackDestination.isEmpty() || itemStackOrigin.isEmpty()) {
            return true;
        }

        if (!itemStackOrigin.isItemEqual(itemStackDestination)) {
            return false;
        }

        int sizeAfterMerge = itemStackDestination.getCount() + itemStackOrigin.getCount();
        if (sizeAfterMerge <= furnaceZoneContents.getInventoryStackLimit()
                && sizeAfterMerge <= itemStackDestination.getMaxStackSize()) {
            return true;
        }
        return false;
    }

    // returns the smelting result for the given stack. Returns ItemStack.EMPTY if
    // the given stack can not be smelted
    public static ItemStack getSmeltingResultForItem(World world, ItemStack itemStack) 
    {       
        if(EnchantmentHelper.getEnchantments(itemStack).size() > 0)
        {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);            
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);


            if (map.size() > 0) 
            {
                EnchantmentHelper.setEnchantments(map, book);
            }

            return book.copy();
        }

        return ItemStack.EMPTY; // beware! You must deep copy otherwise you will alter the
    }

    // returns the number of ticks the given item will burn. Returns 0 if the given
    // item is not a valid fuel
    public static int getItemBurnTime(World world, ItemStack stack) 
    {
        return 500;
    }

    /**
     * Gets the cooking time for this recipe input
     * 
     * @param world
     * @param itemStack the input item to be smelted
     * @return cooking time (ticks) or 0 if no matching recipe
     */
    public static int getCookTime(World world, ItemStack itemStack) {
        return 500;
    }

    // Return true if the given stack is allowed to be inserted in the given slot
    // Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
    static public boolean isItemValidForFuelSlot(ItemStack itemStack) {
        return true;
    }

    // Return true if the given stack is allowed to be inserted in the given slot
    // Unlike the vanilla furnace, we allow anything to be placed in the input slots
    static public boolean isItemValidForInputSlot(ItemStack itemStack) {
        return true;
    }

    // Return true if the given stack is allowed to be inserted in the given slot
    static public boolean isItemValidForOutputSlot(ItemStack itemStack) {
        return false;
    }

    // ------------------------------
    private final String INPUT_SLOTS_NBT = "inputSlots";
    private final String OUTPUT_SLOTS_NBT = "outputSlots";

    // This is where you save any data that you don't want to lose when the tile
    // entity unloads
    // In this case, it saves the state of the furnace (burn time etc) and the
    // itemstacks stored in the fuel, input, and output slots
    @Override
    public CompoundNBT write(CompoundNBT parentNBTTagCompound) {
        super.write(parentNBTTagCompound); // The super call is required to save and load the tile's location

        furnaceStateData.putIntoNBT(parentNBTTagCompound);
        parentNBTTagCompound.put(INPUT_SLOTS_NBT, inputZoneContents.serializeNBT());
        parentNBTTagCompound.put(OUTPUT_SLOTS_NBT, outputZoneContents.serializeNBT());
        return parentNBTTagCompound;
    }

    // This is where you load the data that you saved in writeToNBT
    @Override
    public void read(BlockState state, CompoundNBT nbtTagCompound) {
        super.read(state, nbtTagCompound); // The super call is required to save and load the tile's location

        furnaceStateData.readFromNBT(nbtTagCompound);

        CompoundNBT inventoryNBT = nbtTagCompound.getCompound(INPUT_SLOTS_NBT);
        inputZoneContents.deserializeNBT(inventoryNBT);

        inventoryNBT = nbtTagCompound.getCompound(OUTPUT_SLOTS_NBT);
        outputZoneContents.deserializeNBT(inventoryNBT);

        if (inputZoneContents.getSizeInventory() != INPUT_SLOTS_COUNT
                || outputZoneContents.getSizeInventory() != OUTPUT_SLOTS_COUNT)
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
    }

    /**
     * When this tile entity is destroyed, drop all of its contents into the world
     * 
     * @param world
     * @param blockPos
     */
    public void dropAllContents(World world, BlockPos blockPos) {
        InventoryHelper.dropInventoryItems(world, blockPos, inputZoneContents);
        InventoryHelper.dropInventoryItems(world, blockPos, outputZoneContents);
    }

    /**
     * The name is misleading; createMenu has nothing to do with creating a Screen,
     * it is used to create the Container on the server only
     * 
     * @param windowID
     * @param playerInventory
     * @param playerEntity
     * @return
     */
    private ItemStack currentlySmeltingItemLastTick = ItemStack.EMPTY;

    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return EnchantmentExtractorContainer.createContainerServerSide(windowID, playerInventory, inputZoneContents,
                outputZoneContents, furnaceStateData);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.survivalessentials.enchantmentextractor");
    }
}