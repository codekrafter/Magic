/**
 * @author codekrafter
 *
 */

package net.codekrafter.plugins.magic.spellloading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.codekrafter.plugins.magic.Magic;
import net.codekrafter.plugins.magic.api.Spell;
import net.codekrafter.plugins.magic.api.SpellDescriptionFile;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginManager;
import org.yaml.snakeyaml.error.YAMLException;

/**
 * @author codekrafter
 *
 */
public class SpellLoader
{

	Magic pl;

	public Magic getPlugin()
	{
		return pl;
	}

	public void setPlugin(Magic plugin)
	{
		this.pl = plugin;
	}

	public PluginManager getPm()
	{
		return pm;
	}

	public void setPm(PluginManager pm)
	{
		this.pm = pm;
	}

	PluginManager pm;

	public SpellLoader(Magic magic, PluginManager pluginManager)
	{
		setPlugin(magic);
		setPm(pluginManager);
	}

	public void loadSpells(ClassLoader classloader)
	{
		/*
		 * Get Jars
		 */

		final List<File> jarList = new ArrayList<File>();
		for (File file : pl.spellDir.listFiles())
		{
			if (file.getName().endsWith(".jar"))
			{
				jarList.add(file);
			}
		}

		/*
		 * Create Classloader
		 */

		Map<URL, String> urls = new HashMap<URL, String>();
		ClassLoader cl = classloader;
		try
		{
			urls.put(pl.spellDir.toURI().toURL(), null);
			for (File f : jarList)
			{
				urls.put(f.toURI().toURL(), getSpellDescription(f).getMain());
			}
			Object[] urlsArrayObject = urls.values().toArray();
			URL[] urlsArray = new URL[urls.size()];
			int i0 = 0;
			for (Object o : urlsArrayObject)
			{
				urlsArray[i0] = (URL) o;
				i0++;
			}
			cl = new URLClassLoader(urlsArray, classloader);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (InvalidDescriptionException e)
		{
			e.printStackTrace();
		}

		/*
		 * Load Classes
		 */

		for (Map.Entry<URL, String> entry : urls.entrySet())
		{
			Class<?> clazz = null;
			Spell spellObject = null;
			if (entry.getValue() != null)
			{
				try
				{
					clazz = cl.loadClass(entry.getValue());
					spellObject = (Spell) clazz.newInstance();
					spellObject.onSpellLoad();
					Magic.spells.add(spellObject);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	public SpellDescriptionFile getSpellDescription(File file)
			throws InvalidDescriptionException
	{
		Validate.notNull(file, "File cannot be null");

		JarFile jar = null;
		InputStream stream = null;

		try
		{
			jar = new JarFile(file);
			JarEntry entry = jar.getJarEntry("spell.yml");

			if (entry == null)
			{
				throw new InvalidDescriptionException(
						new FileNotFoundException(
								"Jar does not contain spell.yml"));
			}

			stream = jar.getInputStream(entry);

			return new SpellDescriptionFile(stream);

		}
		catch (IOException ex)
		{
			throw new InvalidDescriptionException(ex);
		}
		catch (YAMLException ex)
		{
			throw new InvalidDescriptionException(ex);
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{}
			}
			if (stream != null)
			{
				try
				{
					stream.close();
				}
				catch (IOException e)
				{}
			}
		}
	}

}
