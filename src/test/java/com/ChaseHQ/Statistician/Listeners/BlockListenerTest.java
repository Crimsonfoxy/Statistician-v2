package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BlockBreakEvent.class, BlockPlaceEvent.class})
public class BlockListenerTest {
	private EDHPlayer passedEDH;
	private BlockListener blockListener;
	
	@Before
	public void setUp() throws Exception {
		this.passedEDH = Mockito.mock(EDHPlayer.class);
		this.blockListener = new BlockListener(this.passedEDH);
	}
	
	@Test
	public void testOnBlockBreakEvent() {
        Player mockPlayer = Mockito.mock(Player.class);
		Block mockBlock = Mockito.mock(Block.class);

        BlockBreakEvent mockEvent = PowerMockito.mock(BlockBreakEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockEvent.getBlock()).thenReturn(mockBlock);

        for (Material material : Material.values()) {
        	if (!material.isBlock()) continue;
        	int id = material.getId();
            Mockito.when(mockBlock.getTypeId()).thenReturn(id);
            this.blockListener.onBlockBreak(mockEvent);
    		Mockito.verify(this.passedEDH).PlayerBlockBreak(mockPlayer, id);
		}
	}

	@Test
	public void testOnBlockPlaceEvent() {
        Player mockPlayer = Mockito.mock(Player.class);
		Block mockBlock = Mockito.mock(Block.class);

		BlockPlaceEvent mockEvent = PowerMockito.mock(BlockPlaceEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockEvent.getBlock()).thenReturn(mockBlock);

        for (Material material : Material.values()) {
        	if (!material.isBlock()) continue;
        	int id = material.getId();
            Mockito.when(mockBlock.getTypeId()).thenReturn(id);
            this.blockListener.onBlockPlace(mockEvent);
    		Mockito.verify(this.passedEDH).PlayerBlockPlace(mockPlayer, id);
		}
	}

}
