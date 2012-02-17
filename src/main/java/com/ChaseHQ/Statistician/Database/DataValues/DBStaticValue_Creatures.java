package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

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

		if (creature instanceof Player)
			return DBStaticValue_Creatures.PLAYER;

		if (creature instanceof Chicken)
			return DBStaticValue_Creatures.CHICKEN;

		if (creature instanceof MushroomCow)
			return DBStaticValue_Creatures.MUSHROOMCOW;

		if (creature instanceof Cow)
			return DBStaticValue_Creatures.COW;

		if (creature instanceof Creeper) {
			if (((Creeper)creature).isPowered()) return DBStaticValue_Creatures.ELECTRIFIED_CREEPER;
			return DBStaticValue_Creatures.CREEPER;
		}

		if (creature instanceof Ghast)
			return DBStaticValue_Creatures.GHAST;

		if (creature instanceof Pig)
			return DBStaticValue_Creatures.PIG;

		if (creature instanceof PigZombie)
			return DBStaticValue_Creatures.PIG_ZOMBIE;

		if (creature instanceof Sheep)
			return DBStaticValue_Creatures.SHEEP;

		if (creature instanceof Skeleton) {
			if (((Skeleton)creature).isInsideVehicle()) return DBStaticValue_Creatures.SPIDER_JOCKY;
			return DBStaticValue_Creatures.SKELETON;
		}

		if (creature instanceof MagmaCube)
			return DBStaticValue_Creatures.MAGMACUBE;

		if (creature instanceof Slime)
			return DBStaticValue_Creatures.SLIME;

		if (creature instanceof Squid)
			return DBStaticValue_Creatures.SQUID;

		if (creature instanceof Wolf) {
			if (((Wolf)creature).isTamed()) return DBStaticValue_Creatures.TAME_WOLF;
			return DBStaticValue_Creatures.WOLF;
		}

		if (creature instanceof Zombie) return DBStaticValue_Creatures.ZOMBIE;

		if (creature instanceof CaveSpider)
			return DBStaticValue_Creatures.CAVESPIDER;

		if (creature instanceof Spider) return DBStaticValue_Creatures.SPIDER;

		if (creature instanceof Giant)
			return DBStaticValue_Creatures.GIANT;

		if (creature instanceof Blaze)
			return DBStaticValue_Creatures.BLAZE;

		if (creature instanceof EnderDragon)
			return DBStaticValue_Creatures.ENDERDRAGON;

		if (creature instanceof Enderman)
			return DBStaticValue_Creatures.ENDERMAN;

		if (creature instanceof Silverfish)
			return DBStaticValue_Creatures.SILVERFISH;

		if (creature instanceof Snowman)
			return DBStaticValue_Creatures.SNOWMAN;

		if (creature instanceof Villager)
			return DBStaticValue_Creatures.VILLAGER;

		if (creature instanceof Monster)
			return DBStaticValue_Creatures.MONSTER;

		return DBStaticValue_Creatures.NONE;
	}
}
