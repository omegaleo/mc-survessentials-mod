package pt.omegaleo.survivalessentials;

import net.minecraft.item.crafting.IRecipeSerializer;
import pt.omegaleo.survivalessentials.crafting.recipe.RecolorBackpackRecipe;

public class ModRecipes 
{
    public static void init() 
    {
        IRecipeSerializer.register(RecolorBackpackRecipe.NAME.toString(), RecolorBackpackRecipe.SERIALIZER);
    }
}
