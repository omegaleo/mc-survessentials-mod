package pt.omegaleo.survivalessentials.items;

import java.util.Random;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

    Item[] foodItems = new Item[]
    {
        Items.PORKCHOP,
        Items.COOKED_PORKCHOP,
        Items.MUTTON,
        Items.COOKED_MUTTON,
        Items.CHICKEN,
        Items.COOKED_CHICKEN,
        Items.COD,
        Items.COOKED_COD,
        Items.RABBIT,
        Items.COOKED_RABBIT,
        Items.APPLE,
        Items.GOLDEN_APPLE,
        Items.CARROT,
        Items.GOLDEN_CARROT,
        Items.MELON,
        Items.SWEET_BERRIES,
        Items.BREAD,
        Items.CAKE,
        Items.COOKIE,
        Items.PUFFERFISH,
        Items.SALMON,
        Items.COOKED_SALMON,
        Items.PUMPKIN_PIE,
        Items.BEETROOT_SOUP,
        Items.MUSHROOM_STEW,
        Items.RABBIT_STEW,
        Items.SUSPICIOUS_STEW,
        Items.BEETROOT,
        Items.POISONOUS_POTATO,
        Items.POTATO,
        Items.BAKED_POTATO,
        Items.DRIED_KELP,
        Items.HONEY_BOTTLE
    };

    public LootBag(LootType type) 
    {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(64));
        _type = type;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        Random r = new Random();

        if(_type == LootType.FOOD)
        {
            Item foodResult = foodItems[r.nextInt(foodItems.length)];

            int amount = r.nextInt(4);

            if(amount == 0) amount = 1; //To guarantee it always drops at least 1

            if(foodResult != null)
                playerIn.inventory.addItemStackToInventory(new ItemStack(foodResult, amount));
        }

        playerIn.getHeldItem(handIn).shrink(1);

        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
