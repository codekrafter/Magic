
package net.codekrafter.plugins.magic;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicListener implements Listener
{

	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e)
	{
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK
				|| e.getAction() == Action.RIGHT_CLICK_AIR)
		{
			if (e.getPlayer().getItemInHand().getType() == Magic.wand && e.getPlayer().isSneaking())
			{
				e.getPlayer().sendMessage("You Actived Your Wand!");
			}
		}
	}
}
