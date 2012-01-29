package com.ChaseHQ.Statistician.Stats;

import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ChaseHQ.Statistician.Database.DataValues.DBStaticValue_Creatures;
import com.ChaseHQ.Statistician.Database.DataValues.DBStaticValue_KillTypes;
import com.ChaseHQ.Statistician.Database.DataValues.DBStaticValue_Projectiles;

public class KillTag {
	public DBStaticValue_Creatures Killed = DBStaticValue_Creatures.NONE;
	public DBStaticValue_Creatures KilledBy = DBStaticValue_Creatures.NONE;
	public DBStaticValue_KillTypes KillType = DBStaticValue_KillTypes.NONE;
	public Integer KilledUsing = -1;
	public DBStaticValue_Projectiles KillProjectile = DBStaticValue_Projectiles.NONE;
	public String KilledBy_UUID = "";
	public String Killed_UUID = "";

	public KillTag() {}

	/**
	 * Block killed a player.
	 * 
	 * @param killer The block.
	 * @param victim The player.
	 */
	public KillTag(Block killer, Player victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.KilledBy = DBStaticValue_Creatures.BLOCK;
		if (killer != null) {
			this.KilledUsing = killer.getTypeId();
		}
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
	}

	/**
	 * Creature killed a player.
	 * 
	 * @param killer The creature.
	 * @param victim The player.
	 */
	public KillTag(Creature killer, Player victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KilledBy = DBStaticValue_Creatures.mapCreature(killer);
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
	}

	/**
	 * Creature killed a player using a projectile.
	 * 
	 * @param killer The creature.
	 * @param victim The player.
	 */
	public KillTag(Creature killer, Player victim, Projectile projectile, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KilledBy = DBStaticValue_Creatures.mapCreature(killer);
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KillProjectile = DBStaticValue_Projectiles.mapProjectile(projectile);
	}

	/**
	 * Something killed a player.
	 * 
	 * @param victim The player.
	 */
	public KillTag(Player victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
	}

	/**
	 * Player killed a player.
	 * 
	 * @param killer The player that killed the other player.
	 * @param victim The player that died.
	 */
	public KillTag(Player killer, Player victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KilledUsing = killer.getItemInHand().getTypeId();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}

	/**
	 * Player killed a player using a projectile.
	 * 
	 * @param killer The player that killed the other player.
	 * @param victim The player that died.
	 */
	public KillTag(Player killer, Player victim, Projectile projectile, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KilledUsing = killer.getItemInHand().getTypeId();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KillProjectile = DBStaticValue_Projectiles.mapProjectile(projectile);

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}

	/**
	 * Slime killed a player.
	 * 
	 * @param killer The creature.
	 * @param victim The player.
	 */
	public KillTag(Slime killer, Player victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.PLAYER;
		this.Killed_UUID = victim.getUniqueId().toString();
		this.KilledBy = DBStaticValue_Creatures.SLIME;
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
	}

	/**
	 * Player killed a creature.
	 * 
	 * @param killer
	 * @param victim
	 * @param damageCause
	 */
	public KillTag(Player killer, Creature victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.mapCreature(victim);
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KilledUsing = killer.getItemInHand().getTypeId();

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}

	/**
	 * Player killed a creature using a projectile.
	 * 
	 * @param killer
	 * @param victim
	 * @param damageCause
	 */
	public KillTag(Player killer, Creature victim, Projectile projectile, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.mapCreature(victim);
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KillProjectile = DBStaticValue_Projectiles.mapProjectile(projectile);
		this.KilledUsing = killer.getItemInHand().getTypeId();

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}

	/**
	 * Player killed a slime.
	 * 
	 * @param killer
	 * @param victim
	 * @param damageCause
	 */
	public KillTag(Player killer, Slime victim, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.SLIME;
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KilledUsing = killer.getItemInHand().getTypeId();

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}

	/**
	 * Player killed a slime using a projectile.
	 * 
	 * @param killer
	 * @param victim
	 * @param damageCause
	 */
	public KillTag(Player killer, Slime victim, Projectile projectile, DamageCause cause) {
		this.Killed = DBStaticValue_Creatures.SLIME;
		this.KilledBy = DBStaticValue_Creatures.PLAYER;
		this.KilledBy_UUID = killer.getUniqueId().toString();
		this.KillType = DBStaticValue_KillTypes.mapDamageCause(cause);
		this.KillProjectile = DBStaticValue_Projectiles.mapProjectile(projectile);
		this.KilledUsing = killer.getItemInHand().getTypeId();

		if (this.KilledUsing == 0) {
			// Map it to Hand instead of air
			this.KilledUsing = 9001;
		}
	}
}
