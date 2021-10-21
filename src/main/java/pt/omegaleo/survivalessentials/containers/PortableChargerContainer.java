package pt.omegaleo.survivalessentials.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import pt.omegaleo.survivalessentials.ModContainerTypes;
import pt.omegaleo.survivalessentials.items.PortableChargerItem;
import pt.omegaleo.survivalessentials.util.enums.EnergyStorageItem;

public class PortableChargerContainer extends Container
{
    private final ItemStack item;
    private final IItemHandler itemHandler;
    private int blocked = -1;

    private int fuelSlot = 0;
    private int energyStorageSlot = 1;

    public PortableChargerContainer(int id, PlayerInventory playerInventory) 
    {
        super(ModContainerTypes.portableChargerContainer, id);

        this.item = getHeldItem(playerInventory.player);
        this.itemHandler = ((PortableChargerItem) this.item.getItem()).getInventory(this.item);

        // Add backpack slots (3 rows of 9)
        System.out.println("Slots: " + this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); ++i) 
        {
            int x = 134 + 18;
            int y = 18 + 18 * i;
            addSlot(new SlotItemHandler(this.itemHandler, i, x, y));
        }

        final int rowCount = getInventoryRows();
        final int yOffset = (rowCount - 4) * 18;

        // Player inventory
        for (int y = 0; y < 3; ++y) 
        {
            for (int x = 0; x < 9; ++x) 
            {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yOffset));
            }
        }

        // Hotbar
        for (int x = 0; x < 9; ++x) {
            Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + yOffset) 
            {
                @Override
                public boolean canTakeStack(PlayerEntity playerIn) 
                {
                    return slotNumber != blocked;
                }
            });

            if (x == playerInventory.currentItem && ItemStack.areItemStacksEqual(playerInventory.getCurrentItem(), this.item)) 
            {
                blocked = slot.slotNumber;
            }
        }
    }

	@Override
    public boolean canInteractWith(PlayerEntity playerIn) 
    {
        return true;
    }


    private static ItemStack getHeldItem(PlayerEntity player) 
    {
        // Determine which held item is a backpack (if either)
        if (player.getHeldItemMainhand().getItem() instanceof PortableChargerItem) 
        {
            return player.getHeldItemMainhand();
        }
        if (player.getHeldItemOffhand().getItem() instanceof PortableChargerItem) 
        {
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
        return 2;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        // Save the inventory to the backpack's NBT
        ((PortableChargerItem) this.item.getItem()).saveInventory(this.item, this.itemHandler);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        // This method handles shift-clicking to transfer items quickly. This can easily crash the game if not coded
        // correctly. The first slots (index 0 to whatever) are usually the inventory block/item, while player slots
        // start after those.
        Slot slot = this.getSlot(index);

        if (!slot.canTakeStack(playerIn)) {
            return slot.getStack();
        }

        if (index == blocked || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();

        ItemStack newStack = stack.copy();

        if (isFuel(stack))
        {
            Slot fuelSlotObj = this.inventorySlots.get(fuelSlot);
            ItemStack currentItemInSlot = fuelSlotObj.getStack();
            if(isFuel(currentItemInSlot))
            {
                if(currentItemInSlot.getItem() == stack.getItem())
                {
                    this.mergeItemStack(stack, fuelSlot, fuelSlot, false);
                }
            }
            else
            {
                //Slot is empty
                this.mergeItemStack(stack, fuelSlot, fuelSlot, false);
            }
            slot.onSlotChanged();
        }

        if (isEnergyStorage(stack))
        {
            Slot energySlotObj = this.inventorySlots.get(energyStorageSlot);
            ItemStack currentItemInSlot = energySlotObj.getStack();
            if(!isEnergyStorage(currentItemInSlot))
            {
                //Slot is empty, put item in slot
                energySlotObj.putStack(currentItemInSlot);
                slot.onSlotChanged();
            }
        }

        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.mergeItemStack(stack, containerSlots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChanged();
        } else if (!this.mergeItemStack(stack, 0, containerSlots, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        return slot.onTake(playerIn, newStack);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) 
    {
        if (slotId < 0 || slotId > inventorySlots.size()) 
        {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }

        Slot slot = inventorySlots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) 
        {
            return slot.getStack();
        }

        if(!isEnergyStorage(slot.getStack()) && !isFuel(slot.getStack()) && !slot.getStack().isEmpty())
        {
            return slot.getStack();
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    private static boolean isCharger(ItemStack stack)
    {
        return stack.getItem() instanceof PortableChargerItem;
    }

    private static boolean isFuel(ItemStack stack) 
    {
        Item item = stack.getItem();
        return item == Items.COAL || item == Items.COAL_BLOCK || item == Items.CHARCOAL;
    }

    private static int getEnergyAmount(ItemStack stack) 
    {
        if (isFuel(stack))
        {
            Item item = stack.getItem();
            if (item == Items.COAL) { return 100; }
            if (item == Items.COAL_BLOCK) { return 900; }
            if (item == Items.CHARCOAL) { return 50; }
        }
        
        return 0;
    }
    
    private static boolean isEnergyStorage(ItemStack stack) 
    {
        return stack.getItem() instanceof EnergyStorageItem;
    }

    private boolean canTake(int slotId, Slot slot, int button, PlayerEntity player, ClickType clickType) {
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isCharger(player.inventory.getItemStack())) 
        {
            return false;
        }
        
        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (blocked == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !isCharger(slot.getStack()) && !isCharger(hotbarSlot.getStack());
            }
        }

        return true;
    }

    @Override
    public void detectAndSendChanges() 
    {
        System.out.println("TICK");
        PortableChargerItem charger = (PortableChargerItem)item.getItem();
        System.out.println("Fuel Slot: " + itemHandler.getStackInSlot(fuelSlot));
        System.out.println("Energy Slot: " + itemHandler.getStackInSlot(energyStorageSlot));
        if (itemHandler.getStackInSlot(fuelSlot) != null)
        {
            ItemStack fuelStack = itemHandler.getStackInSlot(fuelSlot);
            if(isFuel(fuelStack))
            {
                charger.InsertEnergy(item, getEnergyAmount(fuelStack));
                fuelStack.setCount(fuelStack.getCount() - 1);
            }
        }

        if (itemHandler.getStackInSlot(energyStorageSlot) != null)
        {
            ItemStack energyStack = itemHandler.getStackInSlot(energyStorageSlot);
            if(isEnergyStorage(energyStack))
            {
                EnergyStorageItem energyStorage = (EnergyStorageItem)energyStack.getItem();
                int energyExtracted = charger.ExtractEnergy(item, 10);
                energyStorage.InsertEnergy(item, energyExtracted);
            }

        }
        super.detectAndSendChanges();
    }
}
