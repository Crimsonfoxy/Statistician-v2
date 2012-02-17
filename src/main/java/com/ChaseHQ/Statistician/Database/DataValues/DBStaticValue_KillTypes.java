package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public enum DBStaticValue_KillTypes implements IStaticValue {
	NONE(0),
	BLOCK_EXPLOSION(1),
	DROWNING(2),
	ENTITY_EXPLOSION(3),
	FALL(4),
	FIRE(5),
	FIRE_TICK(6),
	VOID(7),
	SUFFOCATION(8),
	LIGHTENING(9),
	LAVA(10),
	CONTACT(11),
	ENTITY_ATTACK(12),
	CUSTOM(13),
	SUICIDE(14),
	STARVATION(15),
	POISON(16),
	MAGIC(17);

	private final Integer id;

	private DBStaticValue_KillTypes(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	public static DBStaticValue_KillTypes mapDamageCause(DamageCause cause) {

		switch (cause) {
			case BLOCK_EXPLOSION:
				return DBStaticValue_KillTypes.BLOCK_EXPLOSION;
			case DROWNING:
				return DBStaticValue_KillTypes.DROWNING;
			case ENTITY_EXPLOSION:
				return DBStaticValue_KillTypes.ENTITY_EXPLOSION;
			case FALL:
				return DBStaticValue_KillTypes.FALL;
			case FIRE:
				return DBStaticValue_KillTypes.FIRE;
			case FIRE_TICK:
				return DBStaticValue_KillTypes.FIRE_TICK;
			case VOID:
				return DBStaticValue_KillTypes.VOID;
			case SUFFOCATION:
				return DBStaticValue_KillTypes.SUFFOCATION;
			case LIGHTNING:
				return DBStaticValue_KillTypes.LIGHTENING;
			case LAVA:
				return DBStaticValue_KillTypes.LAVA;
			case CONTACT:
				return DBStaticValue_KillTypes.CONTACT;
			case ENTITY_ATTACK:
				return DBStaticValue_KillTypes.ENTITY_ATTACK;
			case CUSTOM:
				return DBStaticValue_KillTypes.CUSTOM;
			case SUICIDE:
				return DBStaticValue_KillTypes.SUICIDE;
			case STARVATION:
				return DBStaticValue_KillTypes.STARVATION;
			case POISON:
				return DBStaticValue_KillTypes.POISON;
			case MAGIC:
				return DBStaticValue_KillTypes.MAGIC;
		}

		return DBStaticValue_KillTypes.NONE;
	}
}
