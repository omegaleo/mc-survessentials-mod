package pt.omegaleo.survivalessentials.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.inventory.BlockPlacerContainer;

public class BlockPlacer extends Item {
    public BlockPlacer() {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                playerIn.openContainer(new SimpleNamedContainerProvider(
                        (id, playerInventory, player) -> new BlockPlacerContainer(id, playerInventory),
                        new TranslationTextComponent("container.survivalessentials.blockPlacer")));
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();

        if (player.isSneaking()) {
            player.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, p) -> new BlockPlacerContainer(id, playerInventory),
                    new TranslationTextComponent("container.survivalessentials.blockPlacer")));
        } else {
            ItemStack stack = context.getItem();

            if (stack != null) {
                IItemHandler itemHandler = getInventory(stack);

                if (itemHandler != null) {
                    ItemStack firstBlock = itemHandler.getStackInSlot(0);

                    if (firstBlock != null) {
                        if (firstBlock.getItem() instanceof BlockItem) {
                            BlockItem blockItem = (BlockItem) firstBlock.getItem();

                            blockItem.tryPlace(new BlockItemUseContext(context));

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

        return super.onItemUse(context);
    }

    Boolean isBlockPlacer(ItemStack stack) {
        return stack.getItem() instanceof BlockPlacer;
    }
}
