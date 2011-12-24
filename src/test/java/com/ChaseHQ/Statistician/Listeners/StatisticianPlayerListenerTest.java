package com.ChaseHQ.Statistician.Listeners;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ChaseHQ.Statistician.EventDataHandlers.EDHPlayer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayerJoinEvent.class, PlayerQuitEvent.class, PlayerMoveEvent.class, PlayerPickupItemEvent.class, PlayerDropItemEvent.class})
public class StatisticianPlayerListenerTest {
	private EDHPlayer passedEDH;
	private StatisticianPlayerListener playerListener;
	
	@Before
	public void setUp() throws Exception {
		this.passedEDH = Mockito.mock(EDHPlayer.class);
		this.playerListener = new StatisticianPlayerListener(this.passedEDH);
	}

	@Test
	public void testOnPlayerJoinEvent() {
        Player mockPlayer = Mockito.mock(Player.class);

        PlayerJoinEvent mockEvent = PowerMockito.mock(PlayerJoinEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);

        this.playerListener.onPlayerJoin(mockEvent);
		Mockito.verify(this.passedEDH).PlayerJoin(mockPlayer);
	}

	@Test
	public void testOnPlayerQuitEvent() {
        Player mockPlayer = Mockito.mock(Player.class);

        PlayerQuitEvent mockEvent = PowerMockito.mock(PlayerQuitEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);

        this.playerListener.onPlayerQuit(mockEvent);
		Mockito.verify(this.passedEDH).PlayerQuit(mockPlayer);
	}

	@Test
	public void testOnPlayerMoveEvent() {
        Player mockPlayer = Mockito.mock(Player.class);
		Minecart mockMinecart = Mockito.mock(Minecart.class);
		Pig mockPig = Mockito.mock(Pig.class);
		Boat mockBoat = Mockito.mock(Boat.class);

        PlayerMoveEvent mockEvent = PowerMockito.mock(PlayerMoveEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);

        Mockito.when(mockPlayer.isInsideVehicle()).thenReturn(false);
        this.playerListener.onPlayerMove(mockEvent);
		Mockito.verify(this.passedEDH).PlayerMove(mockPlayer, false, false, false);

        Mockito.when(mockPlayer.isInsideVehicle()).thenReturn(true);

        Mockito.when(mockPlayer.getVehicle()).thenReturn(mockMinecart);
        this.playerListener.onPlayerMove(mockEvent);
		Mockito.verify(this.passedEDH).PlayerMove(mockPlayer, true, false, false);
        
        Mockito.when(mockPlayer.getVehicle()).thenReturn(mockPig);
        this.playerListener.onPlayerMove(mockEvent);
		Mockito.verify(this.passedEDH).PlayerMove(mockPlayer, false, true, false);
        
        Mockito.when(mockPlayer.getVehicle()).thenReturn(mockBoat);
        this.playerListener.onPlayerMove(mockEvent);
		Mockito.verify(this.passedEDH).PlayerMove(mockPlayer, false, false, true);
	}

	@Test
	public void testOnPlayerPickupItemEvent() {
        Player mockPlayer = Mockito.mock(Player.class);

        ItemStack mockItemStack = Mockito.mock(ItemStack.class);
        Mockito.when(mockItemStack.getTypeId()).thenReturn(10);
        Mockito.when(mockItemStack.getAmount()).thenReturn(20);

        Item mockItem = Mockito.mock(Item.class);
        Mockito.when(mockItem.getItemStack()).thenReturn(mockItemStack);

        PlayerPickupItemEvent mockEvent = PowerMockito.mock(PlayerPickupItemEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockEvent.getItem()).thenReturn(mockItem);

        this.playerListener.onPlayerPickupItem(mockEvent);
		Mockito.verify(this.passedEDH).PlayerPickedUpItem(mockPlayer, 10, 20);
	}

	@Test
	public void testOnPlayerDropItemEvent() {
        Player mockPlayer = Mockito.mock(Player.class);

        ItemStack mockItemStack = Mockito.mock(ItemStack.class);
        Mockito.when(mockItemStack.getTypeId()).thenReturn(10);
        Mockito.when(mockItemStack.getAmount()).thenReturn(20);

        Item mockItem = Mockito.mock(Item.class);
        Mockito.when(mockItem.getItemStack()).thenReturn(mockItemStack);

        PlayerDropItemEvent mockEvent = PowerMockito.mock(PlayerDropItemEvent.class);
        Mockito.when(mockEvent.getPlayer()).thenReturn(mockPlayer);
        Mockito.when(mockEvent.getItemDrop()).thenReturn(mockItem);

        this.playerListener.onPlayerDropItem(mockEvent);
		Mockito.verify(this.passedEDH).PlayerDroppedItem(mockPlayer, 10, 20);
	}

}
