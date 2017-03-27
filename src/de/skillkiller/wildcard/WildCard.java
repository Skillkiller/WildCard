package de.skillkiller.wildcard;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.skillkiller.wildcard.MySQL.MySQL;
import de.skillkiller.wildcard.MySQL.MySQLHandler;
import de.skillkiller.wildcard.listener.onJoin;
import de.skillkiller.wildcard.listener.onLogin;

public class WildCard extends JavaPlugin{

	public Logger logger = getLogger();
	public MySQL sql;
	public MySQLHandler sqlHandler;
	public FileConfiguration cfg = getConfig();
	public boolean enabled;
	
	final public String prefix = "§[§9WildCard§8]§r ";
	
	@Override
	public void onEnable() {
		this.logger.info("Plugin startet");
		
	    try
	    {
	      this.logger.info("Loading MySQL ...");
	      this.sql = new MySQL(this);
	      this.sqlHandler = new MySQLHandler(this.sql, this);
	      //startRefresh();
	      this.logger.info("MySQL successfully loaded.");
	    }
	    catch (Exception e1)
	    {
	      this.logger.warning("Failled to load MySQL: " + e1.toString());
	    }
	    
	    this.getCommand("wildcard").setExecutor(new de.skillkiller.wildcard.Command(this));
		
	    if (getConfig().getBoolean("enabled")) {
	    	Bukkit.getServer().getPluginManager().registerEvents(new onLogin(this), this);
	    	Bukkit.getServer().getPluginManager().registerEvents(new onJoin(this), this);
	    } else {
	    	logger.info("Plugin beschränkt");
	    }
	    
	    
	    cfg.options().copyDefaults(true);
	    saveConfig();
	    
	}
	
	@Override
	public void onDisable() {
		
	}
}
