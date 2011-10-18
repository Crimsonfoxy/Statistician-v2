package com.ChaseHQ.Statistician;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.StatDB;
import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;
import com.ChaseHQ.Statistician.Listeners.StatisticianBlockListener;
import com.ChaseHQ.Statistician.Listeners.StatisticianCBInventoryListener;
import com.ChaseHQ.Statistician.Listeners.StatisticianEntityListener;
import com.ChaseHQ.Statistician.Listeners.StatisticianPlayerListener;
import com.ChaseHQ.Statistician.Stats.PlayerData;

public class StatisticianPlugin extends JavaPlugin { 
	
	private static StatisticianPlugin _singleton = null;
	private ExecutorService executor;
	private DataProcessor _dprocessor;
	private PlayerData _playerData;
	private EDHPlayer eventDataHandlerPlayer;

	@Override
	public void onDisable() {
		Log.ConsoleLog("Shutting down...");
		
		if (eventDataHandlerPlayer != null)
			for (Player player : getServer().getOnlinePlayers()) {	
				eventDataHandlerPlayer.PlayerQuit(player);
			}

		if (_playerData != null)
			_playerData._processData();
		
		StatDB db = StatDB.getDB();
		if (db != null)
			db.callStoredProcedure("pluginShutdown", null);
		
		_singleton = null;
		
		executor.shutdown();
	}

	@Override
	public void onEnable() {
		if (_singleton != null) {
			return;
		}
		
		_singleton = this;
		
		setNaggable(false);
		
		executor = Executors.newCachedThreadPool();
		
		Log.ConsoleLog("Version " + Config.getStatisticianVersion() + " By ChaseHQ Starting Up...");
		
		// Make sure the configuration is accessible
		if (Config.getConfig() == null) {
			getPluginLoader().disablePlugin(this);
			return;
		}
		
		// Check mySQL Dependency
		if (StatDB.getDB() == null) {
			getPluginLoader().disablePlugin(this);
			return;
		}
		
		StatDB.getDB().callStoredProcedure("pluginStartup", null);
		
		eventDataHandlerPlayer = new EDHPlayer();
		
		// Setup Listeners
		StatisticianPlayerListener _pl = new StatisticianPlayerListener(eventDataHandlerPlayer);
		StatisticianBlockListener _bl = new StatisticianBlockListener(eventDataHandlerPlayer);
		StatisticianEntityListener _el = new StatisticianEntityListener(eventDataHandlerPlayer);
		
		StatisticianCBInventoryListener _cbil = new StatisticianCBInventoryListener(eventDataHandlerPlayer);
		
		PluginManager pm = getServer().getPluginManager();
		
		// Release Build Register only used events
		// Block Listeners
		pm.registerEvent(Event.Type.BLOCK_BREAK, _bl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, _bl, Event.Priority.Normal, this);
		// Entity Listeners
		pm.registerEvent(Event.Type.ENTITY_DEATH, _el, Event.Priority.Normal, this);
		// Player Listeners
		pm.registerEvent(Event.Type.PLAYER_JOIN, _pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, _pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, _pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, _pl, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, _pl, Event.Priority.Normal, this);

		// TODO: Register Inventory Craft Event
		
		_playerData = new PlayerData();
		
		_dprocessor = new DataProcessor();
		_dprocessor.addProcessable(_playerData);
		
		new Timer(true).scheduleAtFixedRate(_dprocessor, Config.getConfig().get_databaseUpdateTime() * 1000, Config.getConfig().get_databaseUpdateTime() * 1000);
		
		// This could be a reload so see if people are logged in
		for (Player player : getServer().getOnlinePlayers()) {
			eventDataHandlerPlayer.PlayerJoin(player);
		}
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
	public static StatisticianPlugin getEnabledPlugin() {
		return _singleton;
	}
	
	public PlayerData getPlayerData() {
		return _playerData;
	}
	
	public boolean permissionToRecordStat(Player player) {
		if (player.hasPermission("Statistician.ignoreOverride"))
			return true;
		if (player.hasPermission("Statistician.ignore"))
			return false;
		return true;
	}

}
