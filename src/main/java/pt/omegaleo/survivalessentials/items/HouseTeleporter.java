package pt.omegaleo.survivalessentials.items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

import net.minecraft.world.item.Item.Properties;

public class HouseTeleporter extends Item
{
    private static final String NBT_POS = "position";

    public HouseTeleporter()
    {
        super(new Properties().tab(SurvivalEssentialsMod.ITEMS_TAB).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        ItemStack stack = playerIn.getMainHandItem();
        if(playerIn.getItemInHand(handIn).getItem() == ModItems.HOME_TELEPORTER.get())
        {
            if(playerIn.isCrouching())
            {
                String position = round(playerIn.position().x,2) + ";" + round(playerIn.position().y,2) + ";" + round(playerIn.position().z,2);
                setCoordinates(stack, position);
                //System.out.println("Set position to:" + position);
            }
            else
            {
                //System.out.println(getSetCoordinates(stack));
                String[] homePosition = getSetCoordinates(stack).split(";");
                double posX = Double.valueOf(homePosition[0]);
                double posY = Double.valueOf(homePosition[1]);
                double posZ = Double.valueOf(homePosition[2]);
                //System.out.println("Attempting to teleport to: " + posX + ";" + posY + ";" + posZ);

                playerIn.setPos(posX, posY, posZ);
            }
        }

        return super.use(worldIn,playerIn,handIn);
    }


    public static String getSetCoordinates(ItemStack stack) 
    {
        return stack.getOrCreateTag().getString(NBT_POS);
    }

    public static void setCoordinates(ItemStack stack, String position) 
    {
        stack.getOrCreateTag().putString(NBT_POS, position);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
