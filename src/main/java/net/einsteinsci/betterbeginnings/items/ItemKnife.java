package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.HashSet;
import java.util.Set;

public abstract class ItemKnife extends ItemTool implements IBBName
{
	public static final float DAMAGE = 3.0f;

	public ItemKnife(ToolMaterial material)
	{
		super(DAMAGE, material, getBreakable());
	}

	public static Set getBreakable()
	{
		Set<Block> s = new HashSet<>();

		// s.add(Blocks.log);
		// s.add(Blocks.log2);
		// s.add(Blocks.planks);
		s.add(Blocks.pumpkin);
		s.add(Blocks.lit_pumpkin);
		s.add(Blocks.melon_block);
		s.add(Blocks.clay);
		s.add(Blocks.grass);
		s.add(Blocks.mycelium);
		s.add(Blocks.leaves);
		s.add(Blocks.leaves2);
		s.add(Blocks.brown_mushroom_block);
		s.add(Blocks.red_mushroom_block);
		s.add(Blocks.glass);
		s.add(Blocks.glass_pane);
		s.add(Blocks.soul_sand);
		s.add(Blocks.stained_glass);
		s.add(Blocks.stained_glass_pane);
		s.add(Blocks.cactus);

		return s;
	}

	// Determines if a block broken with the tool will drop its drops.
	//@Override
	//public boolean func_150897_b(Block block)
	//{
	//	if (block.getMaterial() == Material.rock && toolMaterial.getHarvestLevel() >= 0)
	//	{
	//		return true;
	//	}
	//	if (block.getMaterial() == Material.wood && toolMaterial.getHarvestLevel() >= 0)
	//	{
	//		return true;
	//	}
	//
	//	return false;
	//}

	//@Override
	//public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
	//{
	//	return false;
	//}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		return toolMaterial.getHarvestLevel();
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		Set<String> res = new HashSet<>();

		res.add("knife");

		return res;
	}

	// ...which also requires this...
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		itemStack.setItemDamage(itemStack.getItemDamage() + 1);

		return itemStack;
	}

	// Allows durability-based crafting.
	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}

	@Override
	public abstract String getName();
}
