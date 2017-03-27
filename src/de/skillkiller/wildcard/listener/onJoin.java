package de.skillkiller.wildcard.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.skillkiller.wildcard.WildCard;

public class onJoin implements Listener{

	private WildCard plugin;
	
	public onJoin(WildCard plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoinEvemt(PlayerJoinEvent e) {
		if (plugin.getConfig().getBoolean("enabled") && plugin.getConfig().getBoolean("message")) {
			Player p = e.getPlayer();
			if (p.hasPermission("WildCard.bypass") || plugin.sqlHandler.hasWildcard(p)) {
				p.sendMessage(plugin.prefix + "§aWillkommen auf dem Server.");
				
				// pZahl > 0 ? pZahl+" ist positiv." : pZahl+" ist negativ oder 0."
				p.sendMessage(plugin.prefix + "§7Wildcard§8: §6" + plugin.sqlHandler.getWildCardbyPlayer(p) == null ? "keine" : plugin.sqlHandler.getWildCardbyPlayer(p));
				p.sendMessage(plugin.prefix + "§7Rechte§8: " + (p.hasPermission("WildCard.bypass") == true ? "Ja" : "Nein"));
			}
		}
	}
}
