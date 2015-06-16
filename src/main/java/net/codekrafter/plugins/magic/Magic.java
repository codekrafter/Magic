
package net.codekrafter.plugins.magic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.codekrafter.plugins.magic.api.Spell;
import net.codekrafter.plugins.magic.spells.FireballSpell;
import net.codekrafter.plugins.magic.tasks.ManaRegenTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Magic extends JavaPlugin
{

	public static Material wand = Material.STICK;
	public static List<Spell> spells = new ArrayList<Spell>();
	public File spellDir = new File(getDataFolder(), "spells");
	public File spellListing = new File(getDataFolder(), "spells/spells.yml");
	public static Map<Player, Spell> currentSpells = new HashMap<Player, Spell>();
	public static Map<Player, Double> mana = new HashMap<Player, Double>();

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		wand = Material.getMaterial(getConfig().getString("wand.type"));
		getServer().getPluginManager().registerEvents(new MagicListener(this),
				this);
		Bukkit.getScheduler().runTaskTimer(this, new ManaRegenTask(), 0, 100);
		try
		{
			manageFiles();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		spells.add(new FireballSpell());
	}

	private void manageFiles() throws IOException
	{
		if (!spellDir.exists())
		{
			spellDir.mkdir();
		}

		if (!spellListing.exists())
		{
			spellListing.createNewFile();
		}
	}

	@Override
	public void onDisable()
	{
		getConfig().set("wand.type", wand.toString());
	}

}
