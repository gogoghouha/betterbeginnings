package net.einsteinsci.noobcraft.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class NoobcraftConfigGuiFactory implements IModGuiFactory
{
	
	
	@Override
	public void initialize(Minecraft minecraftInstance)
	{
		// Nothin here
	}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return NoobcraftConfigGui.class;
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}
	
}
