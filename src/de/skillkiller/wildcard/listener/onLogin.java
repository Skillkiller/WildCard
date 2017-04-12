package de.skillkiller.wildcard.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.skillkiller.wildcard.WildCard;

public class onLogin implements Listener {

	private WildCard plugin;
	
	public onLogin(WildCard plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLoginEvent(PlayerLoginEvent e) {
		if (plugin.getConfig().getBoolean("enabled")) {
			Player p = e.getPlayer();
			if(p.hasPermission("WildCard.bypass") || plugin.sqlHandler.hasWildcard(p)) {
				e.allow();
			} else {
				e.disallow(Result.KICK_WHITELIST, "\n §6Dir §cscheint es nicht erlaubt zu sein den Server zu betreten!\n"
						+ "§9Falls du eine Wildcard besitzt dann löse sie auf §6http://thecoin.de §9ein");
			}
		} else {
			e.allow();
		}
		
	}
	
}
