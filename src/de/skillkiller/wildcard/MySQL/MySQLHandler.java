package de.skillkiller.wildcard.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import de.skillkiller.wildcard.WildCard;

public class MySQLHandler {
	
	  private WildCard plugin;
	  private MySQL sql;
	
	  public MySQLHandler(MySQL mysql, WildCard plugin)
	  {
	    this.sql = mysql;
	    this.sql.queryUpdate("CREATE TABLE IF NOT EXISTS card (id int NOT NULL AUTO_INCREMENT, card varchar(32), used int(11), maxuse int(11), PRIMARY KEY (id)) DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci");
	    this.sql.queryUpdate("CREATE TABLE IF NOT EXISTS player (id int NOT NULL AUTO_INCREMENT, name varchar(32), card varchar(32), PRIMARY KEY (id)) DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci");
	    this.plugin = plugin;
	  }
	  
	  public String addCard(Player p, int maxuse) {
		  if (maxuse > 20) {
			  return null;
		  }
		  
		  Connection conn = this.sql.getConnection();
		  
		  try {
			PreparedStatement st = conn.prepareStatement("INSERT INTO `card` (`id`, `card`, `used`, `maxuse`) VALUES (NULL, ?, '0', ?)");
			String key = getString(32, "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789");
			st.setString(1, key);
			st.setInt(2, maxuse);
			st.executeUpdate();
			st.close();
			return key;
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, e.getMessage(), e);
		}
		  
		  return null;
	  }
	  
	  public boolean hasWildcard(Player p) {
		  Connection conn = this.sql.getConnection();
		  try {
			PreparedStatement st = conn.prepareStatement("SELECT COUNT(*) as count FROM player WHERE `name` LIKE ?");
			st.setString(1, p.getName());
			ResultSet rs = st.executeQuery();
			
			rs.first();
			
			
			if (rs.getInt("count") == 1) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, e.getMessage(), e);
		}
		  
		  
		  return false;
	  }
	  
	  public String getWildCardbyPlayer(Player p) {
		  Connection conn = plugin.sql.getConnection();
		  try {
			PreparedStatement st = conn.prepareStatement("SELECT `card` FROM `player` WHERE `name` LIKE ? ");
			st.setString(1, p.getName());
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				rs.first();
				return rs.getString("card");
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			plugin.getLogger().log(Level.WARNING, e.getMessage(), e);
		}
		  
		  
		  
		  return null;
	  }
	  
	  
	  
	    public static String getString(int len, String charSet) {
	        String result = "";
	       
	        while (result.length() < len) {
	            result = result + getChar(charSet);
	        }
	       
	        return result;
	    }
	   
	    public static char getChar(String charSet) {
	        int s = getInt(charSet.length());
	        return charSet.charAt(s - 1);
	    }
	    
	    public static int getInt(int max) {
	        return (int) (Math.ceil(Math.random() * max));
	    }
	  
}
