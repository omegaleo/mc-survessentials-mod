package pt.omegaleo.survivalessentials.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.inventory.ItemFilterContainer;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;

public class ItemFilterUpgrade extends DrillUpgrade
{
    public ItemFilterUpgrade() 
    {
        super();
    }


    public int getInventorySize(ItemStack stack) 
    {
        return 9;
    }

    public List<ItemStack> getItems(ItemStack stack)
    {
        IItemHandler handler = getInventory(stack);
        
        List<ItemStack> items = new ArrayList<ItemStack>();

        for (int i = 0; i < handler.getSlots(); i++)
        {
            if (!handler.getStackInSlot(i).isEmpty()){
                items.add(handler.getStackInSlot(i));
            }
        }

        return items;
    }

    public IItemHandler getInventory(ItemStack stack) 
    {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }

    public void saveInventory(ItemStack stack, IItemHandler itemHandler) 
    {
        if (itemHandler instanceof ItemStackHandler) 
        {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        if (!worldIn.isRemote) 
        {
            playerIn.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, player) -> new ItemFilterContainer(id, playerInventory),
                    new TranslationTextComponent("container.survivalessentials.filter")
            ));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
