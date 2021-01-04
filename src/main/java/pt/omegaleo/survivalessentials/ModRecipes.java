package pt.omegaleo.survivalessentials;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.crafting.recipe.RecolorBackpackRecipe;

public class ModRecipes 
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister
                .create(ForgeRegistries.RECIPE_SERIALIZERS, SurvivalEssentialsMod.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<RecolorBackpackRecipe>> recolorBackpackRecipe = RECIPES
                .register(RecolorBackpackRecipe.NAME.toString(), 
                () -> RecolorBackpackRecipe.SERIALIZER);
}
