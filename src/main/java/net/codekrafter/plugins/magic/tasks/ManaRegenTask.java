
package net.codekrafter.plugins.magic.tasks;

import net.codekrafter.plugins.magic.Magic;

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
				Magic.mana.put(p, 0D);
			}
			else
			{
				loopFunction(p);
			}
			Float xp = Float.valueOf(String.valueOf(Magic.mana.get(p)));
			p.setExp(xp);
			p.sendMessage("hey, your mana is: " + Magic.mana.get(p));
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
	}

}
