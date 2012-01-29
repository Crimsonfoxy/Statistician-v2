package com.ChaseHQ.Statistician;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.Database;
import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;
import com.ChaseHQ.Statistician.Listeners.BlockListener;
import com.ChaseHQ.Statistician.Listeners.EntityListener;
import com.ChaseHQ.Statistician.Listeners.PlayerListener;
import com.ChaseHQ.Statistician.Stats.PlayerData;

public class StatisticianPlugin extends JavaPlugin {
	private static StatisticianPlugin singleton = null;
	private ExecutorService executorService;
	private DataProcessor dataProcessor;
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

		Log.ConsoleLog("Version " + Config.getStatisticianVersion() + " By ChaseHQ Starting Up...");

		// Make sure the configuration is accessible
		if (Config.getConfig() == null) {
			this.getPluginLoader().disablePlugin(this);
			return;
		}

		// Check mySQL Dependency
		if (Database.getDB() == null) {
			this.getPluginLoader().disablePlugin(this);
			return;
		}

		Database.getDB().callStoredProcedure("pluginStartup", null);

		this.executorService = Executors.newCachedThreadPool();

		this.edhPlayer = new EDHPlayer();

		this.playerData = new PlayerData();

		this.dataProcessor = new DataProcessor();
		this.dataProcessor.addProcessable(this.playerData);

		new Timer(true).scheduleAtFixedRate(this.dataProcessor, Config.getConfig().getDBUpdateTime(), Config.getConfig().getDBUpdateTime());

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
		Log.ConsoleLog("Shutting down...");

		if (this.edhPlayer != null) {
			for (Player player : this.getServer().getOnlinePlayers()) {
				this.edhPlayer.PlayerQuit(player);
			}
		}

		if (this.playerData != null) {
			this.playerData._processData();
		}

		Database db = Database.getDB();
		if (db != null) {
			db.callStoredProcedure("pluginShutdown", null);
		}

		StatisticianPlugin.singleton = null;

		if (this.executorService != null) {
			this.executorService.shutdown();
		}
	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();

		// Block Listeners
		pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);

		// Entity Listeners
		pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Normal, this);

		// Player Listeners
		pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, this.playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, this.playerListener, Event.Priority.Normal, this);

		// TODO: Register Inventory Craft Event
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
