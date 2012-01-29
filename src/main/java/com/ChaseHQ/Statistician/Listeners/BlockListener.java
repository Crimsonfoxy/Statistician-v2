package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

public class BlockListener extends org.bukkit.event.block.BlockListener {
	private EDHPlayer edhPlayer;

	public BlockListener(EDHPlayer passedEDH) {
		this.edhPlayer = passedEDH;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		this.edhPlayer.PlayerBlockBreak(event.getPlayer(), event.getBlock().getTypeId());
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() != Material.AIR) {
			this.edhPlayer.PlayerBlockPlace(event.getPlayer(), event.getBlock().getTypeId());
		}
	}
}
