package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DBStaticValue_CreaturesTest {
	@Test
	public void testMapCreature() {
		// Verify we support all of the creature's
		for (EntityType entityType : EntityType.values()) {
			if (entityType == EntityType.UNKNOWN || !Creature.class.isAssignableFrom(entityType.getEntityClass())) {
				continue;
			}
			Creature mockCreature = (Creature)Mockito.mock(entityType.getEntityClass());
			Mockito.when(mockCreature.getType()).thenReturn(entityType);
			Assert.assertNotSame(DBStaticValue_Creatures.NONE, DBStaticValue_Creatures.mapCreature(mockCreature));
		}
	}
}