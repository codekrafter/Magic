
package net.codekrafter.plugins.magic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.codekrafter.plugins.magic.api.Spell;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Magic extends JavaPlugin
{

	public static Material wand = Material.STICK;
	public static Map<String, Spell> spells = new HashMap<String, Spell>();
	public File spellDir = new File(getDataFolder(), "spells");
	public File spellListing = new File(getDataFolder(), "spells/spells.yml");

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		wand = Material.getMaterial(getConfig().getString("wand.type"));
		getServer().getPluginManager().registerEvents(new MagicListener(), this);
		getLogger().info(wand.name() + " : " + wand.toString());
		try
		{
			manageFiles();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void manageFiles() throws IOException
	{
		if(!spellDir.exists()) {
			spellDir.mkdir();
		}
		
		if(!spellListing.exists()) {
			spellListing.createNewFile();
		}
	}

	@Override
	public void onDisable()
	{
		getConfig().set("wand.type", wand.toString());
	}

}
