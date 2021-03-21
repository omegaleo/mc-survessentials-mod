package pt.omegaleo.survivalessentials.util.enums;

import pt.omegaleo.survivalessentials.ModItems;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModItemTier implements IItemTier 
{
            //Harvest level, Uses, Efficiency, Damage, Enchantability, RepairMaterial
    REDSTONE(3, 800, 12.5f, 3.0f, 30,() -> { return Ingredient.fromItems(ModItems.REDSTONE_INGOT.get()); }),
    CORUNDUM(4, 1000, 13.5f, 5.0f, 27,() -> { return Ingredient.fromItems(ModItems.CORUNDUM_GEM.get()); }),
    MYTHRIL(5, 3000, 16.5f, 8.0f, 30,() -> { return Ingredient.fromItems(ModItems.MYTHRIL_INGOT.get()); });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial)
    {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }
    
}
