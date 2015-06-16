
package net.codekrafter.plugins.magic.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Spell
{

	public int onRightClick(Player p);

	public int onLeftClick(Player p);

	public String getName();

	public SpellType getType();

	public ItemStack getIcon();

	public void onShift(Player p);

	public void onSpellToggle(boolean b, Player p);
}
