package pt.omegaleo.survivalessentials.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.BookContainer;

public class ModBook extends Item
{

    public ModBook() 
    {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        if (!worldIn.isRemote) 
        {
            playerIn.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, player) -> new BookContainer(id, playerInventory),
                    new TranslationTextComponent("container.survivalessentials.modbook")
            ));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
