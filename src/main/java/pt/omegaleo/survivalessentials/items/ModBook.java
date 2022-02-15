package pt.omegaleo.survivalessentials.items;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.BookContainer;

import net.minecraft.world.item.Item.Properties;
import pt.omegaleo.survivalessentials.inventory.ItemFilterContainer;

public class ModBook extends Item
{

    public ModBook() 
    {
        super(new Properties().tab(SurvivalEssentialsMod.ITEMS_TAB).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41433_.openMenu(new SimpleMenuProvider(
                (id, playerInventory, player) -> new BookContainer(id, playerInventory),
                new TranslatableComponent("container.survivalessentials.modbook")
        ));

        return super.use(p_41432_, p_41433_, p_41434_);
    }
}
