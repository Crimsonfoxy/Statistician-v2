package com.ChaseHQ.Statistician.Database.DataValues;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import org.junit.Assert;
import org.junit.Test;

public class DBStaticValue_KillTypesTest {
	@Test
	public void testMapDamageCause() {
		// Verify we support all of the DamageCause's
		for (DamageCause cause : DamageCause.values()) {
			if (cause != DamageCause.PROJECTILE) { // Projectile is handled differently.
				Assert.assertNotSame(DBStaticValue_KillTypes.NONE, DBStaticValue_KillTypes.mapDamageCause(cause));
			}
		}
	}
}