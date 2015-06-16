
package net.codekrafter.plugins.magic;

import net.codekrafter.plugins.magic.api.Spell;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class MagicListener implements Listener
{

	public MagicListener(Magic plugin)
	{
		this.plugin = plugin;
	}

	public Magic plugin;

	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e)
	{
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				|| e.getAction() == Action.RIGHT_CLICK_AIR)
		{
			if (e.getPlayer().getItemInHand().getType() == Magic.wand
					&& e.getPlayer().isSneaking())
			{
				Inventory inv = Bukkit.createInventory(e.getPlayer(), 9,
						ChatColor.BOLD + "Spells");
				for (Spell s : Magic.spells)
				{
					inv.addItem(s.getIcon());
				}
				e.getPlayer().openInventory(inv);
				if (Magic.mana.get(e.getPlayer()) == null)
				{}
			}
			for (Spell s : Magic.spells)
			{
				if (Magic.currentSpells.get(e.getPlayer()) == s
						&& !e.getPlayer().isSneaking()
						&& Magic.mana.get(e.getPlayer()) - s.onRightClick(null) > -1)
				{
					Magic.mana.put(e.getPlayer(), Magic.mana.get(e.getPlayer())
							- s.onRightClick(e.getPlayer()));
				}
			}
		}
		else if (e.getAction() == Action.LEFT_CLICK_BLOCK
				|| e.getAction() == Action.LEFT_CLICK_AIR)
		{
			for (Spell s : Magic.spells)
			{
				if (Magic.currentSpells.get(e.getPlayer()) == s
						&& Magic.mana.get(e.getPlayer()) - s.onLeftClick(null) > -1)
				{
					Magic.mana.put(e.getPlayer(), Magic.mana.get(e.getPlayer())
							- s.onLeftClick(e.getPlayer()));
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if (e.getInventory().getTitle()
				.equalsIgnoreCase(ChatColor.BOLD + "Spells"))
		{
			e.setCancelled(true);
			((Player) e.getWhoClicked()).updateInventory();
			for (Spell s : Magic.spells)
			{
				if (e.getCurrentItem().getType().toString()
						.equalsIgnoreCase(s.getIcon().getType().toString()))
				{
					Magic.currentSpells.put((Player) e.getWhoClicked(), s);
					s.onSpellToggle(true, (Player) e.getWhoClicked());
				}
			}
		}
	}

	@EventHandler
	public void EntityExplode(EntityExplodeEvent e)
	{
			e.blockList().clear();
	}
}
