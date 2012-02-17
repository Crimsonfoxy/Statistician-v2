package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;

public enum DBStaticValue_Projectiles implements IStaticValue {
	NONE(0),
	ARROW(1);

	private final Integer id;

	private DBStaticValue_Projectiles(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	public static DBStaticValue_Projectiles mapProjectile(Entity projectile) {

		if (projectile instanceof Arrow)
			return DBStaticValue_Projectiles.ARROW;

		return DBStaticValue_Projectiles.NONE;
	}
}
