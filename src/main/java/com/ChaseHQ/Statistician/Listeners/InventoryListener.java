package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.event.Listener;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

//Commented imports, for now, so Spout dependency is unneeded until this is used.
//import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
//public class InventoryListener extends org.getspout.spoutapi.event.inventory.InventoryListener {

public class InventoryListener implements Listener {
	private EDHPlayer edhPlayer;

	public InventoryListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	/*@Override
	public void onInventoryCraft(InventoryCraftEvent event) {}*/
}
