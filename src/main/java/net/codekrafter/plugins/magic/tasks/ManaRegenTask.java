
package net.codekrafter.plugins.magic.tasks;

import net.codekrafter.plugins.magic.Magic;
import net.codekrafter.plugins.utils.ExperienceManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegenTask extends BukkitRunnable
{

	public void run()
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (Magic.mana.get(p) == null)
			{
				Magic.mana.put(p, 0);
			}
			else
			{
				loopFunction(p);
			}
			Float xp = Float.valueOf(String.valueOf(Magic.mana.get(p)));
		}

	}

	private void loopFunction(Player p)
	{
		if (Magic.mana.get(p) > 100)
		{
			Magic.mana.put(p, Magic.mana.get(p)-1);
			loopFunction(p);
		}
		else if (Magic.mana.get(p) < 100)
		{
			Magic.mana.put(p, Magic.mana.get(p)+10);
			if (Magic.mana.get(p) > 100)
			{
				Magic.mana.put(p, Magic.mana.get(p)-1);
				loopFunction(p);
			}
		}
		ExperienceManager xpman = new ExperienceManager(p);
		int xpforlvl = xpman.getXpForLevel(Magic.mana.get(p));
		xpman.setExp(xpforlvl);
	}

}
