package net.codekrafter.plugins.magic.api;

import org.bukkit.inventory.ItemStack;


public interface Spell
{
	public void onRightClick();
	public void onLeftClick();
	public String getName();
	public SpellType getType();
	public ItemStack getIcon();
	public void onShiftClick();
	public void onSpellToggleClick(boolean b);
}