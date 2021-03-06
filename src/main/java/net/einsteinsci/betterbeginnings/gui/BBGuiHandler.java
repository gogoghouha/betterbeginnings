package net.einsteinsci.betterbeginnings.gui;

import net.einsteinsci.betterbeginnings.inventory.*;
import net.einsteinsci.betterbeginnings.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Some issues with this. Please note the server doesn't register items or blocks. At all.
 */
public class BBGuiHandler implements IGuiHandler
{
	public static final int KILN_ID = 0;
	public static final int SIMPLEWORKBENCH_ID = 1;
	public static final int DOUBLEWORKBENCH_ID = 2;
	public static final int BRICKOVEN_ID = 3;
	public static final int SMELTER_ID = 4;
	public static final int INFUSIONREPAIR_ID = 5;
	public static final int OBSIDIANKILN_ID = 6;
	public static final int NETHERBRICKOVEN_ID = 7;
	public static final int ENDERSMELTER_ID = 8;
	public static final int CAMPFIRE_ID = 9;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (ID == KILN_ID)
		{
			TileEntityKiln tileEntityKiln = (TileEntityKiln)world.getTileEntity(pos);
			return new ContainerKiln(player.inventory, tileEntityKiln);
		}

		if (ID == SIMPLEWORKBENCH_ID)
		{
			return new ContainerSimpleWorkbench(player.inventory, world, pos);
		}

		if (ID == DOUBLEWORKBENCH_ID)
		{
			return new ContainerDoubleWorkbench(player.inventory, world, pos);
		}

		if (ID == BRICKOVEN_ID)
		{
			TileEntityBrickOven brickOven = (TileEntityBrickOven)world.getTileEntity(pos);
			return new ContainerBrickOven(player.inventory, brickOven);
		}

		if (ID == SMELTER_ID)
		{
			TileEntitySmelter smelter = (TileEntitySmelter)world.getTileEntity(pos);
			return new ContainerSmelter(player.inventory, smelter);
		}

		if (ID == INFUSIONREPAIR_ID)
		{
			return new ContainerInfusionRepair(player.inventory, world, pos);
		}

		if (ID == OBSIDIANKILN_ID)
		{
			TileEntityObsidianKiln obsKiln = (TileEntityObsidianKiln)world.getTileEntity(pos);
			return new ContainerObsidianKiln(player.inventory, obsKiln);
		}

		if (ID == NETHERBRICKOVEN_ID)
		{
			TileEntityNetherBrickOven nbOven = (TileEntityNetherBrickOven)world.getTileEntity(pos);
			return new ContainerNetherBrickOven(player.inventory, nbOven);
		}

		if (ID == ENDERSMELTER_ID)
		{
			TileEntityEnderSmelter enderSmelter = (TileEntityEnderSmelter)world.getTileEntity(pos);
			return new ContainerEnderSmelter(player.inventory, enderSmelter);
		}

		if (ID == CAMPFIRE_ID)
		{
			TileEntityCampfire campfire = (TileEntityCampfire)world.getTileEntity(pos);
			return new ContainerCampfire(player.inventory, campfire);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (ID == KILN_ID)
		{
			TileEntityKiln tileEntityKiln = (TileEntityKiln)world.getTileEntity(pos);
			return new GuiKiln(player.inventory, tileEntityKiln);
		}

		if (ID == SIMPLEWORKBENCH_ID)
		{
			return new GuiSimpleWorkbench(player.inventory, world, pos);
		}

		if (ID == DOUBLEWORKBENCH_ID)
		{
			return new GuiDoubleWorkbench(player.inventory, world, pos);
		}

		if (ID == BRICKOVEN_ID)
		{
			TileEntityBrickOven brickOven = (TileEntityBrickOven)world.getTileEntity(pos);
			return new GuiBrickOven(player.inventory, brickOven);
		}

		if (ID == SMELTER_ID)
		{
			TileEntitySmelter smelter = (TileEntitySmelter)world.getTileEntity(pos);
			return new GuiSmelter(player.inventory, smelter);
		}

		if (ID == INFUSIONREPAIR_ID)
		{
			return new GuiInfusionRepair(player.inventory, world, pos);
		}

		if (ID == OBSIDIANKILN_ID)
		{
			TileEntityObsidianKiln obsKiln = (TileEntityObsidianKiln)world.getTileEntity(pos);
			return new GuiObsidianKiln(player.inventory, obsKiln);
		}

		if (ID == NETHERBRICKOVEN_ID)
		{
			TileEntityNetherBrickOven nbOven = (TileEntityNetherBrickOven)world.getTileEntity(pos);
			return new GuiNetherBrickOven(player.inventory, nbOven);
		}

		if (ID == ENDERSMELTER_ID)
		{
			TileEntityEnderSmelter enderSmelter = (TileEntityEnderSmelter)world.getTileEntity(pos);
			return new GuiEnderSmelter(player.inventory, enderSmelter);
		}

		if (ID == CAMPFIRE_ID)
		{
			TileEntityCampfire campfire = (TileEntityCampfire)world.getTileEntity(pos);
			return new GuiCampfire(player.inventory, campfire);
		}

		return null;
	}
}
