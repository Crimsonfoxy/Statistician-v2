package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
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
import org.bukkit.entity.Weather;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityDeathEvent.class, EntityDamageEvent.class, EntityDamageByEntityEvent.class, EntityDamageByBlockEvent.class})
public class EntityListenerTest {
	private EDHPlayer passedEDH;
	private EntityListener entityListener;

	/* Blaze.class, // fireballCreature
	 * CaveSpider.class, // meleeCreature
	 * Chicken.class, // passive
	 * Cow.class, // passive
	 * Creeper.class, // explosiveCreature
	 * EnderDragon.class, // meleeCreature
	 * Enderman.class, // meleeCreature
	 * Ghast.class, // fireballCreature
	 * MagmaCube.class, // meleeCreature
	 * MushroomCow.class, // passive
	 * Pig.class, // passive
	 * PigZombie.class, // meleeCreature
	 * Sheep.class, // passive
	 * Silverfish.class, // meleeCreature
	 * Skeleton.class, // arrowCreature
	 * Slime.class, // meleeCreature
	 * Snowman.class, // snowballPassive
	 * Spider.class, // meleeCreature
	 * Squid.class, // passive
	 * Villager.class, // passive
	 * Wolf.class, // meleeCreature
	 * Zombie.class // meleeCreature
	 */

	private static final Class<?>[] meleeCreatureClasses = new Class<?>[] {
		CaveSpider.class,
		EnderDragon.class,
		Enderman.class,
		MagmaCube.class,
		PigZombie.class,
		Silverfish.class,
		Slime.class,
		Spider.class,
		Wolf.class,
		Zombie.class
	};

	private static final Class<?>[] passiveClasses = new Class<?>[] {
		Chicken.class,
		Cow.class,
		MushroomCow.class,
		Pig.class,
		Sheep.class,
		Squid.class,
		Villager.class
	};

	private static final Class<?>[] arrowCreatureClasses = new Class<?>[] {
		Skeleton.class
	};

	private static final Class<?>[] fireballCreatureClasses = new Class<?>[] {
		Blaze.class,
		Ghast.class
	};

	private static final Class<?>[] explosiveCreatureClasses = new Class<?>[] {
		Creeper.class
	};

	private static final Class<?>[] snowballPassiveClasses = new Class<?>[] {
		Snowman.class
	};

	private static final Class<?>[] creatureClasses = new Class<?>[] {
		Blaze.class,
		CaveSpider.class,
		Chicken.class,
		Cow.class,
		Creeper.class,
		//EnderDragon.class, // Not a creature
		Enderman.class,
		//Ghast.class, // Not a creature
		//MagmaCube.class, // Not a creature
		MushroomCow.class,
		Pig.class,
		PigZombie.class,
		Sheep.class,
		Silverfish.class,
		Skeleton.class,
		//Slime.class, // Not a creature
		Snowman.class,
		Spider.class,
		Squid.class,
		Villager.class,
		Wolf.class,
		Zombie.class
	};
	
	@Before
	public void setUp() throws Exception {
		this.passedEDH = Mockito.mock(EDHPlayer.class);
		this.entityListener = new EntityListener(this.passedEDH);
	}

