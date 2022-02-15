package pt.omegaleo.survivalessentials.util.tools;

import net.minecraft.world.InteractionResult;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class RedstonePickaxe extends PickaxeItem
{

    public RedstonePickaxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED));
        super.onUsingTick(stack, player, count);
    }
}
