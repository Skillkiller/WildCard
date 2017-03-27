package de.skillkiller.wildcard.MySQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.skillkiller.wildcard.WildCard;

public class MySQL
{
  private String host;
  private int port;
  private String user;
  private String password;
  private String database;
  private Connection conn;
  
  public MySQL(WildCard plugin)
    throws Exception
  {
    File file = new File(plugin.getDataFolder(), "mysql.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    cfg.options().header("Die Datenbank wird zu speichern alle Wildcards und Spieler genutzt");
    String db = "database.";
    cfg.addDefault(db + "host", "localhost");
    cfg.addDefault(db + "port", Integer.valueOf(3306));
    cfg.addDefault(db + "user", "USER");
    cfg.addDefault(db + "password", "PASSWORD");
    cfg.addDefault(db + "database", "DATABASE");
    cfg.options().copyDefaults(true);
    try
    {
      cfg.save(file);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    this.host = cfg.getString(db + "host");
    this.port = cfg.getInt(db + "port");
    this.user = cfg.getString(db + "user");
    this.password = cfg.getString(db + "password");
    this.database = cfg.getString(db + "database");
    
    this.conn = openConnection();
  }
  
  public Connection openConnection()
    throws Exception
  {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
    return conn;
  }
  
  public void refreshConnect()
    throws Exception
  {
    Class.forName("com.mysql.jdbc.Driver");
    this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
  }
  
  public Connection getConnection()
  {
    try
    {
      if (!this.conn.isValid(1))
      {
        System.out.println("[WildCard] Lost MySQL-Connection! Reconnecting...");
        try
        {
          this.conn = openConnection();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    try
    {
      PreparedStatement stmt = this.conn.prepareStatement("SELECT 1");Throwable localThrowable2 = null;
      try
      {
        stmt.executeQuery();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (stmt != null) {
          if (localThrowable2 != null) {
            try
            {
              stmt.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            stmt.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println("[WildCars] SELECT 1 - failled. Reconnecting...");
      try
      {
        this.conn = openConnection();
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
    }
    return this.conn;
  }
  
  public boolean hasConnecion()
  {
    try
    {
      return (this.conn != null) || (this.conn.isValid(1));
    }
    catch (SQLException e) {}
    return false;
  }
  
  public void queryUpdate(String query)
  {
    Connection connection = this.conn;
    try
    {
      PreparedStatement st = connection.prepareStatement(query);Throwable localThrowable2 = null;
      try
      {
        st.executeUpdate();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (st != null) {
          if (localThrowable2 != null) {
            try
            {
              st.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            st.close();
          }
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void closeRessources(ResultSet rs, PreparedStatement st)
  {
    if (rs != null) {
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    if (st != null) {
      try
      {
        st.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void closeConnection()
  {
    try
    {
      this.conn.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      this.conn = null;
    }
  }
}
