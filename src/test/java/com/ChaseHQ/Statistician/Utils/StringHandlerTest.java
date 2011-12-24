package com.ChaseHQ.Statistician.Utils;

import org.junit.Assert;
import org.mockito.Mockito;

import org.bukkit.entity.Player;
import org.junit.Test;

public class StringHandlerTest {
	@Test
	public void testFormatForChat() {
		Player playerMock = Mockito.mock(Player.class);
		Mockito.when(playerMock.getName()).thenReturn("Coryf88");

		Assert.assertEquals("empty", "", StringHandler.formatForChat("", playerMock));
		Assert.assertEquals("plain", "Hello World!", StringHandler.formatForChat("Hello World!", playerMock));
		Assert.assertEquals("colored", "\u00A70H\u00A71e\u00A72l\u00A73l\u00A74o\u00A75_\u00A76W\u00A77o\u00A78r\u00A79l\u00A7ad\u00A7b!\u00A7c_\u00A7dA\u00A7eB\u00A7fC", StringHandler.formatForChat("&0H&1e&2l&3l&4o&5_&6W&7o&8r&9l&ad&b!&c_&dA&eB&fC", playerMock));
		Assert.assertEquals("full", "\u00A70Hello \u00A71Coryf88\u00A72!", StringHandler.formatForChat("&0Hello &1{playerName}&2!", playerMock));
	}

	@Test(expected = NullPointerException.class)
	public void testFormatForChatNull() {
		StringHandler.formatForChat(null, Mockito.mock(Player.class));
	}
}