package pt.omegaleo.survivalessentials.items;

import java.util.Random;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.BookContainer;
import pt.omegaleo.survivalessentials.util.enums.GlobalEnums.LootType;

import net.minecraft.world.item.Item.Properties;

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
        super(new Properties().tab(SurvivalEssentialsMod.ITEMS_TAB).stacksTo(64));
        _type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player playerIn, InteractionHand handIn) {
        Random r = new Random();

        if(_type == LootType.FOOD)
        {
            Item foodResult = foodItems[r.nextInt(foodItems.length)];

            int amount = r.nextInt(4);

            if(amount == 0) amount = 1; //To guarantee it always drops at least 1

            if(foodResult != null)
                playerIn.getInventory().add(new ItemStack(foodResult, amount));
        }

        playerIn.getItemInHand(handIn).shrink(1);

        return super.use(p_41432_, playerIn, handIn);
    }

}
