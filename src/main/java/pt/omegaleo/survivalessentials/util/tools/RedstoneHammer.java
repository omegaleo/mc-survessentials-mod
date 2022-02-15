package pt.omegaleo.survivalessentials.util.tools;

import java.util.Set;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

public class RedstoneHammer extends HammerTool 
{
    public RedstoneHammer(Tier tier, int attackDamageIn, float attackSpeedIn) 
    {
        super(tier, attackDamageIn, attackSpeedIn, 10);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED));
        super.onUsingTick(stack, player, count);
    }
}
