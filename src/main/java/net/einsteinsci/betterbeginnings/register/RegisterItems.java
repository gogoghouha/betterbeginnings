package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.items.*;
import net.einsteinsci.betterbeginnings.items.ItemCloth;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RegisterItems
{
	public static final ToolMaterial NOOBWOOD = EnumHelper.addToolMaterial("NOOBWOOD", 0, 60, 2.0f, -4, 35);
	public static final ItemNoobWoodSword noobWoodSword = new ItemNoobWoodSword(NOOBWOOD);
	public static final ItemArmor.ArmorMaterial CLOTH = EnumHelper.addArmorMaterial(
			"CLOTH", "CLOTH", 5, new int[] {1, 3, 2, 1}, 25);
	public static final ItemClothArmor clothHat = new ItemClothArmor(CLOTH, 0, "clothHat");
	public static final ItemClothArmor clothShirt = new ItemClothArmor(CLOTH, 1, "clothShirt");
	public static final ItemClothArmor clothPants = new ItemClothArmor(CLOTH, 2, "clothPants");
	public static final ItemClothArmor clothBoots = new ItemClothArmor(CLOTH, 3, "clothBoots");
	public static final ItemKnife flintKnife = new ItemKnifeFlint();
	public static final ItemKnife boneKnife = new ItemKnifeBone();
	public static final ItemKnife ironKnife = new ItemKnifeIron();
	public static final ItemKnife diamondKnife = new ItemKnifeDiamond();
	public static final ItemKnife goldKnife = new ItemKnifeGold();
	public static final ItemFlintHatchet flintHatchet = new ItemFlintHatchet();
	public static final ItemBonePickaxe bonePickaxe = new ItemBonePickaxe();
	public static final ItemBoneShard boneShard = new ItemBoneShard();
	public static final ItemTestItem testItem = new ItemTestItem();
	public static final ItemSilk silk = new ItemSilk();
	public static final ItemThread thread = new ItemThread();
	public static final ItemCloth cloth = new ItemCloth();
	public static final ItemLeatherStrip leatherStrip = new ItemLeatherStrip();
	public static final ItemIronNugget ironNugget = new ItemIronNugget();
	public static final ItemCharredMeat charredMeat = new ItemCharredMeat();
	public static final ItemFireBow fireBow = new ItemFireBow();
	public static final ItemMarshmallow marshmallow = new ItemMarshmallow();
	public static final ItemMarshmallowCooked marshmallowCooked = new ItemMarshmallowCooked();
	public static final ItemRoastingStick roastingStick = new ItemRoastingStick();
	public static final ItemRoastingStickMallow roastingStickRawMallow = new ItemRoastingStickMallow(false);
	public static final ItemRoastingStickMallow roastingStickCookedMallow = new ItemRoastingStickMallow(true);
	public static final ItemTwine twine = new ItemTwine();
	public static final ItemRockHammer rockHammer = new ItemRockHammer(ToolMaterial.IRON);
	public static final ItemPan pan = new ItemPan();

	public static final List<Item> allItems = new ArrayList<>();

	public static void register()
	{
		RegisterHelper.registerItem(flintKnife);
		RegisterHelper.registerItem(boneKnife);
		RegisterHelper.registerItem(ironKnife);
		RegisterHelper.registerItem(goldKnife);
		RegisterHelper.registerItem(diamondKnife);

		RegisterHelper.registerItem(flintHatchet);
		RegisterHelper.registerItem(bonePickaxe);

		RegisterHelper.registerItem(boneShard);
		RegisterHelper.registerItem(testItem);
		RegisterHelper.registerItem(silk);
		RegisterHelper.registerItem(thread);
		RegisterHelper.registerItem(cloth);
		RegisterHelper.registerItem(twine);
		RegisterHelper.registerItem(leatherStrip);
		RegisterHelper.registerItem(ironNugget);
		RegisterHelper.registerItem(charredMeat);
		RegisterHelper.registerItem(fireBow);
		RegisterHelper.registerItem(rockHammer);
		RegisterHelper.registerItem(pan);

		RegisterHelper.registerItem(marshmallow);
		RegisterHelper.registerItem(roastingStick);
		RegisterHelper.registerItem(marshmallowCooked);
		RegisterHelper.registerItem(roastingStickCookedMallow);
		RegisterHelper.registerItem(roastingStickRawMallow);

		RegisterHelper.registerItem(clothHat);
		RegisterHelper.registerItem(clothShirt);
		RegisterHelper.registerItem(clothPants);
		RegisterHelper.registerItem(clothBoots);

		RegisterHelper.registerItem(noobWoodSword);

		oreDictRegistry();
		loadAllItems();
	}

	public static void oreDictRegistry()
	{
		OreDictionary.registerOre("nuggetIron", ironNugget);
		OreDictionary.registerOre("itemKnife", new ItemStack(flintKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemKnife", new ItemStack(boneKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemKnife", new ItemStack(ironKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemKnife", new ItemStack(goldKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("itemKnife", new ItemStack(diamondKnife, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("itemString", new ItemStack(Items.string));
		OreDictionary.registerOre("itemString", new ItemStack(thread));
		OreDictionary.registerOre("itemString", new ItemStack(twine));
	}

	private static void loadAllItems()
	{
		List<Field> fields = new ArrayList<>();
		Field[] allFields = RegisterItems.class.getFields();
		for (Field f : allFields)
		{
			if (Item.class.isAssignableFrom(f.getType()) &&
					IBBName.class.isAssignableFrom(f.getType()))
			{
				fields.add(f);
			}
		}

		for (Field f : fields)
		{
			try
			{
				allItems.add((Item)f.get(null));
			}
			catch (Exception e)
			{
				ModMain.Log(Level.ERROR, "Field " + f.getName() + " does not inherit from Item.");
			}
		}
	}

	public static void tweakVanilla()
	{
		((ItemFood)GameRegistry.findItem("minecraft", "beef")).setPotionEffect(17, 20, 0, 20);
		((ItemFood)GameRegistry.findItem("minecraft", "porkchop")).setPotionEffect(17, 25, 0, 25);
		((ItemFood)GameRegistry.findItem("minecraft", "fish")).setPotionEffect(17, 30, 1, 60); // Both fish types here
		((ItemFood)GameRegistry.findItem("minecraft", "mutton")).setPotionEffect(17, 20, 0, 25);
		((ItemFood)GameRegistry.findItem("minecraft", "rabbit")).setPotionEffect(17, 25, 0, 30);

		// Let's face it, the vanilla stack sizes for these suck.
		GameRegistry.findItem("minecraft", "minecart").setMaxStackSize(16);
		// Strangely enough the oak one doesn't change.
		GameRegistry.findItem("minecraft", "wooden_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "spruce_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "birch_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "acacia_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "dark_oak_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "iron_door").setMaxStackSize(16);
		GameRegistry.findItem("minecraft", "potion").setMaxStackSize(16);
	}
}
