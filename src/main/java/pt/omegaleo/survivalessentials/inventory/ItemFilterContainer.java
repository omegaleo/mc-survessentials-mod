package pt.omegaleo.survivalessentials.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;

import pt.omegaleo.survivalessentials.ModContainerTypes;
import pt.omegaleo.survivalessentials.items.ItemFilterUpgrade;

public class ItemFilterContainer extends Container 
{
    private final ItemStack item;
    private final IItemHandler itemHandler;
    private final PlayerInventory inventory;
    private int blocked = -1;

    public ItemFilterContainer(int id, PlayerInventory playerInventory) {
        super(ModContainerTypes.itemFilter, id);
        this.item = getHeldItem(playerInventory.player);
        this.itemHandler = ((ItemFilterUpgrade) this.item.getItem()).getInventory(this.item);
        this.inventory = playerInventory;
        // Add backpack slots (3 rows of 9)
        for (int i = 0; i < this.itemHandler.getSlots(); ++i) {
            int x = 8 + 18 * (i % 9);
            int y = 18;
            addSlot(new SlotItemHandler(this.itemHandler, i, x, y));
        }

        final int rowCount = getInventoryRows();
        final int yOffset = (rowCount - 4) * 18;

        // Player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yOffset));
            }
        }

        // Hotbar
        for (int x = 0; x < 9; ++x) {
            Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + yOffset) {
                @Override
                public boolean canTakeStack(PlayerEntity playerIn) {
                    return slotNumber != blocked;
                }
            });

            if (x == playerInventory.currentItem && ItemStack.areItemStacksEqual(playerInventory.getCurrentItem(), this.item)) {
                blocked = slot.slotNumber;
            }
        }
    }

    private static ItemStack getHeldItem(PlayerEntity player) {
        // Determine which held item is a backpack (if either)
        if (isBackpack(player.getHeldItemMainhand())) {
            return player.getHeldItemMainhand();
        }
        if (isBackpack(player.getHeldItemOffhand())) {
            return player.getHeldItemOffhand();
        }
        return ItemStack.EMPTY;
    }

    /**
     * Gets the number of backpack inventory rows. This assumes 9 slots per row.
     *
     * @return The number of rows of backpack slots
     */
    public int getInventoryRows() {
        return 1;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        // Save the inventory to the backpack's NBT
        ((ItemFilterUpgrade) this.item.getItem()).saveInventory(this.item, this.itemHandler);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) 
    {
        Slot slot = this.getSlot(index);

        if (!slot.canTakeStack(player)) 
        {
            return slot.getStack();
        }

        if (index == blocked || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();

        ItemStack newStack = ItemHandlerHelper.copyStackWithSize(stack, 1);

        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.mergeItemStack(newStack, containerSlots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChanged();
        } else if (!this.mergeItemStack(newStack, 0, containerSlots, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

		return slot.onTake(player, newStack);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) 
    {

        if (slotId < 0 || slotId > inventorySlots.size() - 1) 
        {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }

        if(slotId >= 0 && slotId < 9)
        {
            ItemStack stack = ItemHandlerHelper.copyStackWithSize(player.inventory.getItemStack(), 1);
            if (stack.isEmpty())
            {
                if (slotId < inventorySlots.size())
                {
                    inventorySlots.remove(slotId);
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (slotId < inventorySlots.size())
                {
                    inventorySlots.get(slotId).putStack(stack);
                }
    
                return ItemStack.EMPTY;
            }
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    private static boolean isBackpack(ItemStack stack) {
        return stack.getItem() instanceof ItemFilterUpgrade;
    }
}