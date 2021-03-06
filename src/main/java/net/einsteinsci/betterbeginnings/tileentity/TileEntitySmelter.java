package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockSmelter;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySmelter extends TileEntity implements ISidedInventory, IUpdatePlayerListBox, IInteractionObject
{
	public static final int smeltTime = 160;
	public static final int INPUT = 0;
	public static final int FUEL = 1;
	public static final int OUTPUT = 2;
	public static final int GRAVEL = 3;
	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	public int smelterBurnTime;
	public int currentItemBurnLength;
	public int smelterCookTime;
	private ItemStack[] smelterStacks = new ItemStack[4];
	private String smelterName;

	public TileEntitySmelter()
	{
		super();
	}

	public void setBlockName(String string)
	{
		smelterName = string;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int progress)
	{
		return smelterCookTime * progress / smeltTime;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int time)
	{
		if (currentItemBurnLength == 0)
		{
			currentItemBurnLength = smeltTime;
		}

		return smelterBurnTime * time / currentItemBurnLength;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		// ItemStacks
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		smelterStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
			byte slot = itemTag.getByte("Slot");

			if (slot >= 0 && slot < smelterStacks.length)
			{
				smelterStacks[slot] = ItemStack.loadItemStackFromNBT(itemTag);
			}
		}

		// Burn Time & Cook Time
		smelterBurnTime = tagCompound.getShort("BurnTime");
		smelterCookTime = tagCompound.getShort("CookTime");
		currentItemBurnLength = getItemBurnTime(smelterStacks[FUEL]);

		if (tagCompound.hasKey("CustomName", 8))
		{
			smelterName = tagCompound.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setShort("BurnTime", (short)smelterBurnTime);
		tagCompound.setShort("CookTime", (short)smelterCookTime);
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < smelterStacks.length; ++i)
		{
			if (smelterStacks[i] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				smelterStacks[i].writeToNBT(itemTag);
				itemTag.setByte("Slot", (byte)i);
				tagList.appendTag(itemTag);
			}
		}

		tagCompound.setTag("Items", tagList);
		if (hasCustomName())
		{
			tagCompound.setString("CustomName", smelterName);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return smelterStacks.length;
	}

	public static int getItemBurnTime(ItemStack itemStack)
	{
		if (itemStack == null)
		{
			return 0;
		}
		else
		{
			Item item = itemStack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
			{
				Block block = Block.getBlockFromItem(item);

				// A VERY HIGH AMOUNT OF POWER!!!
				if (block == Blocks.bedrock)
				{
					return Short.MAX_VALUE;
				}
			}

			// Charcoal
			if (item == Items.coal && itemStack.getItemDamage() == 1)
			{
				return 1600;
			}

			// Blaze Rods and Lava are valid fuel sources for a Smelter.
			if (item == Items.blaze_rod)
			{
				return 600;
			}
			if (item == Items.lava_bucket)
			{
				return 7200;
			}

			return GameRegistry.getFuelValue(itemStack);
		}
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return smelterStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (smelterStacks[slot] != null)
		{
			ItemStack stack;
			if (smelterStacks[slot].stackSize <= amount)
			{
				stack = smelterStacks[slot];
				smelterStacks[slot] = null;
				return stack;
			}
			else
			{
				stack = smelterStacks[slot].splitStack(amount);

				if (smelterStacks[slot].stackSize == 0)
				{
					smelterStacks[slot] = null;
				}

				return stack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if (smelterStacks[slot] != null)
		{
			ItemStack stack = smelterStacks[slot];
			smelterStacks[slot] = null;
			return stack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		smelterStacks[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (worldObj.getTileEntity(pos) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq((double)pos.getX() + 0.5d, (double)pos.getY() + 0.5d,
			                            (double)pos.getZ() + 0.5d) <= 64.0d;
		}
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return slot == 2 ? false : slot == 1 ? isItemFuel(stack) : true;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? smelterName : "container.smelter";
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public boolean hasCustomName()
	{
		return smelterName != null && smelterName.length() > 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(getName());
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < smelterStacks.length; i++)
		{
			smelterStacks[i] = null;
		}
	}

	@Override
	public void update()
	{
		boolean flag = smelterBurnTime > 0;
		boolean flag1 = false;

		if (smelterBurnTime > 0)
		{
			--smelterBurnTime;
		}

		if (!worldObj.isRemote)
		{
			if (smelterBurnTime == 0 && canSmelt())
			{
				currentItemBurnLength = smelterBurnTime = getItemBurnTime(smelterStacks[FUEL]);

				if (smelterBurnTime > 0)
				{
					flag1 = true;
					if (smelterStacks[FUEL] != null)
					{
						--smelterStacks[FUEL].stackSize;

						if (smelterStacks[FUEL].stackSize == 0)
						{
							smelterStacks[FUEL] = smelterStacks[FUEL].getItem().getContainerItem(smelterStacks[FUEL]);
						}
					}
				}
			}

			if (isBurning() && canSmelt())
			{
				++smelterCookTime;
				if (smelterCookTime == smeltTime)
				{
					smelterCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			}
			else
			{
				smelterCookTime = 0;
			}
		}

		if (flag != smelterBurnTime > 0)
		{
			flag1 = true;
			BlockSmelter.updateBlockState(smelterBurnTime > 0, worldObj, pos);
		}

		if (flag1)
		{
			markDirty();
		}
	}

	private boolean canSmelt()
	{
		if (smelterStacks[INPUT] == null || smelterStacks[GRAVEL] == null)
		{
			return false;
		}
		else
		{
			ItemStack stack = SmelterRecipeHandler.smelting().getSmeltingResult(smelterStacks[INPUT]);
			int gravelNeeded = SmelterRecipeHandler.smelting().getGravelCount(smelterStacks[INPUT]);

			if (stack == null)
			{
				return false;
			}

			if (gravelNeeded > smelterStacks[GRAVEL].stackSize)
			{
				return false;
			}

			if (smelterStacks[OUTPUT] == null)
			{
				return true;
			}
			if (!smelterStacks[OUTPUT].isItemEqual(stack))
			{
				return false;
			}

			int result = smelterStacks[OUTPUT].stackSize + stack.stackSize;
			return result <= getInventoryStackLimit() && result <= smelterStacks[OUTPUT].getMaxStackSize();
		}
	}

	public boolean isBurning()
	{
		return smelterBurnTime > 0;
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack itemStack = SmelterRecipeHandler.smelting().getSmeltingResult(smelterStacks[INPUT]);

			if (smelterStacks[OUTPUT] == null)
			{
				smelterStacks[OUTPUT] = itemStack.copy();
			}
			else if (smelterStacks[OUTPUT].getItem() == itemStack.getItem())
			{
				smelterStacks[OUTPUT].stackSize += itemStack.stackSize;
			}

			int gravelUsed = SmelterRecipeHandler.smelting().getGravelCount(smelterStacks[INPUT]);

			--smelterStacks[INPUT].stackSize;

			if (smelterStacks[INPUT].stackSize <= 0)
			{
				smelterStacks[INPUT] = null;
			}
			smelterStacks[GRAVEL].stackSize -= gravelUsed;

			if (smelterStacks[GRAVEL].stackSize <= 0)
			{
				smelterStacks[GRAVEL] = null;
			}
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slotsBottom : side == EnumFacing.UP ? slotsTop : slotsSides;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack stack, EnumFacing par3)
	{
		return isItemValidForSlot(par1, stack);
	}

	public static boolean isItemFuel(ItemStack itemStack)
	{
		return getItemBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack stack, EnumFacing par3)
	{
		return par3 != EnumFacing.DOWN || par1 != 1 || stack.getItem() == Items.bucket;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}

	@Override
	public String getGuiID()
	{
		return null;
	}






}
