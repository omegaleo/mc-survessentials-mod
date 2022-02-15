package pt.omegaleo.survivalessentials.items;

import java.awt.Color;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;

import net.minecraft.world.item.Item.Properties;

public class BackpackItem extends Item 
{
    private static final String NBT_COLOR = "BackpackColor";

    public BackpackItem() 
    {
        super(new Properties().tab(SurvivalEssentialsMod.ITEMS_TAB).stacksTo(1));
    }
    
    public int getInventorySize(ItemStack stack) 
    {
        return 27;
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
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
    {
        p_41433_.openMenu(new SimpleMenuProvider(
                (id, playerInventory, player) -> new BackpackContainer(id, playerInventory),
                new TranslatableComponent("container.survivalessentials.backpack")
        ));
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    public static int getBackpackColor(ItemStack stack) 
    {
        return stack.getOrCreateTag().getInt(NBT_COLOR);
    }

    public static void setBackpackColor(ItemStack stack, int color) 
    {
        stack.getOrCreateTag().putInt(NBT_COLOR, color);
    }

    public static int getItemColor(ItemStack stack, int tintIndex)
    {
        if (tintIndex == 0) 
        {
            return getBackpackColor(stack);
        }
        return Color.WHITE.getRGB();
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        for (DyeColor color : DyeColor.values())
        {
            ItemStack stack = new ItemStack(this);
            setBackpackColor(stack, color.getFireworkColor());
            items.add(stack);
        }
    }
}
