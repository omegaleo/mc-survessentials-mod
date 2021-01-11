package pt.omegaleo.survivalessentials.util.enums;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class Battery extends Item
{
    private int curEnergy = 0;
    private int maxEnergy;

    public Battery(int maxEnergy) 
    {
        super(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(1).maxDamage(maxEnergy));
        this.maxEnergy = maxEnergy;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent(
                "Energy: " + curEnergy + "/" + maxEnergy));
        /*tooltip.add(new TranslationTextComponent(
                "Hold " + "\u00A76" + "Sneak" + "\u00A76" + " while right-clicking to open drill menu"));*/
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) 
    {
        //Always show bar
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) 
    {
        return (maxEnergy - curEnergy);
    }
}
