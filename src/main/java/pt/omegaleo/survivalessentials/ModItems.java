package pt.omegaleo.survivalessentials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import pt.omegaleo.survivalessentials.items.BackpackItem;
import pt.omegaleo.survivalessentials.items.BlockPlacer;
import pt.omegaleo.survivalessentials.items.CustomArmorItem;
import pt.omegaleo.survivalessentials.items.CustomItem;
import pt.omegaleo.survivalessentials.items.HouseTeleporter;
import pt.omegaleo.survivalessentials.items.ItemFilterUpgrade;
import pt.omegaleo.survivalessentials.items.LootBag;
import pt.omegaleo.survivalessentials.items.ModBook;
import pt.omegaleo.survivalessentials.items.PortableChargerItem;
import pt.omegaleo.survivalessentials.util.RedstoneElytraArmor;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;
import pt.omegaleo.survivalessentials.util.enums.EnergyStorageItem;
import pt.omegaleo.survivalessentials.util.enums.ModArmorMaterials;
import pt.omegaleo.survivalessentials.util.enums.ModItemTier;
import pt.omegaleo.survivalessentials.util.enums.GlobalEnums.LootType;
import pt.omegaleo.survivalessentials.util.tools.CustomAxeItem;
import pt.omegaleo.survivalessentials.util.tools.CustomHoeItem;
import pt.omegaleo.survivalessentials.util.tools.CustomPickaxeItem;
import pt.omegaleo.survivalessentials.util.tools.CustomShovelItem;
import pt.omegaleo.survivalessentials.util.tools.CustomSwordItem;
import pt.omegaleo.survivalessentials.util.tools.DrillTool;
import pt.omegaleo.survivalessentials.util.tools.Hammer;
import pt.omegaleo.survivalessentials.util.tools.ImprovedAxeItem;
import pt.omegaleo.survivalessentials.util.tools.RedstoneHammer;
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
                
        public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", 
                () -> new BackpackItem().asItem());

        public static final RegistryObject<Item> MODBOOK = ITEMS.register("modbook", 
                () -> new ModBook().asItem());

        public static final RegistryObject<Item> HOME_TELEPORTER = ITEMS.register("home_teleporter", 
                () -> new HouseTeleporter().asItem());

        public static final RegistryObject<Item> UPGRADE_TEMPLATE = ITEMS.register("upgrade_template",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));

        public static final RegistryObject<Item> DRILL_HEAD = ITEMS.register("drill_head",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<Item> DRILL_BODY = ITEMS.register("drill_body",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<Item> FOOD_LOOTBAG = ITEMS.register("food_lootbag", 
                () -> new LootBag(LootType.FOOD).asItem());

        public static final RegistryObject<Item> CORUNDUM_GEM = ITEMS.register("corundum_gem", 
                () -> new Item(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(64)));

        public static final RegistryObject<Item> MYTHRIL_FRAGMENT = ITEMS.register("mythril_fragment", 
                () -> new Item(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(64)));

        public static final RegistryObject<Item> MYTHRIL_COMPOUND = ITEMS.register("mythril_compound", 
                () -> new Item(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(64)));

        public static final RegistryObject<Item> MYTHRIL_INGOT = ITEMS.register("mythril_ingot", 
                () -> new CustomItem(new Properties().group(SurvivalEssentialsMod.ITEMS_TAB).maxStackSize(64), true));

        /*public static final RegistryObject<Item> MOB_ESSENCE = ITEMS.register("mob_essence", 
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));*/
                   
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

        public static final RegistryObject<ArmorItem> REDSTONE_HELMET = ITEMS.register("redstone_helmet",
                () -> new ArmorItem(ModArmorMaterials.REDSTONE, EquipmentSlotType.HEAD,
                        new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> REDSTONE_CHESTPLATE = ITEMS.register("redstone_chestplate",
                () -> new ArmorItem(ModArmorMaterials.REDSTONE, EquipmentSlotType.CHEST, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> REDSTONE_ELYTRA_CHESTPLATE = ITEMS.register("redstone_elytra_chestplate",
                () -> new RedstoneElytraArmor(ModArmorMaterials.REDSTONE, EquipmentSlotType.CHEST, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));


        public static final RegistryObject<ArmorItem> REDSTONE_LEGGINGS = ITEMS.register("redstone_leggings",
                () -> new ArmorItem(ModArmorMaterials.REDSTONE, EquipmentSlotType.LEGS, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> REDSTONE_BOOTS = ITEMS.register("redstone_boots",
                () -> new ArmorItem(ModArmorMaterials.REDSTONE, EquipmentSlotType.FEET, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> CORUNDUM_BOOTS = ITEMS.register("corundum_boots",
                () -> new ArmorItem(ModArmorMaterials.CORUNDUM, EquipmentSlotType.FEET, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

                public static final RegistryObject<ArmorItem> CORUNDUM_HELMET = ITEMS.register("corundum_helmet",
                () -> new ArmorItem(ModArmorMaterials.CORUNDUM, EquipmentSlotType.HEAD,
                        new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> CORUNDUM_CHESTPLATE = ITEMS.register("corundum_chestplate",
                () -> new ArmorItem(ModArmorMaterials.CORUNDUM, EquipmentSlotType.CHEST, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> CORUNDUM_LEGGINGS = ITEMS.register("corundum_leggings",
                () -> new ArmorItem(ModArmorMaterials.CORUNDUM, EquipmentSlotType.LEGS, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB)));

        public static final RegistryObject<ArmorItem> MYTHRIL_BOOTS = ITEMS.register("mythril_boots",
                () -> new CustomArmorItem(ModArmorMaterials.MYTHRIL, EquipmentSlotType.FEET, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB), true));

        public static final RegistryObject<ArmorItem> MYTHRIL_HELMET = ITEMS.register("mythril_helmet",
                () -> new CustomArmorItem(ModArmorMaterials.MYTHRIL, EquipmentSlotType.HEAD,
                        new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB), true));

        public static final RegistryObject<ArmorItem> MYTHRIL_CHESTPLATE = ITEMS.register("mythril_chestplate",
                () -> new CustomArmorItem(ModArmorMaterials.MYTHRIL, EquipmentSlotType.CHEST, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB), true));

        public static final RegistryObject<ArmorItem> MYTHRIL_LEGGINGS = ITEMS.register("mythril_leggings",
                () -> new CustomArmorItem(ModArmorMaterials.MYTHRIL, EquipmentSlotType.LEGS, new Item.Properties().group(SurvivalEssentialsMod.ARMOR_TAB), true));

        // Tools
        public static final RegistryObject<PickaxeItem> REDSTONE_PICKAXE = ITEMS.register("redstone_pickaxe",
                () -> new RedstonePickaxe(ModItemTier.REDSTONE, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<SwordItem> REDSTONE_SWORD = ITEMS.register("redstone_sword",
                () -> new SwordItem(ModItemTier.REDSTONE, 0, 5.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<AxeItem> REDSTONE_AXE = ITEMS.register("redstone_axe",
                () -> new AxeItem(ModItemTier.REDSTONE, 0, 4.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));
        
        public static final RegistryObject<ShovelItem> REDSTONE_SHOVEL = ITEMS.register("redstone_shovel",
                () -> new ShovelItem(ModItemTier.REDSTONE, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<HoeItem> REDSTONE_HOE = ITEMS.register("redstone_hoe",
                () -> new HoeItem(ModItemTier.REDSTONE, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));
        
        public static final RegistryObject<PickaxeItem> REDSTONE_HAMMER = ITEMS.register("redstone_hammer",
                () -> new RedstoneHammer(ModItemTier.REDSTONE, 0, -2.8F));

        public static final RegistryObject<PickaxeItem> CORUNDUM_PICKAXE = ITEMS.register("corundum_pickaxe",
                () -> new PickaxeItem(ModItemTier.CORUNDUM, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<SwordItem> CORUNDUM_SWORD = ITEMS.register("corundum_sword",
                () -> new SwordItem(ModItemTier.CORUNDUM, 2, 6.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<AxeItem> CORUNDUM_AXE = ITEMS.register("corundum_axe",
                () -> new AxeItem(ModItemTier.CORUNDUM, 3, 5.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));
        
        public static final RegistryObject<ShovelItem> CORUNDUM_SHOVEL = ITEMS.register("corundum_shovel",
                () -> new ShovelItem(ModItemTier.CORUNDUM, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));

        public static final RegistryObject<HoeItem> CORUNDUM_HOE = ITEMS.register("corundum_hoe",
                () -> new HoeItem(ModItemTier.CORUNDUM, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB)));


        public static final RegistryObject<PickaxeItem> MYTHRIL_PICKAXE = ITEMS.register("mythril_pickaxe",
                () -> new CustomPickaxeItem(ModItemTier.MYTHRIL, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB), true));

        public static final RegistryObject<SwordItem> MYTHRIL_SWORD = ITEMS.register("mythril_sword",
                () -> new CustomSwordItem(ModItemTier.MYTHRIL, 2, 6.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB), true));

        public static final RegistryObject<AxeItem> MYTHRIL_AXE = ITEMS.register("mythril_axe",
                () -> new CustomAxeItem(ModItemTier.MYTHRIL, 3, 5.0F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB), true));
        
        public static final RegistryObject<ShovelItem> MYTHRIL_SHOVEL = ITEMS.register("mythril_shovel",
                () -> new CustomShovelItem(ModItemTier.MYTHRIL, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB), true));

        public static final RegistryObject<HoeItem> MYTHRIL_HOE = ITEMS.register("mythril_hoe",
                () -> new CustomHoeItem(ModItemTier.MYTHRIL, 0, -2.8F, new Item.Properties().group(SurvivalEssentialsMod.TOOLS_TAB), true));

        //Hammers
        public static final RegistryObject<PickaxeItem> STONE_HAMMER = ITEMS.register("stone_hammer",
                () -> new Hammer(ItemTier.STONE, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> IRON_HAMMER = ITEMS.register("iron_hammer",
                () -> new Hammer(ItemTier.IRON, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> GOLD_HAMMER = ITEMS.register("gold_hammer",
                () -> new Hammer(ItemTier.GOLD, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
                () -> new Hammer(ItemTier.DIAMOND, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
                () -> new Hammer(ItemTier.NETHERITE, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> CORUNDUM_HAMMER = ITEMS.register("corundum_hammer",
                () -> new Hammer(ModItemTier.CORUNDUM, 0, -2.8F, false));

        public static final RegistryObject<PickaxeItem> MYTHRIL_HAMMER = ITEMS.register("mythril_hammer",
                () -> new Hammer(ModItemTier.MYTHRIL, 0, -2.8F, true));


        //Improved axes
        public static final RegistryObject<AxeItem> STONE_HATCHET = ITEMS.register("stone_hatchet",
                () -> new ImprovedAxeItem(ItemTier.STONE, 0, -2.8F, false));

        public static final RegistryObject<AxeItem> IRON_HATCHET = ITEMS.register("iron_hatchet",
                () -> new ImprovedAxeItem(ItemTier.IRON, 0, -2.8F, false));
        
        public static final RegistryObject<AxeItem> GOLD_HATCHET = ITEMS.register("gold_hatchet",
                () -> new ImprovedAxeItem(ItemTier.GOLD, 0, -2.8F, false));
        
        public static final RegistryObject<AxeItem> REDSTONE_HATCHET = ITEMS.register("redstone_hatchet",
                () -> new ImprovedAxeItem(ModItemTier.REDSTONE, 0, -2.8F, false));

        public static final RegistryObject<AxeItem> DIAMOND_HATCHET = ITEMS.register("diamond_hatchet",
                () -> new ImprovedAxeItem(ItemTier.DIAMOND, 0, -2.8F, false));

        public static final RegistryObject<AxeItem> NETHERITE_HATCHET = ITEMS.register("netherite_hatchet",
                () -> new ImprovedAxeItem(ItemTier.NETHERITE, 0, -2.8F, false));

        public static final RegistryObject<AxeItem> CORUNDUM_HATCHET = ITEMS.register("corundum_hatchet",
                () -> new ImprovedAxeItem(ModItemTier.CORUNDUM, 0, -2.8F, false));

        public static final RegistryObject<AxeItem> MYTHRIL_HATCHET = ITEMS.register("mythril_hatchet",
                () -> new ImprovedAxeItem(ModItemTier.MYTHRIL, 0, -2.8F, true));

        public static final RegistryObject<PickaxeItem> DRILL = ITEMS.register("drill",
        () -> new DrillTool(ItemTier.IRON, 0, -2.8F, 10));

        public static final RegistryObject<Item> BLOCK_PLACER = ITEMS.register("block_placer", 
        () -> new BlockPlacer());

        // Tool Upgrades
        public static final RegistryObject<Item> TO_STONE = ITEMS.register("to_stone",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_IRON = ITEMS.register("to_iron",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_DIAMOND = ITEMS.register("to_diamond",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));
        public static final RegistryObject<Item> TO_NETHERITE = ITEMS.register("to_netherite",
                () -> new Item(new Item.Properties().group(SurvivalEssentialsMod.UPGRADES_TAB)));

        // Dril Upgrades
        public static final RegistryObject<Item> FIVE_BLOCKS = ITEMS.register("five_blocks",
                () -> new DrillUpgrade());
        public static final RegistryObject<Item> SEVEN_BLOCKS = ITEMS.register("seven_blocks",
                () -> new DrillUpgrade());
        public static final RegistryObject<Item> ITEM_FILTER = ITEMS.register("item_filter",
                () -> new ItemFilterUpgrade());
        public static final RegistryObject<Item> MAGNET = ITEMS.register("magnet",
                () -> new DrillUpgrade());
        public static final RegistryObject<Item> SMELT_UPGRADE = ITEMS.register("smelt_upgrade",
                () -> new DrillUpgrade());
        public static final RegistryObject<Item> REPAIR = ITEMS.register("repair",
                () -> new DrillUpgrade());
  
        public static final RegistryObject<Item> REDSTONE_TIER = ITEMS.register("redstone_tier",
                () -> new DrillUpgrade());

        public static final RegistryObject<Item> DIAMOND_TIER = ITEMS.register("diamond_tier",
                () -> new DrillUpgrade());

        public static final RegistryObject<Item> NETHERITE_TIER = ITEMS.register("netherite_tier",
                () -> new DrillUpgrade());

        public static final RegistryObject<Item> MYTHRIL_TIER = ITEMS.register("mythril_tier",
                () -> new DrillUpgrade());

        // Energy related
        public static final RegistryObject<Item> BATTERY = ITEMS.register("battery",
                () -> new EnergyStorageItem(1000));
        
        public static final RegistryObject<Item> PORTABLE_CHARGER = ITEMS.register("pcharger", 
                () -> new PortableChargerItem(10000));
        
        public static final RegistryObject<Item> FULL_PORTABLE_CHARGER = ITEMS.register("fullpcharger", 
                () -> new PortableChargerItem(10000,true));

        // Blocks
        public static final RegistryObject<Item> enchantment_extractor = ITEMS.register("enchantment_extractor",
                () -> new BlockItem(ModBlocks.enchantment_extractor.get(), new Item.Properties().group(SurvivalEssentialsMod.MACHINES_TAB)));

        public static final RegistryObject<Item> CORUNDUM_ORE = ITEMS.register("corundum_ore",
                () -> new BlockItem(ModBlocks.CORUNDUM_ORE.get(), new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));

        public static final RegistryObject<Item> MYTHRIL_ORE = ITEMS.register("mythril_ore",
                () -> new BlockItem(ModBlocks.MYTHRIL_ORE.get(), new Item.Properties().group(SurvivalEssentialsMod.ITEMS_TAB)));
}
