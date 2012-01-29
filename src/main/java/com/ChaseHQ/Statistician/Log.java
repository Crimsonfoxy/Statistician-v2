package com.ChaseHQ.Statistician;

import com.ChaseHQ.Statistician.Config.Config;

public class Log {
	/**
	 * Send a message to the console with the plugin's name prefixed.
	 * @param message The message to send.
	 */
	public static void ConsoleLog(String message) {
		System.out.println(Config.getLogPrefix() + " " + message);
	}
}
