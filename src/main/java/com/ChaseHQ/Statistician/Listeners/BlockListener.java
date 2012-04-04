package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class BlockListener implements Listener {
	private EDHPlayer edhPlayer;

	public BlockListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		this.edhPlayer.PlayerBlockBreak(event.getPlayer(), event.getBlock().getTypeId());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() != Material.AIR) {
			this.edhPlayer.PlayerBlockPlace(event.getPlayer(), event.getBlock().getTypeId());
		}
	}
}
