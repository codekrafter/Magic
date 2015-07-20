
package net.codekrafter.plugins.magic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.codekrafter.plugins.magic.api.Spell;
import net.codekrafter.plugins.magic.spellloading.SpellLoader;
import net.codekrafter.plugins.magic.spells.FireballSpell;
import net.codekrafter.plugins.magic.tasks.ManaRegenTask;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Magic extends JavaPlugin
{

	public static Material wandType = Material.STICK;
	public static String wandName = "" + ChatColor.DARK_PURPLE + ChatColor.BOLD
			+ "Magic Wand";
	public static List<String> wandLore = new ArrayList<String>();
	public static List<Spell> spells = new ArrayList<Spell>();
	public File spellDir = new File(getDataFolder(), "spells");
	public static Map<Player, Spell> currentSpells = new HashMap<Player, Spell>();
	public static Map<Player, Integer> mana = new HashMap<Player, Integer>();

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		wandLore.add("" + ChatColor.RESET + ChatColor.LIGHT_PURPLE
				+ "The Magic Wand For The Wizard Kit");
		wandType = Material.getMaterial(getConfig().getString("wand.type"));
		getServer().getPluginManager().registerEvents(new MagicListener(this),
				this);
		Bukkit.getScheduler().runTaskTimer(this, new ManaRegenTask(), 0, 100);
		manageFiles();
		new SpellLoader(this, getServer().getPluginManager()).loadSpells(getClassLoader());
		spells.add(new FireballSpell());
	}

	private void manageFiles()
	{
		if (!spellDir.exists())
		{
			spellDir.mkdir();
		}
	}

	@Override
	public void onDisable()
	{
		getConfig().set("wand.type", wandType.toString());
	}

}
