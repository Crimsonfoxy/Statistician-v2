package com.ChaseHQ.Statistician.Listeners;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

//Commented imports, for now, so Spout dependency is unneeded until this is used.
//import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
//public class InventoryListener extends org.getspout.spoutapi.event.inventory.InventoryListener {

public class InventoryListener extends org.bukkit.event.inventory.InventoryListener {
	private EDHPlayer edhPlayer;

	public InventoryListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	/*@Override
	public void onInventoryCraft(InventoryCraftEvent event) {
		
	}*/
}
