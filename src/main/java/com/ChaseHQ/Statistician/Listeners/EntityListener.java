package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class EntityListener implements Listener {
	private EDHPlayer edhPlayer;

	public EntityListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		EntityDamageEvent lastDamageEvent = entity.getLastDamageCause();
		if (lastDamageEvent != null) {
			DamageCause cause = lastDamageEvent.getCause();

			if (entity instanceof Player) {
				Player victim = (Player)entity;
				if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
					Entity damager = ((EntityDamageByEntityEvent)lastDamageEvent).getDamager();

					if (damager instanceof Arrow) {
						Arrow arrow = (Arrow)damager;
						if (arrow.getShooter() instanceof Player) {
							this.edhPlayer.PlayerKilledByPlayerProjectile((Player)arrow.getShooter(), victim, arrow, cause);
						} else if (arrow.getShooter() instanceof Creature) {
							this.edhPlayer.PlayerKilledByCreatureProjectile(victim, (Creature)arrow.getShooter(), arrow, cause);
						}
					} else if (damager instanceof Player) {
						this.edhPlayer.PlayerKilledByPlayer((Player)damager, victim, cause);
					} else if (damager instanceof Explosive) {
						this.edhPlayer.PlayerKilledByOtherCause(victim, cause);
					} else if (damager instanceof Creature) {
						this.edhPlayer.PlayerKilledByCreature(victim, (Creature)damager, cause);
					} else if (damager instanceof Slime) {
						this.edhPlayer.PlayerKilledBySlime(victim, (Slime)damager, cause);
					}
				} else if (lastDamageEvent instanceof EntityDamageByBlockEvent) {
					this.edhPlayer.PlayerKilledByBlock(victim, ((EntityDamageByBlockEvent)lastDamageEvent).getDamager(), cause);
				} else {
					this.edhPlayer.PlayerKilledByOtherCause(victim, cause);
				}
			} else {
				if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
					Entity damager = ((EntityDamageByEntityEvent)lastDamageEvent).getDamager();

					if (damager instanceof Arrow) {
						Arrow arrow = (Arrow)damager;
						if (arrow.getShooter() instanceof Player) {
							Player playerKiller = (Player)arrow.getShooter();
							if (entity instanceof Creature) {
								this.edhPlayer.PlayerKilledCreatureProjectile(playerKiller, (Creature)entity, arrow, cause);
							} else if (entity instanceof Slime) {
								this.edhPlayer.PlayerKilledSlimeProjectile(playerKiller, (Slime)entity, arrow, cause);
							}
						}
					} else if (damager instanceof Player) {
						Player playerKiller = (Player)damager;
						if (entity instanceof Creature) {
							this.edhPlayer.PlayerKilledCreature(playerKiller, (Creature)entity, cause);
						} else if (entity instanceof Slime) {
							this.edhPlayer.PlayerKilledSlime(playerKiller, (Slime)entity, cause);
						}
					}
				}
			}
		}
	}
}
