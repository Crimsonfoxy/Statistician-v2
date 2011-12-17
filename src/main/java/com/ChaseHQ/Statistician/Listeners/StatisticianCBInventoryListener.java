package com.ChaseHQ.Statistician.Listeners;

// Commented imports, for now, so Spout dependency is unneeded until this is used.
//import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
//import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.bukkit.event.inventory.InventoryListener;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class StatisticianCBInventoryListener extends InventoryListener {
	
	private EDHPlayer edhPlayer;
	
	public StatisticianCBInventoryListener(EDHPlayer passedEDH) {
		edhPlayer = passedEDH;
	}
	
	/*@Override
	public void onInventoryCraft(InventoryCraftEvent event) {
		
	}*/
}


