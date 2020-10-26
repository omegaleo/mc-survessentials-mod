package pt.omegaleo.survivalessentials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.util.enums.ModArmorMaterials;
import pt.omegaleo.survivalessentials.util.enums.ModItemTier;
import pt.omegaleo.survivalessentials.util.tools.RedstonePickaxe;

public class ModItems 
{
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SurvivalEssentialsMod.MOD_ID);

        //items
        public static final RegistryObject<Item> FLESH_CHUNK = ITEMS.register("flesh_chunk",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> WOOD_CHIPS = ITEMS.register("wood_chips",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));
        
        public static final RegistryObject<Item> WOOD_PULP = ITEMS.register("wood_pulp",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> REDSTONE_COMPOUND = ITEMS.register("redstone_compound",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> REDSTONE_INGOT = ITEMS.register("redstone_ingot",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> VINEGAR = ITEMS.register("vinegar",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> FLOUR = ITEMS.register("flour",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> SLIME = ITEMS.register("slime",
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

        // Tools
        public static final RegistryObject<PickaxeItem> REDSTONE_PICKAXE = ITEMS.register("redstone_pickaxe",
                () -> new RedstonePickaxe(ModItemTier.REDSTONE, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));


        // Tool Upgrades
        public static final RegistryObject<Item> TO_STONE = ITEMS.register("to_stone",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_IRON = ITEMS.register("to_iron",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_DIAMOND = ITEMS.register("to_diamond",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_NETHERITE = ITEMS.register("to_netherite",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
}
