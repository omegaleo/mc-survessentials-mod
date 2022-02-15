package pt.omegaleo.survivalessentials;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pt.omegaleo.survivalessentials.crafting.recipe.RecolorBackpackRecipe;

public class ModRecipes 
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister
                .create(ForgeRegistries.RECIPE_SERIALIZERS, SurvivalEssentialsMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RecolorBackpackRecipe>> recolorBackpackRecipe = RECIPES
                .register("recolorbackpack", 
                () -> RecolorBackpackRecipe.SERIALIZER);
}
