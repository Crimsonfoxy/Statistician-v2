package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class StatisticianEntityListener extends EntityListener{
	private EDHPlayer edhPlayer;
	
	public StatisticianEntityListener(EDHPlayer passedEDH) {
		edhPlayer = passedEDH;
	}
	
	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		Player playerVictim = null;
		Player playerKiller = null;

		// Temporary Enderman fix...
		if (event.getEntity() instanceof Enderman) {
			if (event.getEntity().getLastDamageCause() == null) {
				// Enderman died from contact with water.
				event.getEntity().setLastDamageCause(new EntityDamageEvent(event.getEntity(), DamageCause.CONTACT, 1));
			}
		}

		DamageCause cause = event.getEntity().getLastDamageCause().getCause();
		
		if (event.getEntity() instanceof Player) {
			playerVictim = (Player)event.getEntity();
			if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
					if (arrow.getShooter() instanceof Player) {
						edhPlayer.PlayerKilledByPlayerProjectile((Player)arrow.getShooter(), playerVictim, arrow, cause);
					} else if (arrow.getShooter() instanceof Creature) {
						edhPlayer.PlayerKilledByCreatureProjectile(playerVictim, (Creature)arrow.getShooter(), arrow, cause);
					}
				} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Player) {
					playerKiller = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
					edhPlayer.PlayerKilledByPlayer(playerKiller, playerVictim, cause);
				} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Explosive) {
					edhPlayer.PlayerKilledByOtherCause(playerVictim, event.getEntity().getLastDamageCause().getCause());
				} else {
					if (((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof Creature) {
						edhPlayer.PlayerKilledByCreature(playerVictim, (Creature) ((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager(),cause);
					} else if (((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof Slime) {
						// I really cant believe Creatures arent considered slimes, this has to be a bug
						edhPlayer.PlayerKilledBySlime(playerVictim, (Slime) ((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager(),cause);
					}
				}
			} else if (event.getEntity().getLastDamageCause() instanceof EntityDamageByBlockEvent) {
				edhPlayer.PlayerKilledByBlock(playerVictim, ((EntityDamageByBlockEvent)event.getEntity().getLastDamageCause()).getDamager(),cause);
			} else {
				edhPlayer.PlayerKilledByOtherCause(playerVictim, event.getEntity().getLastDamageCause().getCause());
			}
		} else {
			if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow)((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager();
					playerKiller = (Player)arrow.getShooter();
					if (event.getEntity() instanceof Creature) {
						edhPlayer.PlayerKilledCreatureProjectile(playerKiller, (Creature)event.getEntity(), arrow, cause);
					} else if (event.getEntity() instanceof Slime) {
						// I really cant believe Creatures arent considered slimes, this has to be a bug
						edhPlayer.PlayerKilledSlimeProjectile(playerKiller, (Slime)event.getEntity(), arrow, cause);
					}
				} else if (((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Player) {

					playerKiller = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
					if (event.getEntity() instanceof Creature) {
						edhPlayer.PlayerKilledCreature(playerKiller, (Creature)event.getEntity(), cause);
					} else if (event.getEntity() instanceof Slime) {
						// I really cant believe Creatures arent considered slimes, this has to be a bug
						edhPlayer.PlayerKilledSlime(playerKiller, (Slime)event.getEntity(), cause);
					}
				} 
			} 
		}
	}
}
