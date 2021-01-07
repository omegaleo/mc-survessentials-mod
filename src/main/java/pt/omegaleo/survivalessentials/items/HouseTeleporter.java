package pt.omegaleo.survivalessentials.items;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.logging.log4j.util.StringBuilderFormattable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class HouseTeleporter extends Item
{
    private static final String NBT_POS = "position";

    public HouseTeleporter()
    {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("Position set: " + getSetCoordinates(stack)));
        tooltip.add(new TranslationTextComponent("Hold " + "\u00A76" + "Shift" + "\u00A76" + " while right-clicking to set position"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        if(!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItemMainhand();
            if(playerIn.getHeldItem(handIn).getItem() == ModItems.HOME_TELEPORTER.get())
            {
                if(playerIn.isSneaking())
                {
                    String position = round(playerIn.getPosX(),2) + ";" + round(playerIn.getPosY(),2) + ";" + round(playerIn.getPosZ(),2);
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
                    
                    playerIn.setPositionAndUpdate(posX, posY, posZ);
                }
            }
        }

        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
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
