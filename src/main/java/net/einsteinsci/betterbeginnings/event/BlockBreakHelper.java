package net.einsteinsci.betterbeginnings.event;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.items.ItemKnife;
import net.einsteinsci.betterbeginnings.util.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.logging.log4j.Level;
import java.util.HashMap;
import java.util.Map;

public class BlockBreakHelper
{
	public static final Map<EntityPlayer, BlockPos> brokenOnce = new HashMap<>();

	// returns whether to cancel drops or not
	public static void handleBlockBreaking(BlockEvent.BreakEvent e)
	{
		Block block = e.state.getBlock();
		EntityPlayer player = e.getPlayer();
		ItemStack heldItemStack = player.getHeldItem();

		if (player.capabilities.isCreativeMode)
		{
			return;
		}

		int neededHarvestLevel = block.getHarvestLevel(e.state);
		String neededToolClass = block.getHarvestTool(e.state);
		int usedHarvestLevel = 0;
		String usedToolClass = null;
		String stackName = heldItemStack != null ? heldItemStack.getDisplayName() : "NULL";

		if (heldItemStack != null)
		{
			for (String toolClass : heldItemStack.getItem().getToolClasses(heldItemStack))
			{
				int hl = heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass);
				if (hl >= usedHarvestLevel)
				{
					usedHarvestLevel = hl;
					usedToolClass = toolClass;
				}
			}
		}

		boolean cancel = false;

		if (heldItemStack != null)
		{
			if (heldItemStack.getItem() instanceof ItemKnife &&
					ItemKnife.getBreakable().contains(block))
			{
				return; // allows knife to do stuff.
			}
		}

		if (neededToolClass == null ||
				neededToolClass.equalsIgnoreCase("shovel") ||
				neededToolClass.equalsIgnoreCase("null"))
		{
			return;
		}

		if (neededToolClass.equalsIgnoreCase(usedToolClass))
		{
			if (usedHarvestLevel < neededHarvestLevel)
			{
				cancel = true;
			}
		}
		else
		{
			if (usedToolClass == null || usedToolClass.equalsIgnoreCase("null"))
			{
				if (e.world.getDifficulty() != EnumDifficulty.PEACEFUL)
				{
					player.attackEntityFrom(new DamageSourceFace(block), 6.0f);
				}

				if (!brokenOnce.containsKey(player) || brokenOnce.get(player) == null ||
						!brokenOnce.get(player).equals(e.pos))
				{
					ChatUtil.sendModChatToPlayer(player, "Almost. Once more should do it.");
					brokenOnce.put(player, e.pos);

					// skip other notification
					e.setCanceled(true);
					return;
				}
				else
				{
					ChatUtil.sendModChatToPlayer(player, "Ouch! But at least it worked.");
					brokenOnce.put(player, null);
				}

				ModMain.Log(Level.INFO, "Block break failed for " + stackName + " on " + block.getUnlocalizedName());
				ModMain.Log(Level.INFO, "  Required tool class: " + neededToolClass + ", supplied: " + usedToolClass);
				ModMain.Log(Level.INFO, "  Minimum harvest level: " + neededHarvestLevel + ", supplied: " +
						usedHarvestLevel);
			}
			else
			{
				cancel = true;
			}
		}

		if (cancel)
		{
			ModMain.Log(Level.INFO, "Block break failed for " + stackName + " on " + block.getUnlocalizedName());
			ModMain.Log(Level.INFO, "  Required tool class: " + neededToolClass + ", supplied: " + usedToolClass);
			ModMain.Log(Level.INFO, "  Minimum harvest level: " + neededHarvestLevel + ", supplied: " +
					usedHarvestLevel);

			ChatUtil.sendModChatToPlayer(player, "Wrong tool!");
			ChatUtil.sendModChatToPlayer(player,
			                             "Requires " + getToolLevelName(neededHarvestLevel) + " " + neededToolClass);

			e.setCanceled(true);
		}
	}

	public static String getToolLevelName(int level)
	{
		switch (level)
		{
			case 0:
				return "WOOD";
			case 1:
				return "STONE";
			case 2:
				return "IRON";
			case 3:
				return "DIAMOND";
			default:
				return "Level " + level;
		}
	}
}
