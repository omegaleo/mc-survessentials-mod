package pt.omegaleo.survivalessentials.util.enums;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class EnergyStorageItem extends Item
{
    private int curEnergy = 0;
    private int maxEnergy;

    public EnergyStorageItem(int maxEnergy) 
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
        curEnergy = getEnergy(stack);
        return (maxEnergy - curEnergy);
    }

    public int getEnergy(ItemStack stack) 
    {
        return stack.getOrCreateTag().getInt("curEnergy");
    }

    public void InsertEnergy(ItemStack stack, int energy) 
    {
        if (canInsertEnergy(stack, energy)) 
        {
            curEnergy += energy;
        }
        else
        {
            curEnergy = maxEnergy;
        }
        stack.getOrCreateTag().putInt("curEnergy", curEnergy);
    }

    boolean canExtractEnergy(ItemStack stack, int energy)
    {
        this.curEnergy = getEnergy(stack);
        return curEnergy >= energy;
    }

    public int ExtractEnergy(ItemStack stack, int energy)
    {
        this.curEnergy = getEnergy(stack);
        if(canExtractEnergy(stack, energy))
        {
            this.curEnergy -= energy;
            stack.getOrCreateTag().putInt("curEnergy", curEnergy);
            return energy;
        }
        return 0;
    }

    boolean canInsertEnergy(ItemStack stack, int energy)
    {
        this.curEnergy = getEnergy(stack);
        return maxEnergy >= (this.curEnergy + energy);
    }
}
