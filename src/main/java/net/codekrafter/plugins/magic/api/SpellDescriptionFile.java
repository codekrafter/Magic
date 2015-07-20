
package net.codekrafter.plugins.magic.api;

import java.io.InputStream;
import java.util.Map;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public final class SpellDescriptionFile
{

	private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>()
		{

			@Override
			protected Yaml initialValue()
			{
				return new Yaml(new SafeConstructor()
					{

						{
							yamlConstructors.put(null, new AbstractConstruct()
								{

									public Object construct(final Node node)
									{
										if (!node.getTag().startsWith("!@"))
										{
											// Unknown tag - will fail
											return SafeConstructor.undefinedConstructor
													.construct(node);
										}
										// Unknown awareness - provide a
										// graceful substitution
										return new PluginAwareness()
											{

												@Override
												public String toString()
												{
													return node.toString();
												}
											};
									}
								});
							for (final PluginAwareness.Flags flag : PluginAwareness.Flags
									.values())
							{
								yamlConstructors.put(
										new Tag("!@" + flag.name()),
										new AbstractConstruct()
											{

												public PluginAwareness.Flags construct(
														final Node node)
												{
													return flag;
												}
											});
							}
						}
					});
			}
		};

	private String name;
	private String version;
	private String main;

	public SpellDescriptionFile(final InputStream stream)
			throws InvalidDescriptionException
	{
		loadMap(asMap(YAML.get().load(stream)));
	}

	private void loadMap(Map<?, ?> map) throws InvalidDescriptionException
	{
		setName((String) map.get("name"));
		setVersion((String) map.get("version"));
		setMain((String) map.get("main"));
	}

	private Map<?, ?> asMap(Object object) throws InvalidDescriptionException
	{
		if (object instanceof Map)
		{
			return (Map<?, ?>) object;
		}
		throw new InvalidDescriptionException(object
				+ " is not properly structured.");
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getMain()
	{
		return main;
	}

	public void setMain(String main)
	{
		this.main = main;
	}
}
