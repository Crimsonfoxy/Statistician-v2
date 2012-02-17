package com.ChaseHQ.Statistician.EventDataHandlers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ChaseHQ.Statistician.StatisticianPlugin;
import com.ChaseHQ.Statistician.Database.DBSynchDataGetSet;
import com.ChaseHQ.Statistician.Database.DataValues.DBDataValues_Players;
import com.ChaseHQ.Statistician.Database.DataValues.DataStores;
import com.ChaseHQ.Statistician.Database.DataValues.DataValues_Config;
import com.ChaseHQ.Statistician.Stats.KillTag;
import com.ChaseHQ.Statistician.Utils.StringHandler;

public class EDHPlayer {
	public void PlayerBlockBreak(final Player player, final Integer blockID) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().addBlockBreak(player.getUniqueId().toString(), blockID);
			}
		});
	}

	public void PlayerBlockPlace(final Player player, final Integer blockID) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().addBlockPlaced(player.getUniqueId().toString(), blockID);
			}
		});
	}

	public void PlayerDroppedItem(final Player player, final Integer itemID, final Integer numberInStack) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().addItemDropped(player.getUniqueId().toString(), itemID, numberInStack);
			}
		});
	}

	public void PlayerJoin(final Player player) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				try {
					if (DBSynchDataGetSet.isPlayerInDB(player.getUniqueId().toString())) {
						// Player Exists and has been here before
						DBSynchDataGetSet.playerLogin(player.getUniqueId().toString());
						if (DataValues_Config.SHOW_LASTJOIN_WELCOME.getValueAsBoolean()) {
							// Show Last Joined Message
							String lastJoinMessage = StringHandler.formatForChat(DataValues_Config.LASTJOIN_WELCOME_MSG.getValue(), player);
							String timeStamp = DBSynchDataGetSet.getValue(DataStores.PLAYER, DBDataValues_Players.LAST_LOGOUT, DBDataValues_Players.UUID, player.getUniqueId().toString());
							String lastJoin = new SimpleDateFormat(DataValues_Config.DATE_FORMAT.getValue()).format(new Date(Long.parseLong(timeStamp) * 1000));
							lastJoinMessage = lastJoinMessage.replaceAll("\\{lastJoin}", lastJoin);
							player.sendMessage(lastJoinMessage);
						}
					} else {
						// First Time Player Logged in with Statistician Running
						DBSynchDataGetSet.playerCreate(player.getUniqueId().toString(), player.getName());
						if (DataValues_Config.SHOW_FIRSTJOIN_WELCOME.getValueAsBoolean()) {
							// If they want to show first join message
							player.sendMessage(StringHandler.formatForChat(DataValues_Config.FIRSTJOIN_WELCOME_MSG.getValue(), player));
						}
					}
					// Check and update the most users ever logged on
					StatisticianPlugin.getInstance().getDB().callStoredProcedure("updateMostEverOnline", null);
				} catch (NullPointerException e) {

				} finally {
					// Watch them
					StatisticianPlugin.getInstance().getPlayerData().addPlayerToWatch(player.getUniqueId().toString(), player.getLocation());
				}
			}
		});
	}

	public void PlayerMove(final Player player, final Class<? extends Vehicle> vehicleType) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().incrementStepsTaken(player.getUniqueId().toString(), player.getLocation(), vehicleType);
			}
		});
	}

	public void PlayerPickedUpItem(final Player player, final Integer itemID, final Integer numberInStack) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().addItemPickup(player.getUniqueId().toString(), itemID, numberInStack);
			}
		});
	}

	public void PlayerQuit(final Player player) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				DBSynchDataGetSet.playerLogout(player.getUniqueId().toString());
				// Unwatch Them
				StatisticianPlugin.getInstance().getPlayerData().removePlayerToWatch(player.getUniqueId().toString());
			}
		});
	}

	public void PlayerKilledByPlayer(final Player killer, final Player victim, final DamageCause cause) {
		if (!StatisticianPlugin.getInstance().permissionToRecordStat(victim)) return;
		this.execute(killer, new KillTag(killer, victim, cause));
	}

	public void PlayerKilledByPlayerProjectile(final Player killer, final Player victim, final Entity projectile, final DamageCause cause) {
		if (!StatisticianPlugin.getInstance().permissionToRecordStat(victim)) return;
		this.execute(killer, new KillTag(killer, victim, (Projectile)projectile, cause));
	}

	public void PlayerKilledByCreature(final Player victim, final Creature creature, final DamageCause cause) {
		this.execute(victim, new KillTag(creature, victim, cause));
	}

	public void PlayerKilledBySlime(final Player victim, final Slime creature, final DamageCause cause) {
		this.execute(victim, new KillTag(creature, victim, cause));
	}

	public void PlayerKilledByCreatureProjectile(final Player victim, final Creature creature, final Entity projectile, final DamageCause cause) {
		this.execute(victim, new KillTag(creature, victim, (Projectile)projectile, cause));
	}

	public void PlayerKilledCreature(final Player killer, final Creature creature, final DamageCause cause) {
		this.execute(killer, new KillTag(killer, creature, cause));
	}

	public void PlayerKilledSlime(final Player killer, final Slime creature, final DamageCause cause) {
		this.execute(killer, new KillTag(killer, creature, cause));
	}

	public void PlayerKilledCreatureProjectile(final Player killer, final Creature creature, final Entity projectile, final DamageCause cause) {
		this.execute(killer, new KillTag(killer, creature, (Projectile)projectile, cause));
	}

	public void PlayerKilledSlimeProjectile(final Player killer, final Slime creature, final Entity projectile, final DamageCause cause) {
		this.execute(killer, new KillTag(killer, creature, (Projectile)projectile, cause));
	}

	public void PlayerKilledByBlock(final Player victim, final Block block, final DamageCause cause) {
		this.execute(victim, new KillTag(block, victim, cause));
	}

	public void PlayerKilledByOtherCause(final Player victim, final DamageCause cause) {
		this.execute(victim, new KillTag(victim, cause));
	}

	private void execute(final Player player, final KillTag killTag) {
		this.execute(player, new Runnable() {
			@Override
			public void run() {
				StatisticianPlugin.getInstance().getPlayerData().addKillTag(player.getUniqueId().toString(), killTag);
			}
		});
	}

	private void execute(Player player, Runnable runnable) {
		if (!StatisticianPlugin.getInstance().permissionToRecordStat(player)) return;
		if (runnable != null) {
			StatisticianPlugin.getInstance().getExecutor().execute(runnable);
		}
	}
}
