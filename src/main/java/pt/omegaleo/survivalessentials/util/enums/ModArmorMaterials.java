package pt.omegaleo.survivalessentials.util.enums;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;

import java.util.function.Supplier;

public class ModArmorMaterials implements ArmorMaterial
{
    public final static ModArmorMaterials WOOD = new ModArmorMaterials(SurvivalEssentialsMod.MOD_ID+":wood", 5, new int[] {1,2,1,1}, 18, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> {return Ingredient.of(Items.BIRCH_WOOD);});
    public final static ModArmorMaterials REDSTONE = new ModArmorMaterials(SurvivalEssentialsMod.MOD_ID+":redstone",27,new int[]{1,4,3,1}, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 1.5F, () -> {return Ingredient.of(ModItems.REDSTONE_INGOT.get());});
    public final static ModArmorMaterials CORUNDUM = new ModArmorMaterials(SurvivalEssentialsMod.MOD_ID+":corundum",30,new int[]{2,6,4,2}, 27, SoundEvents.ARMOR_EQUIP_GENERIC, 1.75F, () -> {return Ingredient.of(ModItems.CORUNDUM_GEM.get());});
    public final static ModArmorMaterials MYTHRIL = new ModArmorMaterials(SurvivalEssentialsMod.MOD_ID+":mythril",45,new int[]{5,10,5,4}, 30, SoundEvents.ARMOR_EQUIP_GENERIC, 4.0F, () -> {return Ingredient.of(ModItems.MYTHRIL_INGOT.get());});

    private static final int[] MAX_DAMAGE_ARRAY = new int[] { 11, 16, 15, 13 };
    private final String name;
    private final int maxDamageFactor; //Durability, Iron=15, Diamond=33, Gold=7, Leather=5
    private final int[] damageReductionAmountArray; //Armor Bar Protection, 1 = 1/2 armor bar
    private final int enchantability;
    private final net.minecraft.sounds.SoundEvent soundEvent;
    private final float toughness; //Increases Protection, 0.0F=Iron/Gold/Leather, 2.0F=Diamond, 3.0F=Netherite
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, net.minecraft.sounds.SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    public int getMaxDamageFactor() {
        return maxDamageFactor;
    }


    @Override
    public int getDurabilityForSlot(EquipmentSlot slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getEquipSound() {
        return this.soundEvent;
    }


    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        // TODO Auto-generated method stub
        return 0;
    }
}
