package pt.omegaleo.survivalessentials.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
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
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41433_.openMenu(new SimpleMenuProvider(
                (id, playerInventory, player) -> new ItemFilterContainer(id, playerInventory),
                new TranslatableComponent("container.survivalessentials.filter")
        ));

        return super.use(p_41432_, p_41433_, p_41434_);
    }

}
