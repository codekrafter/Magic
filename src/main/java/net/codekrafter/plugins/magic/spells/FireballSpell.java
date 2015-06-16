
package net.codekrafter.plugins.magic.spells;

import net.codekrafter.plugins.magic.api.Spell;
import net.codekrafter.plugins.magic.api.SpellType;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FireballSpell implements Spell
{

	public int onRightClick(Player p)
	{
		if (p != null)
		{
			Fireball fb = p.launchProjectile(Fireball.class);
			fb.setIsIncendiary(false);
			
		}
		return 20;

	}

	public int onLeftClick(Player p)
	{
		if (p != null)
		{
			p.setVelocity(p.getLocation().getDirection().multiply(2.5D));
			p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 2002);
			p.playSound(p.getLocation(), Sound.BLAZE_BREATH, 10, 10);
		}
		return 5;
	}

	public String getName()
	{
		return "Fireball Spell";
	}

	public SpellType getType()
	{
		return SpellType.COMBAT;
	}

	public ItemStack getIcon()
	{
		ItemStack is = new ItemStack(Material.FIREBALL);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("" + ChatColor.DARK_RED + ChatColor.BOLD
				+ "Fireball Spell");
		is.setItemMeta(meta);
		return is;
	}

	public void onShift(Player p)
	{

	}

	public void onSpellToggle(boolean b, Player p)
	{
		if (b)
		{
			p.sendMessage(ChatColor.DARK_RED + "You Activated The "
					+ ChatColor.BOLD + "Fireball " + ChatColor.DARK_RED
					+ "Spell!");
		}
		else if (!b)
		{
			p.sendMessage(ChatColor.DARK_RED + "You De-Activated The "
					+ ChatColor.BOLD + "Fireball " + ChatColor.DARK_RED
					+ "Spell!");
		}
		else
		{
			p.sendMessage(ChatColor.DARK_RED + "You null-ed The "
					+ ChatColor.BOLD + "Fireball " + ChatColor.DARK_RED
					+ "Spell!");
		}
	}

}
