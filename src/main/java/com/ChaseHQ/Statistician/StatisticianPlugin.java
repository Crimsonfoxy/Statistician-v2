package com.ChaseHQ.Statistician;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.DBConnectFail;
import com.ChaseHQ.Statistician.Database.Database;
import com.ChaseHQ.Statistician.Database.DataValues.DataValues_Config;
import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;
import com.ChaseHQ.Statistician.Listeners.BlockListener;
import com.ChaseHQ.Statistician.Listeners.EntityListener;
import com.ChaseHQ.Statistician.Listeners.PlayerListener;
import com.ChaseHQ.Statistician.Stats.PlayerData;

public class StatisticianPlugin extends JavaPlugin {
	private static StatisticianPlugin singleton = null;
	private Database database;
	private ExecutorService executorService;
	private DataProcessor dataProcessor;
	private Timer dataProcessorTimer;
	private PlayerData playerData;
	private EDHPlayer edhPlayer;

	private PlayerListener playerListener;
	private BlockListener blockListener;
	private EntityListener entityListener;

	//private InventoryListener inventoryListener;

	public static StatisticianPlugin getInstance() {
		return StatisticianPlugin.singleton;
	}

	@Override
	public void onEnable() {
		if (StatisticianPlugin.singleton != null) return;

		StatisticianPlugin.singleton = this;

		this.setNaggable(false);

		if (this.database == null) {
			try {
				this.database = new Database();
			} catch (ClassNotFoundException e) {
				this.getLogger().severe("MySQL Driver not found");
				if (Config.getConfig().isVerboseErrors()) {
					e.printStackTrace();
				}
			} catch (DBConnectFail e) {
				this.getLogger().log(Level.SEVERE, "Critical Error, could not connect to mySQL. Is the database Available? Check config file and try again. (" + e.getMessage() + ")");
				if (Config.getConfig().isVerboseErrors()) {
					e.printStackTrace();
				}
			}
		}
		if (this.database == null) {
			StatisticianPlugin.singleton = null;
			this.getPluginLoader().disablePlugin(this);
			return;
		}

		DataValues_Config.refresh();

		this.database.callStoredProcedure("pluginStartup", null);

		this.executorService = Executors.newCachedThreadPool();

		this.edhPlayer = new EDHPlayer();

		this.playerData = new PlayerData();

		this.dataProcessor = new DataProcessor();
		this.dataProcessor.addProcessable(this.playerData);

		this.dataProcessorTimer = new Timer(true);
		this.dataProcessorTimer.scheduleAtFixedRate(this.dataProcessor, Config.getConfig().getDBUpdateTime(), Config.getConfig().getDBUpdateTime());

		// Setup Listeners
		this.playerListener = new PlayerListener(this.edhPlayer);
		this.blockListener = new BlockListener(this.edhPlayer);
		this.entityListener = new EntityListener(this.edhPlayer);
		//this.inventoryListener = new InventoryListener(this.edhPlayer);

		this.registerEvents();

		// This could be a reload so see if people are logged in
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.edhPlayer.PlayerJoin(player);
		}
	}

	@Override
	public void onDisable() {
		if (StatisticianPlugin.singleton == null || !StatisticianPlugin.singleton.equals(this)) return;

		this.dataProcessorTimer.cancel();

		if (this.edhPlayer != null) {
			for (Player player : this.getServer().getOnlinePlayers()) {
				this.edhPlayer.PlayerQuit(player);
			}
		}

		if (this.playerData != null) {
			this.playerData._processData();
		}

		if (this.database != null) {
			this.database.callStoredProcedure("pluginShutdown", null);
			this.database = null;
		}

		StatisticianPlugin.singleton = null;

		if (this.executorService != null) {
			this.executorService.shutdown();
		}
	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.entityListener, this);
		pm.registerEvents(this.playerListener, this);
	}

	public Database getDB() {
		return this.database;
	}

	public ExecutorService getExecutor() {
		return this.executorService;
	}

	public PlayerData getPlayerData() {
		return this.playerData;
	}

	public boolean permissionToRecordStat(Player player) {
		return !player.hasPermission("statistician.ignore");
	}
}
