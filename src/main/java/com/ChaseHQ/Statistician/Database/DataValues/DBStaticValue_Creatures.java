package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wolf;

public enum DBStaticValue_Creatures implements IStaticValue {
	NONE(0),
	PLAYER(999),
	CHICKEN(1),
	COW(2),
	CREEPER(3),
	ELECTRIFIED_CREEPER(4),
	GHAST(5),
	GIANT(6),
	MONSTER(7),
	PIG(8),
	PIG_ZOMBIE(9),
	SHEEP(10),
	SKELETON(11),
	SLIME(12),
	SPIDER(13),
	SQUID(14),
	WOLF(15),
	TAME_WOLF(16),
	SPIDER_JOCKY(17),
	BLOCK(18),
	ZOMBIE(19),
	BLAZE(20),
	CAVESPIDER(21),
	ENDERDRAGON(22),
	ENDERMAN(23),
	MAGMACUBE(24),
	MUSHROOMCOW(25),
	SILVERFISH(26),
	SNOWMAN(27),
	VILLAGER(28);

	private final Integer id;

	private DBStaticValue_Creatures(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getID() {
		return this.id;
	}

	public static DBStaticValue_Creatures mapCreature(Creature creature) {
		switch (creature.getType()) {
			case PLAYER:
				return DBStaticValue_Creatures.PLAYER;
			case CHICKEN:
				return DBStaticValue_Creatures.CHICKEN;
			case MUSHROOM_COW:
				return DBStaticValue_Creatures.MUSHROOMCOW;
			case COW:
				return DBStaticValue_Creatures.COW;
			case CREEPER:
				if (((Creeper)creature).isPowered()) return DBStaticValue_Creatures.ELECTRIFIED_CREEPER;
				return DBStaticValue_Creatures.CREEPER;
			case GHAST:
				return DBStaticValue_Creatures.GHAST;
			case PIG:
				return DBStaticValue_Creatures.PIG;
			case PIG_ZOMBIE:
				return DBStaticValue_Creatures.PIG_ZOMBIE;
			case SHEEP:
				return DBStaticValue_Creatures.SHEEP;
			case SKELETON:
				if (((Skeleton)creature).isInsideVehicle()) return DBStaticValue_Creatures.SPIDER_JOCKY;
				return DBStaticValue_Creatures.SKELETON;
			case MAGMA_CUBE:
				return DBStaticValue_Creatures.MAGMACUBE;
			case SLIME:
				return DBStaticValue_Creatures.SLIME;
			case SQUID:
				return DBStaticValue_Creatures.SQUID;
			case WOLF:
				if (((Wolf)creature).isTamed()) return DBStaticValue_Creatures.TAME_WOLF;
				return DBStaticValue_Creatures.WOLF;
			case ZOMBIE:
				return DBStaticValue_Creatures.ZOMBIE;
			case CAVE_SPIDER:
				return DBStaticValue_Creatures.CAVESPIDER;
			case SPIDER:
				return DBStaticValue_Creatures.SPIDER;
			case GIANT:
				return DBStaticValue_Creatures.GIANT;
			case BLAZE:
				return DBStaticValue_Creatures.BLAZE;
			case ENDER_DRAGON:
				return DBStaticValue_Creatures.ENDERDRAGON;
			case ENDERMAN:
				return DBStaticValue_Creatures.ENDERMAN;
			case SILVERFISH:
				return DBStaticValue_Creatures.SILVERFISH;
			case SNOWMAN:
				return DBStaticValue_Creatures.SNOWMAN;
			case VILLAGER:
				return DBStaticValue_Creatures.VILLAGER;
		}
		if (creature instanceof Monster)
			return DBStaticValue_Creatures.MONSTER;

		return DBStaticValue_Creatures.NONE;
	}
}
