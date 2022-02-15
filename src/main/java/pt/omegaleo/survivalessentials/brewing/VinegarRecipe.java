package pt.omegaleo.survivalessentials.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import pt.omegaleo.survivalessentials.ModItems;

public class VinegarRecipe implements IBrewingRecipe {

    private ItemStack output = new ItemStack(ModItems.VINEGAR.get());

    public VinegarRecipe() {
    }

    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotion(input) == Potions.WATER;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == Items.APPLE;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (ingredient != null && input != null && isIngredient(ingredient)) {
            ItemStack output = this.output.copy();
            if (input != output) {
                return output;
            } else {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }
}