	@Test
	public void testOnEntityDeathEntityDeathEvent() {
		Player mockPlayer = Mockito.mock(Player.class);
		
		Slime mockSlime = Mockito.mock(Slime.class);

		Arrow mockArrow = Mockito.mock(Arrow.class);
		
		Block mockBlock = Mockito.mock(Block.class);
		
		Weather mockWeather = Mockito.mock(Weather.class);

		// BLOCK DAMAGE

		//BlockCactus.java: new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.CONTACT, 1)
		verifyEntityDamageByBlockEvent(mockBlock, mockPlayer, DamageCause.CONTACT, 1);

		//Entity.java: new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.LAVA, 4);
		verifyEntityDamageByBlockEvent(mockBlock, mockPlayer, DamageCause.LAVA, 4);

		//EntityLiving.java: new EntityDamageByBlockEvent(null, this.getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, 4);
		verifyEntityDamageByBlockEvent(mockBlock, mockPlayer, DamageCause.VOID, 4);

		//Explosion.java: new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
		verifyEntityDamageByBlockEvent(null, mockPlayer, DamageCause.BLOCK_EXPLOSION, 1);

		// WEATHER DAMAGE
		
		//Entity.java: new EntityDamageByEntityEvent(entityweatherstorm.getBukkitEntity(), this.getBukkitEntity(), EntityDamageEvent.DamageCause.LIGHTNING, 5);
		verifyEntityDamageByEntityEvent(mockWeather, mockPlayer, DamageCause.LIGHTNING, 5);

		// PROJECTILE DAMAGE

		//EntityArrow.java: new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 4);
		for (Class<?> entityClass : EntityListenerTest.arrowCreatureClasses) {
			LivingEntity mockLivingEntity = (LivingEntity)Mockito.mock(entityClass);
			Mockito.when(mockArrow.getShooter()).thenReturn(mockLivingEntity);
			verifyEntityDamageByEntityEvent(mockArrow, mockPlayer, DamageCause.PROJECTILE, 4);
		}
		Mockito.when(mockArrow.getShooter()).thenReturn(mockPlayer);
		verifyEntityDamageByEntityEvent(mockArrow, mockPlayer, DamageCause.PROJECTILE, 4);

		for (Class<?> creatureClass : EntityListenerTest.creatureClasses) {
			Creature mockCreature = (Creature)Mockito.mock(creatureClass);
			Mockito.when(mockArrow.getShooter()).thenReturn(mockPlayer);
			verifyEntityDamageByEntityEvent(mockArrow, mockCreature, DamageCause.PROJECTILE, 4);
		}
		Mockito.when(mockArrow.getShooter()).thenReturn(mockPlayer);
		verifyEntityDamageByEntityEvent(mockArrow, mockSlime, DamageCause.PROJECTILE, 4);
		
		// Egg, Fireball, Fish, and Snowball don't cause damage. So, don't test them.
		//EntityEgg.java: new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 0);
		/*Egg mockEgg = Mockito.mock(Egg.class);
		Mockito.when(mockEgg.getShooter()).thenReturn(mockPlayer);
		verifyEntityDamageByEntityEvent(mockEgg, mockPlayer, DamageCause.PROJECTILE, 0);*/

		//EntityFireball.java: new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 0);
		/*Fireball mockFireball = Mockito.mock(Fireball.class);
		for (Class<?> entityClass : StatisticianEntityListenerTest.fireballCreatureClasses) {
			Mockito.when(mockFireball.getShooter()).thenReturn((LivingEntity)Mockito.mock(entityClass));
			verifyEntityDamageByEntityEvent(mockFireball, mockPlayer, DamageCause.PROJECTILE, 0);
		}*/
		
		//EntityFish.java: new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 0);
		/*Fish mockFish = Mockito.mock(Fish.class);
		Mockito.when(mockFish.getShooter()).thenReturn(mockPlayer);
		verifyEntityDamageByEntityEvent(mockFish, mockPlayer, DamageCause.PROJECTILE, 0);*/

		//EntitySnowball.java: new EntityDamageByEntityEvent(projectile, damagee, EntityDamageEvent.DamageCause.PROJECTILE, 0);
		/*Snowball mockSnowball = Mockito.mock(Snowball.class);
		for (Class<?> entityClass : StatisticianEntityListenerTest.snowballPassiveClasses) {
			Mockito.when(mockFireball.getShooter()).thenReturn((LivingEntity)Mockito.mock(entityClass));
			verifyEntityDamageByEntityEvent(mockSnowball, mockPlayer, DamageCause.PROJECTILE, 0);
		}
		Mockito.when(mockSnowball.getShooter()).thenReturn(mockPlayer);
		verifyEntityDamageByEntityEvent(mockSnowball, mockPlayer, DamageCause.PROJECTILE, 0);*/

		// ENTITY DAMAGE

		//EntityHuman.java: new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, i);
		//EntityMonster.java: new EntityDamageByEntityEvent(this.getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, this.damage);
		//EntityWolf.java: new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, b0);
		for (Class<?> entityClass : EntityListenerTest.meleeCreatureClasses) {
			LivingEntity mockLivingEntity = (LivingEntity)Mockito.mock(entityClass);
			verifyEntityDamageByEntityEvent(mockLivingEntity, mockPlayer, DamageCause.ENTITY_ATTACK, 1);
		}
		verifyEntityDamageByEntityEvent(mockPlayer, mockPlayer, DamageCause.ENTITY_ATTACK, 1);

		for (Class<?> creatureClass : EntityListenerTest.creatureClasses) {
			Creature mockCreature = (Creature)Mockito.mock(creatureClass);
			verifyEntityDamageByEntityEvent(mockPlayer, mockCreature, DamageCause.ENTITY_ATTACK, 1);
		}
		verifyEntityDamageByEntityEvent(mockPlayer, mockSlime, DamageCause.ENTITY_ATTACK, 1);


		//Explosion.java: new EntityDamageByEntityEvent(this.source.getBukkitEntity(), damagee, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damageDone);
		for (Class<?> creatureClass : EntityListenerTest.explosiveCreatureClasses) {
			Creature mockCreature = (Creature)Mockito.mock(creatureClass);
			verifyEntityDamageByEntityEvent(mockCreature, mockPlayer, DamageCause.ENTITY_EXPLOSION, 1);
		}

		// OTHER DAMAGE
		
		//Entity.java: new EntityDamageEvent(damagee, EntityDamageEvent.DamageCause.FIRE_TICK, 1);
		verifyEntityDamageEvent(mockPlayer, DamageCause.FIRE_TICK, 1);
		//Entity.java: new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE, i);
		verifyEntityDamageEvent(mockPlayer, DamageCause.FIRE, 1);
		//EntityLiving.java: new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.SUFFOCATION, 1);
		verifyEntityDamageEvent(mockPlayer, DamageCause.SUFFOCATION, 1);
		//EntityLiving.java: new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.DROWNING, 2);
		verifyEntityDamageEvent(mockPlayer, DamageCause.DROWNING, 2);
		//EntityLiving.java: new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FALL, i);
		verifyEntityDamageEvent(mockPlayer, DamageCause.FALL, 1);
	}

