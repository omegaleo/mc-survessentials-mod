package pt.omegaleo.survivalessentials.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistryEntry;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.items.BackpackItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class RecolorBackpackRecipe extends CustomRecipe
{

    public static final ResourceLocation NAME = SurvivalEssentialsMod.getId("recolorbackpack");
    public static final Serializer SERIALIZER = new Serializer();

    public RecolorBackpackRecipe(ResourceLocation idIn) 
    {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) 
    {
        int backpackCount = 0;
        int dyeCount = 0;

        for (int i = 0; i < inv.getContainerSize(); ++i)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) 
            {
                if (stack.getItem() instanceof BackpackItem) 
                {
                    ++backpackCount;
                } 
                else if (stack.getItem().getCreativeTabs().contains(Tags.Items.DYES))
                {
                    ++dyeCount;
                } 
                else 
                {
                    return false;
                }
            }
        }

        return backpackCount == 1 && dyeCount > 0;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack backpack = ItemStack.EMPTY;
        Collection<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < inv.getContainerSize(); ++i)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty())
            {
                if (stack.getItem() instanceof BackpackItem)
                {
                    backpack = stack;
                }
                else if (stack.getItem().getCreativeTabs().contains(Tags.Items.DYES))
                {
                    dyes.add(stack);
                }
            }
        }

        ItemStack result = backpack.copy();
        applyDyes(result, dyes);
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static void applyDyes(ItemStack backpack, Collection<ItemStack> dyes) 
    {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int backpackColor = BackpackItem.getBackpackColor(backpack);
        if (backpackColor != DyeColor.WHITE.getFireworkColor())
        {
            float r = (float) (backpackColor >> 16 & 255) / 255.0F;
            float g = (float) (backpackColor >> 8 & 255) / 255.0F;
            float b = (float) (backpackColor & 255) / 255.0F;
            maxColorSum = (int) ((float) maxColorSum + Math.max(r, Math.max(g, b)) * 255.0F);
            componentSums[0] = (int) ((float) componentSums[0] + r * 255.0F);
            componentSums[1] = (int) ((float) componentSums[1] + g * 255.0F);
            componentSums[2] = (int) ((float) componentSums[2] + b * 255.0F);
            ++colorCount;
        }

        for (ItemStack dye : dyes) 
        {
            DyeColor dyeColor = DyeColor.getColor(dye);
            if (dyeColor != null) 
            {
                float[] componentValues = dyeColor.getTextureDiffuseColors();
                int r = (int) (componentValues[0] * 255.0F);
                int g = (int) (componentValues[1] * 255.0F);
                int b = (int) (componentValues[2] * 255.0F);
                maxColorSum += Math.max(r, Math.max(g, b));
                componentSums[0] += r;
                componentSums[1] += g;
                componentSums[2] += b;
                ++colorCount;
            }
        }

        if (colorCount > 0) 
        {
            int r = componentSums[0] / colorCount;
            int g = componentSums[1] / colorCount;
            int b = componentSums[2] / colorCount;
            float maxAverage = (float) maxColorSum / (float) colorCount;
            float max = (float) Math.max(r, Math.max(g, b));
            r = (int) ((float) r * maxAverage / max);
            g = (int) ((float) g * maxAverage / max);
            b = (int) ((float) b * maxAverage / max);
            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;
            BackpackItem.setBackpackColor(backpack, finalColor);
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RecolorBackpackRecipe>
    {

        @Override
        public RecolorBackpackRecipe fromJson(ResourceLocation p_44103_, JsonObject p_44104_) {
            return new RecolorBackpackRecipe(p_44103_);
        }

        @Nullable
        @Override
        public RecolorBackpackRecipe fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
            return new RecolorBackpackRecipe(p_44105_);
        }

        @Override
        public void toNetwork(FriendlyByteBuf p_44101_, RecolorBackpackRecipe p_44102_) {

        }
    }
}
