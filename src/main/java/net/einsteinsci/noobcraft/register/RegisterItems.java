package net.einsteinsci.noobcraft.register;

import cpw.mods.fml.common.registry.GameRegistry;
import net.einsteinsci.noobcraft.items.*;
import net.einsteinsci.noobcraft.items.ItemCloth;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterItems
{
	public static final ToolMaterial noobWood = EnumHelper.addToolMaterial("noobwood", 0, 60, 2.0f, -4, 35);
	public static final ItemArmor.ArmorMaterial armorMaterialCloth = EnumHelper.addArmorMaterial("CLOTH_REALLY",
																					  5,
																					  new int[] {1, 3, 2, 1},
																					  25);
	public static final Item noobWoodSword = new NoobWoodSword(noobWood);
	public static final Item flintKnife = new ItemKnifeFlint();
	public static final Item boneKnife = new ItemKnifeBone();
	public static final Item ironKnife = new ItemKnifeIron();
	public static final Item diamondKnife = new ItemKnifeDiamond();
	public static final Item flintHatchet = new ItemFlintHatchet();
	public static final Item bonePickaxe = new ItemBonePickaxe();
	public static final Item boneShard = new ItemBoneShard();
	public static final Item testItem = new ItemTestItem();
	public static final Item silk = new ItemSilk();
	public static final Item thread = new ItemThread();
	public static final Item cloth = new ItemCloth();
	public static final Item leatherStrip = new ItemLeatherStrip();
	public static final Item ironNugget = new ItemIronNugget();
	public static final Item charredMeat = new ItemCharredMeat();
	public static final Item fireBow = new ItemFireBow();
	public static final Item clothHat = new ItemClothArmor(armorMaterialCloth, 0, "clothHat");
	public static final Item clothShirt = new ItemClothArmor(armorMaterialCloth, 1, "clothShirt");
	public static final Item clothPants = new ItemClothArmor(armorMaterialCloth, 2, "clothPants");
	public static final Item clothBoots = new ItemClothArmor(armorMaterialCloth, 3, "clothBoots");
	
	public static void register()
	{
		RegisterHelper.registerItem(flintKnife);
		RegisterHelper.registerItem(boneKnife);
		RegisterHelper.registerItem(ironKnife);
		RegisterHelper.registerItem(diamondKnife);
		
		RegisterHelper.registerItem(flintHatchet);
		RegisterHelper.registerItem(bonePickaxe);
		
		RegisterHelper.registerItem(boneShard);
		RegisterHelper.registerItem(testItem);
		RegisterHelper.registerItem(silk);
		RegisterHelper.registerItem(thread);
		RegisterHelper.registerItem(cloth);
		RegisterHelper.registerItem(leatherStrip);
		RegisterHelper.registerItem(ironNugget);
		RegisterHelper.registerItem(charredMeat);
		RegisterHelper.registerItem(fireBow);

		RegisterHelper.registerItem(clothHat);
		RegisterHelper.registerItem(clothShirt);
		RegisterHelper.registerItem(clothPants);
		RegisterHelper.registerItem(clothBoots);
		
		RegisterHelper.registerItem(noobWoodSword);
		
		oreDictRegistry();
	}

	public static void oreDictRegistry()
	{
		OreDictionary.registerOre("nuggetIron", ironNugget);
	}

	public static void tweakVanilla()
	{
		((ItemFood)GameRegistry.findItem("minecraft", "beef")).setPotionEffect(17, 20, 0, 20);
		((ItemFood)GameRegistry.findItem("minecraft", "porkchop")).setPotionEffect(17, 25, 0, 25);
		((ItemFood)GameRegistry.findItem("minecraft", "fish")).setPotionEffect(17, 30, 1, 60); // Both fish types here
	}
}
