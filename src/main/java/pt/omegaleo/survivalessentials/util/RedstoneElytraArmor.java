package pt.omegaleo.survivalessentials.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RedstoneElytraArmor extends ArmorItem {

    public RedstoneElytraArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) 
    {
        super(materialIn, slot, builderIn);
    }
    //instead of just gliding, allow flying

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
        if (!player.abilities.allowFlying) 
        {
            player.abilities.allowFlying = true;
        } else 
        {
            player.abilities.allowFlying = false;
        }
        super.onArmorTick(stack, world, player);
    }
}
