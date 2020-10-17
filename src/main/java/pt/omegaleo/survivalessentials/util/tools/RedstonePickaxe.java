package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResultType;

public class RedstonePickaxe extends PickaxeItem
{

    public RedstonePickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
    

    @Override
    public ActionResultType onItemUse(ItemUseContext context) 
    {

        context.getPlayer().addPotionEffect(new EffectInstance(Effect.get(3).getEffect())); //add Haste

        return super.onItemUse(context);
    }
    
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        player.addPotionEffect(new EffectInstance(Effect.get(3).getEffect()));
        super.onUsingTick(stack, player, count);
    }
}
