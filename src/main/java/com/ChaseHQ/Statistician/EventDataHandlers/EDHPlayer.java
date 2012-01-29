package com.ChaseHQ.Statistician.EventDataHandlers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ChaseHQ.Statistician.StatisticianPlugin;
import com.ChaseHQ.Statistician.Database.DBSynchDataGetSet;
import com.ChaseHQ.Statistician.Database.Database;
import com.ChaseHQ.Statistician.Database.DataValues.DBDataValues_Players;
import com.ChaseHQ.Statistician.Database.DataValues.DataStores;
import com.ChaseHQ.Statistician.Database.DataValues.DataValues_Config;
import com.ChaseHQ.Statistician.Stats.KillTag;
import com.ChaseHQ.Statistician.Utils.StringHandler;

public class EDHPlayer {
	public void PlayerJoin(final Player player) {
		this.handleEventData(StatType.Join, player);
	}

	public void PlayerQuit(final Player player) {
		this.handleEventData(StatType.Join, player);
	}

	public void PlayerBlockBreak(final Player player, final Integer blockID) {
		this.handleEventData(StatType.Join, player, blockID);
	}

	public void PlayerBlockPlace(final Player player, final Integer blockID) {
		this.handleEventData(StatType.Join, player, blockID);
	}

	public void PlayerMove(final Player player, final boolean isInMinecart, final boolean isOnPig, final boolean isInBoat) {
		this.handleEventData(StatType.Join, player, isInMinecart, isOnPig, isInBoat);
	}

