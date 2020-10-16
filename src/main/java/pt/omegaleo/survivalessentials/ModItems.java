package pt.omegaleo.survivalessentials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.util.enums.ModArmorMaterials;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            SurvivalEssentialsMod.MOD_ID);

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

    // Armor
    public static final RegistryObject<ArmorItem> WOOD_HELMET = ITEMS.register("wood_helmet",
            () -> new ArmorItem(ModArmorMaterials.WOOD, EquipmentSlotType.HEAD,
                    new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

    public static final RegistryObject<ArmorItem> WOOD_CHESTPLATE = ITEMS.register("wood_chestplate",
            () -> new ArmorItem(ModArmorMaterials.WOOD, EquipmentSlotType.CHEST, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

    public static final RegistryObject<ArmorItem> WOOD_LEGGINGS = ITEMS.register("wood_leggings",
            () -> new ArmorItem(ModArmorMaterials.WOOD, EquipmentSlotType.LEGS, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

    public static final RegistryObject<ArmorItem> WOOD_BOOTS = ITEMS.register("wood_boots",
            () -> new ArmorItem(ModArmorMaterials.WOOD, EquipmentSlotType.FEET, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));
}
