package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class EntityListener extends org.bukkit.event.entity.EntityListener {
	private EDHPlayer edhPlayer;

	public EntityListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		// TODO Clean this up
		if (event.getEntity().getLastDamageCause() != null) {
			Player playerVictim = null;
			Player playerKiller = null;

			DamageCause cause = event.getEntity().getLastDamageCause().getCause();

			if (event.getEntity() instanceof Player) {
				playerVictim = (Player)event.getEntity();
				if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
					if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
						if (arrow.getShooter() instanceof Player) {
							this.edhPlayer.PlayerKilledByPlayerProjectile((Player)arrow.getShooter(), playerVictim, arrow, cause);
						} else if (arrow.getShooter() instanceof Creature) {
							this.edhPlayer.PlayerKilledByCreatureProjectile(playerVictim, (Creature)arrow.getShooter(), arrow, cause);
						}
					} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Player) {
						playerKiller = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
						this.edhPlayer.PlayerKilledByPlayer(playerKiller, playerVictim, cause);
					} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Explosive) {
						this.edhPlayer.PlayerKilledByOtherCause(playerVictim, event.getEntity().getLastDamageCause().getCause());
					} else {
						if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Creature) {
							this.edhPlayer.PlayerKilledByCreature(playerVictim, (Creature)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager(), cause);
						} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Slime) {
							// I really cant believe Creatures arent considered slimes, this has to be a bug
							this.edhPlayer.PlayerKilledBySlime(playerVictim, (Slime)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager(), cause);
						}
					}
				} else if (event.getEntity().getLastDamageCause() instanceof EntityDamageByBlockEvent) {
					this.edhPlayer.PlayerKilledByBlock(playerVictim, ((EntityDamageByBlockEvent)event.getEntity().getLastDamageCause()).getDamager(), cause);
				} else {
					this.edhPlayer.PlayerKilledByOtherCause(playerVictim, event.getEntity().getLastDamageCause().getCause());
				}
			} else {
				if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
					if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Arrow) {
						Arrow arrow = (Arrow)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
						if (arrow.getShooter() instanceof Player) {
							playerKiller = (Player)arrow.getShooter();
							if (event.getEntity() instanceof Creature) {
								this.edhPlayer.PlayerKilledCreatureProjectile(playerKiller, (Creature)event.getEntity(), arrow, cause);
							} else if (event.getEntity() instanceof Slime) {
								// I really cant believe Creatures arent considered slimes, this has to be a bug
								this.edhPlayer.PlayerKilledSlimeProjectile(playerKiller, (Slime)event.getEntity(), arrow, cause);
							}
						}
					} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Player) {

						playerKiller = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
						if (event.getEntity() instanceof Creature) {
							this.edhPlayer.PlayerKilledCreature(playerKiller, (Creature)event.getEntity(), cause);
						} else if (event.getEntity() instanceof Slime) {
							// I really cant believe Creatures arent considered slimes, this has to be a bug
							this.edhPlayer.PlayerKilledSlime(playerKiller, (Slime)event.getEntity(), cause);
						}
					}
				}
			}
		}
	}
}
