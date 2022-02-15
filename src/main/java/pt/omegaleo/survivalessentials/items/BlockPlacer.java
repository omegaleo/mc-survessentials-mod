package pt.omegaleo.survivalessentials.items;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.inventory.BlockPlacerContainer;

import net.minecraft.world.item.Item.Properties;

public class BlockPlacer extends Item {
    public BlockPlacer() {
        super(new Properties().tab(SurvivalEssentialsMod.ITEMS_TAB).stacksTo(1));
    }

    public int getInventorySize(ItemStack stack) {
        return 54;
    }

    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }

    public int getSelectedSlot(ItemStack stack) {
        return stack.getOrCreateTag().getInt("SelectedSlot");
    }

    public void saveSelectedSlot(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt("SelectedSlot", value);
    }

    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if (itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player playerIn, InteractionHand p_41434_) {

        if (playerIn.isCrouching())
        {
            playerIn.openMenu(new SimpleMenuProvider(
                    (id, playerInventory, player) -> new BlockPlacerContainer(id, playerInventory),
                    new TranslatableComponent("container.survivalessentials.blockPlacer")));
        }
        else
        {
            ItemStack stack = playerIn.getMainHandItem();

            if (stack != null) {
                IItemHandler itemHandler = getInventory(stack);

                if (itemHandler != null) {
                    ItemStack firstBlock = itemHandler.getStackInSlot(0);

                    if (firstBlock != null) {
                        if (firstBlock.getItem() instanceof BlockItem) {
                            BlockItem blockItem = (BlockItem) firstBlock.getItem();

                            blockItem.use(p_41432_, playerIn, p_41434_);

                            itemHandler.extractItem(0, 1, false);

                            if (itemHandler.getStackInSlot(0).getItem() == Items.AIR) {
                                ItemStack stackToReplace = null;
                                int slotTaken = 0;

                                for (int i = 1; i < getInventorySize(stack); i++) {
                                    if (itemHandler.getStackInSlot(i).getItem() != Items.AIR) {
                                        stackToReplace = itemHandler.getStackInSlot(i);
                                        slotTaken = i;
                                        break;
                                    }
                                }

                                if (stackToReplace != null) {
                                    itemHandler.insertItem(0, stackToReplace.copy(), false);
                                    itemHandler.extractItem(slotTaken, stackToReplace.getCount(), false);
                                }
                            }

                            saveInventory(stack, itemHandler);
                        }
                    }
                }
            }
        }

        return super.use(p_41432_, playerIn, p_41434_);
    }


    Boolean isBlockPlacer(ItemStack stack) {
        return stack.getItem() instanceof BlockPlacer;
    }
}
