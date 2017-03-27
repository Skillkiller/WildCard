package de.skillkiller.wildcard;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor{

	private WildCard plugin;
	public Command(WildCard plugin) {
		this.plugin = plugin;
		
		
	}
	
	
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
        	Player p = (Player) sender;
        	if (args.length == 2 && args[0].equals("add")) {
        		if (testString(args[1])) {
        			p.sendMessage(plugin.prefix + "§aWildCard wird erstellt:");
        			String key = plugin.sqlHandler.addCard(p, Integer.parseInt(args[1]));
        			if (key == null) {
        				p.sendMessage(plugin.prefix + "§cEs gab einen Fehler!");
        			} else {
        				p.sendMessage(key);
        			}
        			
        			
        		} else {
        			p.sendMessage(plugin.prefix + "§cFalsche Angabe für die maximale Benutzung");
        		}
        	} else {
        		p.sendMessage(plugin.prefix + "§c/wildcard add [Maximale Benutzung]");
        	}
            
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
    
    public boolean testString (String a){
    	  for( int i = 0, n = a.length(); i<n; i++ ){
    	    if( ! Character.isDigit( a.charAt( i ))) {
    	      return false;
    	    }
    	  }
    	  return true;
    	}


	
}
