package net.einsteinsci.noobcraft.inventory;

import net.einsteinsci.noobcraft.tileentity.TileEntityBrickOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBrickOven extends Container
{
	public static final int FUEL = 0;
	public static final int OUTPUT = 1;
	public static final int INPUT = 3;
	
	// private InventoryBrickOvenMatrix craftMatrix = new
	// InventoryBrickOvenMatrix(this, 3, 3);
	private TileEntityBrickOven tileBrickOven;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	
	public ContainerBrickOven(InventoryPlayer playerInv, TileEntityBrickOven tileEntityBrickOven)
	{
		tileBrickOven = tileEntityBrickOven;
		addSlotToContainer(new Slot(tileEntityBrickOven, FUEL, 92, 58));
		addSlotToContainer(new SlotFurnace(playerInv.player, tileEntityBrickOven, OUTPUT, 124, 21));
		// addSlotToContainer(new Slot(tileEntityBrickOven, INPUT, 30, 17));
		
		int i;
		
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; j++)
			{
				addSlotToContainer(new Slot(tileEntityBrickOven, j + i * 3 + TileEntityBrickOven.INPUTSTART,
					30 + j * 18, 17 + i * 18));
			}
		}
		
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (i = 0; i < 9; ++i)
		{
			addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting craft)
	{
		super.addCraftingToCrafters(craft);
		
		craft.sendProgressBarUpdate(this, 0, tileBrickOven.ovenCookTime);
		craft.sendProgressBarUpdate(this, 1, tileBrickOven.ovenBurnTime);
		craft.sendProgressBarUpdate(this, 2, tileBrickOven.currentItemBurnLength);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (int i = 0; i < crafters.size(); ++i)
		{
			ICrafting craft = (ICrafting)crafters.get(i);
			
			if (lastCookTime != tileBrickOven.ovenCookTime)
			{
				craft.sendProgressBarUpdate(this, 0, tileBrickOven.ovenCookTime);
			}
			if (lastBurnTime != tileBrickOven.ovenBurnTime)
			{
				craft.sendProgressBarUpdate(this, 1, tileBrickOven.ovenBurnTime);
			}
			if (lastItemBurnTime != tileBrickOven.currentItemBurnLength)
			{
				craft.sendProgressBarUpdate(this, 2, tileBrickOven.currentItemBurnLength);
			}
		}
		
		lastBurnTime = tileBrickOven.ovenBurnTime;
		lastCookTime = tileBrickOven.ovenCookTime;
		lastItemBurnTime = tileBrickOven.currentItemBurnLength;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			tileBrickOven.ovenCookTime = par2;
		}
		if (par1 == 1)
		{
			tileBrickOven.ovenBurnTime = par2;
		}
		if (par1 == 2)
		{
			tileBrickOven.currentItemBurnLength = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileBrickOven.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotId);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (slotId == 0)
			{
				if (!mergeItemStack(itemstack1, 10, 46, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotId >= 10 && slotId < 37)
			{
				if (!mergeItemStack(itemstack1, 37, 46, false))
				{
					return null;
				}
			}
			else if (slotId >= 37 && slotId < 46)
			{
				if (!mergeItemStack(itemstack1, 10, 37, false))
				{
					return null;
				}
			}
			else if (!mergeItemStack(itemstack1, 10, 46, false))
			{
				return null;
			}
			
			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, itemstack1);
		}
		
		return itemstack;
	}
	
	/*
	 * @Override public ItemStack transferStackInSlot(EntityPlayer player, int par2) { ItemStack itemstack = null; Slot
	 * slot = (Slot) inventorySlots.get(par2);
	 * 
	 * if (slot != null && slot.getHasStack()) { ItemStack itemstack1 = slot.getStack(); itemstack = itemstack1.copy();
	 * 
	 * if (par2 == 2) { if (!mergeItemStack(itemstack1, 3, 39, true)) { return null; } slot.onSlotChange(itemstack1,
	 * itemstack); } else if (par2 != 1 && par2 != 0) { if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) !=
	 * null) { if (!mergeItemStack(itemstack1, 0, 1, false)) { return null; } } else if
	 * (TileEntityBrickOven.isItemFuel(itemstack1)) { if (!mergeItemStack(itemstack1, 1, 2, false)) { return null; } }
	 * else if (par2 >= 3 && par2 < 30) { if (!mergeItemStack(itemstack1, 30, 39, false)) { return null; } } else if
	 * (par2 >= 30 && par2 < 39 && !mergeItemStack(itemstack1, 3, 30, false)) { return null; } } else if
	 * (!mergeItemStack(itemstack1, 3, 39, false)) { return null; } if (itemstack1.stackSize == 0) {
	 * slot.putStack((ItemStack) null); } else { slot.onSlotChanged(); } if (itemstack1.stackSize ==
	 * itemstack.stackSize) { return null; } slot.onPickupFromSlot(player, itemstack1); } return itemstack; }
	 */
}