	private void verifyEntityDamageByBlockEvent(Block damager, Player damagee, DamageCause cause, int damage) {
		EntityDamageByBlockEvent mockDmgByBlockEvent = PowerMockito.mock(EntityDamageByBlockEvent.class);
		Mockito.when(mockDmgByBlockEvent.getDamager()).thenReturn(damager);
		Mockito.when(mockDmgByBlockEvent.getEntity()).thenReturn(damagee);
		Mockito.when(mockDmgByBlockEvent.getCause()).thenReturn(cause);
		Mockito.when(mockDmgByBlockEvent.getDamage()).thenReturn(damage);

		Mockito.when(damagee.getLastDamageCause()).thenReturn(mockDmgByBlockEvent);

		EntityDeathEvent mockEvent = PowerMockito.mock(EntityDeathEvent.class);
		Mockito.when(mockEvent.getEntity()).thenReturn(damagee);
	    this.entityListener.onEntityDeath(mockEvent);
		Mockito.verify(this.passedEDH).PlayerKilledByBlock(damagee, damager, cause);
	}

	private void verifyEntityDamageByEntityEvent(Entity damager, Entity damagee, DamageCause cause, int damage) {
		EntityDamageByEntityEvent mockDmgByEntityEvent = PowerMockito.mock(EntityDamageByEntityEvent.class);
		Mockito.when(mockDmgByEntityEvent.getDamager()).thenReturn(damager);
		Mockito.when(mockDmgByEntityEvent.getEntity()).thenReturn(damagee);
		Mockito.when(mockDmgByEntityEvent.getCause()).thenReturn(cause);
		Mockito.when(mockDmgByEntityEvent.getDamage()).thenReturn(damage);

		Mockito.when(damagee.getLastDamageCause()).thenReturn(mockDmgByEntityEvent);

		EntityDeathEvent mockEvent = PowerMockito.mock(EntityDeathEvent.class);
		Mockito.when(mockEvent.getEntity()).thenReturn(damagee);
	    this.entityListener.onEntityDeath(mockEvent);
	    if (damagee instanceof Player) {
		    if (damager instanceof Slime) {
		    	Mockito.verify(this.passedEDH).PlayerKilledBySlime((Player)damagee, (Slime)damager, cause);
		    } else if (damager instanceof Creature) {
			    Mockito.verify(this.passedEDH).PlayerKilledByCreature((Player)damagee, (Creature)damager, cause);
		    } else if (damager instanceof Player) {
			    Mockito.verify(this.passedEDH).PlayerKilledByPlayer((Player)damager, (Player)damagee, cause);
		    } else if (damager instanceof Arrow) {
		    	Arrow arrow = ((Arrow)damager);
		    	LivingEntity shooter = arrow.getShooter();
		    	if (shooter instanceof Player) {
		    		Mockito.verify(this.passedEDH).PlayerKilledByPlayerProjectile((Player)shooter, (Player)damagee, arrow, cause);
		    	} else if (shooter instanceof Creature) {
		    		Mockito.verify(this.passedEDH).PlayerKilledByCreatureProjectile((Player)damagee, (Creature)shooter, arrow, cause);
		    	} else {
		    		Assert.fail("Invalid test");
		    	}
		    }
	    } else if (damager instanceof Player) {
	    	if (damagee instanceof Creature) {
	    		Mockito.verify(this.passedEDH).PlayerKilledCreature((Player)damager, (Creature)damagee, cause);
	    	} else if (damagee instanceof Slime) {
	    		Mockito.verify(this.passedEDH).PlayerKilledSlime((Player)damager, (Slime)damagee, cause);
	    	} else {
	    		Assert.fail("Invalid test");
	    	}
	    } else if (damager instanceof Arrow) {
	    	Arrow arrow = ((Arrow)damager);
	    	LivingEntity shooter = arrow.getShooter();
	    	if (shooter instanceof Player) {
	    		if (damagee instanceof Creature) {
	    			Mockito.verify(this.passedEDH).PlayerKilledCreatureProjectile((Player)shooter, (Creature)damagee, arrow, cause);
	    		} else if (damagee instanceof Slime) {
	    			Mockito.verify(this.passedEDH).PlayerKilledSlimeProjectile((Player)shooter, (Slime)damagee, arrow, cause);
	    		} else {
		    		Assert.fail("Invalid test");
		    	}
	    	} else {
	    		Assert.fail("Invalid test");
	    	}
	    } else {
    		Assert.fail("Invalid test");
    	}
	}

	private void verifyEntityDamageEvent(Player damagee, DamageCause cause, int damage) {
		EntityDamageEvent mockEntityDamageEvent = PowerMockito.mock(EntityDamageEvent.class);
		Mockito.when(mockEntityDamageEvent.getEntity()).thenReturn(damagee);
		Mockito.when(mockEntityDamageEvent.getCause()).thenReturn(cause);
		Mockito.when(mockEntityDamageEvent.getDamage()).thenReturn(damage);

		Mockito.when(damagee.getLastDamageCause()).thenReturn(mockEntityDamageEvent);

		EntityDeathEvent mockEvent = PowerMockito.mock(EntityDeathEvent.class);
		Mockito.when(mockEvent.getEntity()).thenReturn(damagee);

	    this.entityListener.onEntityDeath(mockEvent);
		Mockito.verify(this.passedEDH).PlayerKilledByOtherCause(damagee, cause);
	}
}