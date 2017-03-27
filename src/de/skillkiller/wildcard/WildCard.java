package de.skillkiller.wildcard;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import de.skillkiller.wildcard.MySQL.MySQL;
import de.skillkiller.wildcard.MySQL.MySQLHandler;

public class WildCard extends JavaPlugin{

	public Logger logger;
	public MySQL sql;
	public MySQLHandler sqlHandler;
	
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
	    
	    
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