	public void PlayerKilledByPlayer(final Player killer, final Player victim, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, victim, cause);
	}

	public void PlayerKilledByPlayerProjectile(final Player killer, final Player victim, final Entity projectile, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, victim, projectile, cause);
	}

	public void PlayerKilledByCreature(final Player victim, final Creature creature, final DamageCause cause) {
		this.handleEventData(StatType.Join, victim, creature, cause);
	}

	public void PlayerKilledBySlime(final Player victim, final Slime creature, final DamageCause cause) {
		this.handleEventData(StatType.Join, victim, creature, cause);
	}

	public void PlayerKilledByCreatureProjectile(final Player victim, final Creature creature, final Entity projectile, final DamageCause cause) {
		this.handleEventData(StatType.Join, victim, creature, projectile, cause);
	}

	public void PlayerKilledCreature(final Player killer, final Creature creature, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, creature, cause);
	}

	public void PlayerKilledSlime(final Player killer, final Slime creature, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, creature, cause);
	}

	public void PlayerKilledCreatureProjectile(final Player killer, final Creature creature, final Entity projectile, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, creature, projectile, cause);
	}

	public void PlayerKilledSlimeProjectile(final Player killer, final Slime creature, final Entity projectile, final DamageCause cause) {
		this.handleEventData(StatType.Join, killer, creature, projectile, cause);
	}

	public void PlayerKilledByBlock(final Player victim, final Block block, final DamageCause cause) {
		this.handleEventData(StatType.Join, victim, block, cause);
	}

	public void PlayerKilledByOtherCause(final Player victim, final DamageCause cause) {
		this.handleEventData(StatType.Join, victim, cause);
	}

	public void PlayerPickedUpItem(final Player player, final Integer itemID, final Integer numberInStack) {
		this.handleEventData(StatType.Join, player, itemID, numberInStack);
	}

	public void PlayerDroppedItem(final Player player, final Integer itemID, final Integer numberInStack) {
		this.handleEventData(StatType.Join, player, itemID, numberInStack);
	}

	private KillTag getKillEventTag(final StatType type, final Player player, final Object... data) {
		final StatisticianPlugin plugin = StatisticianPlugin.getInstance();
		switch (type) {
			case KilledByBlock:
				//Player victim, Block block, DamageCause cause
				return new KillTag((Block)data[1], (Player)data[0], (DamageCause)data[2]);
			case KilledBySlime:
				//Player victim, Slime creature, DamageCause cause
				return new KillTag((Slime)data[1], (Player)data[0], (DamageCause)data[2]);
			case KilledByCreature:
				//Player victim, Creature creature, DamageCause cause
				return new KillTag((Creature)data[1], (Player)data[0], (DamageCause)data[2]);
			case KilledByCreatureProjectile:
				//Player victim, Creature creature, Entity projectile, DamageCause cause
				return new KillTag((Creature)data[1], (Player)data[0], (Projectile)data[2], (DamageCause)data[3]);
			case KilledByOtherCause:
				//Player victim, DamageCause cause
				return new KillTag((Player)data[0], (DamageCause)data[1]);
			case KilledByPlayer:
				//Player killer, Player victim, DamageCause cause
				return new KillTag((Player)data[0], (Player)data[1], (DamageCause)data[2]);
			case KilledByPlayerProjectile:
				//Player killer, Player victim, Entity projectile, DamageCause cause
				return new KillTag((Player)data[0], (Player)data[1], (Projectile)data[2], (DamageCause)data[3]);
			case KilledSlime:
				//Player killer, Slime creature, DamageCause cause
				return new KillTag((Player)data[0], (Slime)data[1], (DamageCause)data[2]);
			case KilledSlimeProjectile:
				//Player killer, Slime creature, Entity projectile, DamageCause cause
				return new KillTag((Player)data[0], (Slime)data[1], (Projectile)data[2], (DamageCause)data[3]);
			case KilledCreature:
				//Player killer, Creature creature, DamageCause cause
				return new KillTag((Player)data[0], (Creature)data[1], (DamageCause)data[2]);
			case KilledCreatureProjectile:
				//Player killer, Creature creature, Entity projectile, DamageCause cause
				return new KillTag((Player)data[0], (Creature)data[1], (Projectile)data[2], (DamageCause)data[3]);
		}
		return null;
	}

	private void handleEventData(final StatType type, final Player player, final Object... data) {
		final StatisticianPlugin plugin = StatisticianPlugin.getInstance();

		// Permission Check
		if (!plugin.permissionToRecordStat(player)) return;

		Runnable runnable = null;
		switch (type) {
			case BlockBreak: //Player player, Integer blockID
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().addBlockBreak(player.getUniqueId().toString(), (Integer)data[0]);
					}
				};
				break;
			case BlockPlace: //Player player, Integer blockID
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().addBlockPlaced(player.getUniqueId().toString(), (Integer)data[0]);
					}
				};
				break;
			case DroppedItem: //Player player, Integer itemID, Integer numberInStack
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().addItemDropped(player.getUniqueId().toString(), (Integer)data[0], (Integer)data[1]);
					}
				};
				break;
			case Join: //Player player
				runnable = new Runnable() {
					@Override
					public void run() {
						try {
							if (DBSynchDataGetSet.isPlayerInDB(player.getUniqueId().toString())) {
								// Player Exists and has been here before
								DBSynchDataGetSet.playerLogin(player.getUniqueId().toString());
								if (DataValues_Config.SHOW_LASTJOIN_WELCOME.getConfigValueBoolean()) {
									// Show Last Joined Message
									String lastJoinMessage = StringHandler.formatForChat(DataValues_Config.LASTJOIN_WELCOME_MSG.getConfigValueString(), player);
									String timeStamp = DBSynchDataGetSet.getValue(DataStores.PLAYER, DBDataValues_Players.LAST_LOGOUT, DBDataValues_Players.UUID, player.getUniqueId().toString());
									String lastJoin = new SimpleDateFormat(DataValues_Config.DATE_FORMAT.getConfigValueString()).format(new Date(Long.parseLong(timeStamp) * 1000));
									lastJoinMessage = lastJoinMessage.replaceAll("\\{lastJoin}", lastJoin);
									player.sendMessage(lastJoinMessage);
								}
							} else {
								// First Time Player Logged in with Statistician Running
								DBSynchDataGetSet.playerCreate(player.getUniqueId().toString(), player.getName());
								if (DataValues_Config.SHOW_FIRSTJOIN_WELCOME.getConfigValueBoolean()) {
									// If they want to show first join message
									player.sendMessage(StringHandler.formatForChat(DataValues_Config.FIRSTJOIN_WELCOME_MSG.getConfigValueString(), player));
								}
							}
							// Check and update the most users ever logged on
							Database.getDB().callStoredProcedure("updateMostEverOnline", null);
						} catch (NullPointerException e) {

						} finally {
							// Watch them
							StatisticianPlugin.getInstance().getPlayerData().addPlayerToWatch(player.getUniqueId().toString(), player.getLocation());
						}
					}
				};
				break;
			case Move: //Player player, boolean isInMinecart, boolean isOnPig, boolean isInBoat
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().incrementStepsTaken(player.getUniqueId().toString(), player.getLocation(), (Boolean)data[0], (Boolean)data[1], (Boolean)data[2]);
					}
				};
				break;
			case PickedUpItem: //Player player, Integer itemID, Integer numberInStack
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().addItemPickup(player.getUniqueId().toString(), (Integer)data[0], (Integer)data[1]);
					}
				};
				break;
			case Quit: //Player player
				runnable = new Runnable() {
					@Override
					public void run() {
						DBSynchDataGetSet.playerLogout(player.getUniqueId().toString());
						// Unwatch Them
						StatisticianPlugin.getInstance().getPlayerData().removePlayerToWatch(player.getUniqueId().toString());
					}
				};
				break;
			case KilledByPlayer: //Player killer, Player victim, DamageCause cause
			case KilledByPlayerProjectile: //Player killer, Player victim, Entity projectile, DamageCause cause
				// Permission Check
				if (!StatisticianPlugin.getInstance().permissionToRecordStat((Player)data[0])) return;
			case KilledByBlock: //Player victim, Block block, DamageCause cause
			case KilledByCreature: //Player victim, Creature creature, DamageCause cause
			case KilledByCreatureProjectile: //Player victim, Creature creature, Entity projectile, DamageCause cause
			case KilledByOtherCause: //Player victim, DamageCause cause
			case KilledBySlime: //Player victim, Slime creature, DamageCause cause
			case KilledCreature: //Player killer, Creature creature, DamageCause cause
			case KilledCreatureProjectile: //Player killer, Creature creature, Entity projectile, DamageCause cause
			case KilledSlime: //Player killer, Slime creature, DamageCause cause
			case KilledSlimeProjectile: //Player killer, Slime creature, Entity projectile, DamageCause cause
				runnable = new Runnable() {
					@Override
					public void run() {
						StatisticianPlugin.getInstance().getPlayerData().addKillTag(player.getUniqueId().toString(), EDHPlayer.this.getKillEventTag(type, player, data));
					}
				};
				break;
		}

		if (runnable != null) {
			plugin.getExecutor().execute(runnable);
		}
	}

	private enum StatType {
		Join,
		Quit,
		BlockBreak,
		BlockPlace,
		Move,
		KilledByPlayer,
		KilledByPlayerProjectile,
		KilledByCreature,
		KilledBySlime,
		KilledByCreatureProjectile,
		KilledCreature,
		KilledSlime,
		KilledCreatureProjectile,
		KilledSlimeProjectile,
		KilledByBlock,
		KilledByOtherCause,
		PickedUpItem,
		DroppedItem
	}
}
