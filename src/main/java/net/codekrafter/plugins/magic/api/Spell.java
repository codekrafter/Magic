
package net.codekrafter.plugins.magic.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Spell
{

	public abstract int onRightClick(Player p);

	public abstract int onLeftClick(Player p);

	public abstract String getName();

	public abstract SpellType getType();

	public abstract ItemStack getIcon();

	//public abstract void onShift(Player p);

	public abstract void onSpellToggle(boolean b, Player p);
	
	public abstract void onSpellLoad();
}
