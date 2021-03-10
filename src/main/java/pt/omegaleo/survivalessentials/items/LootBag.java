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
import pt.omegaleo.survivalessentials.util.enums.GlobalEnums.LootType;

public class LootBag extends Item
{
    LootType _type;

    public LootBag(LootType type) 
    {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
        _type = type;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        if(_type == LootType.FOOD)
        {
            
        }

        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
