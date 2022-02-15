package pt.omegaleo.survivalessentials.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import pt.omegaleo.survivalessentials.ModContainerTypes;
import pt.omegaleo.survivalessentials.items.BackpackItem;
import pt.omegaleo.survivalessentials.items.ItemFilterUpgrade;

public class ItemFilterContainer extends AbstractContainerMenu 
{
    private final ItemStack item;
    private final IItemHandler itemHandler;
    private final Inventory inventory;
    private int blocked = -1;

    public ItemFilterContainer(int id, Inventory playerInventory) {
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
                public boolean mayPickup(Player playerIn) {
                    return index != blocked;
                }
            });

            if (x == playerInventory.selected && ItemStack.isSame(playerInventory.getItem(playerInventory.selected), this.item)) {
                blocked = slot.index;
            }
        }
    }

    private static ItemStack getHeldItem(Player player) {
        // Determine which held item is a backpack (if either)
        if (isItemFilter(player.getMainHandItem())) {
            return player.getMainHandItem();
        }
        if (isItemFilter(player.getOffhandItem())) {
            return player.getOffhandItem();
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
    protected void clearContainer(Player p_150412_, Container p_150413_) {
        super.clearContainer(p_150412_, p_150413_);
        ((BackpackItem) this.item.getItem()).saveInventory(this.item, this.itemHandler);
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // This method handles shift-clicking to transfer items quickly. This can easily crash the game if not coded
        // correctly. The first slots (index 0 to whatever) are usually the inventory block/item, while player slots
        // start after those.
        Slot slot = this.getSlot(index);

        if (!slot.mayPickup(playerIn)) {
            return slot.getItem();
        }

        if (index == blocked || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack newStack = stack.copy();

        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.moveItemStackTo(stack, containerSlots, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.setChanged();
        } else if (!this.moveItemStackTo(stack, 0, containerSlots, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(playerIn, newStack);
        return newStack;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
        return super.moveItemStackTo(p_38904_, p_38905_, p_38906_, p_38907_);
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 0 || slotId > slots.size()) {
            super.clicked(slotId, dragType, clickTypeIn, player);
        }

        Slot slot = slots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn) || !isItemFilter(slot.getItem())) {
            return;
        }
        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    private boolean canTake(int slotId, Slot slot, int button, Player player, ClickType clickType) {
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isItemFilter(player.getInventory().getItem(slotId))) {
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
                return !isItemFilter(slot.getItem()) && !isItemFilter(hotbarSlot.getItem()) && isItemFilter(slot.getItem());
            }
        }

        return true;
    }

    private static boolean isItemFilter(ItemStack stack) {
        return stack.getItem() instanceof ItemFilterUpgrade;
    }
}