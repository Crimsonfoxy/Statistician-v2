package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

public class DBStaticValue_CreaturesTest {
	@Test
	public void testMapCreature() {
		// Verify we support all of the creature's
		for (CreatureType creatureType : CreatureType.values()) {
			switch (creatureType) {
				case GHAST:
				case SLIME:
				case ENDER_DRAGON:
				case MAGMA_CUBE:
					continue;
			}
			Creature mockCreature = (Creature)Mockito.mock(creatureType.getEntityClass());
			Assert.assertNotSame(DBStaticValue_Creatures.NONE, DBStaticValue_Creatures.mapCreature(mockCreature));
		}
	}
}