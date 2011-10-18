package com.ChaseHQ.Statistician.Config;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import com.ChaseHQ.Statistician.Log;
import com.ChaseHQ.Statistician.StatisticianPlugin;

// *NOTE Config class must not be used before the plugin's onEnable is called.
public class Config {
	public static String _statisticianVersion;
	public static final Integer DBVersion = 5;
	public static String _logPrefix;
	
	private FileConfiguration _config;
	
	private String _dbAddress = "localhost";
	private int _dbPort = 3306;
	private String _dbName = "mc_statistician";
	private String _dbUsername = "mc_statistician";
	private String _dbPassword = "mc_statistician";
	private int _databaseUpdateTime = 120;
	
	private static Config _internalConfig = null;

	static {
		StatisticianPlugin plugin = StatisticianPlugin.getEnabledPlugin();
		PluginDescriptionFile pdf = plugin.getDescription();
		_statisticianVersion = pdf.getVersion();
		_logPrefix = "[" + pdf.getName() + "]";
	}
	
	public static Config getConfig() {
		if (_internalConfig == null) {
			// Instantiate
			try {
				_internalConfig = new Config();
			} catch (IOException e) {
				Log.ConsoleLog("Could not load/write Database Config file. Fatal error.");
				return null;
			}
		}
		
		return _internalConfig;
	}

	public static String getStatisticianVersion() {
		return _statisticianVersion;
	}

	public static String getLogPrefix() {
		return _logPrefix;
	}
	
	public Config() throws IOException {
		if (_internalConfig != null)
			return;

		StatisticianPlugin plugin = StatisticianPlugin.getEnabledPlugin();

		_config = plugin.getConfig();
		_config.options().copyDefaults(true);
		_dbAddress = _config.getString("database_address");
		_dbPort = _config.getInt("database_port");
		_dbName = _config.getString("database_name");
		_dbUsername = _config.getString("database_username");
		_dbPassword = _config.getString("database_password");
		_databaseUpdateTime = _config.getInt("database_update_time");

		plugin.saveConfig();
	}

	/**
	 * @return the _dbAddress
	 */
	public String get_dbAddress() {
		return _dbAddress;
	}

	/**
	 * @return the _dbPort
	 */
	public int get_dbPort() {
		return _dbPort;
	}
	
	/**
	 * @return the _databaseUpdateTime
	 */
	public int get_databaseUpdateTime() {
		return _databaseUpdateTime;
	}

	/**
	 * @return the _dbName
	 */
	public String get_dbName() {
		return _dbName;
	}

	/**
	 * @return the _dbUsername
	 */
	public String get_dbUsername() {
		return _dbUsername;
	}

	/**
	 * @return the _dbPassword
	 */
	public String get_dbPassword() {
		return _dbPassword;
	}
	
}
