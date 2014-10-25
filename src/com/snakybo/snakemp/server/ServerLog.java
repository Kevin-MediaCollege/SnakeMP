package com.snakybo.snakemp.server;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.snakemp.common.data.Config;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ServerLog {
	private static List<String> logs;
	
	public static void initialize() {
		logs = new ArrayList<String>(Config.MAX_CHAT_LOGS);
	}
	
	public static void log(String message) {
		System.out.println("Server Log: " + message);
		
		logs.add(0, message);
	}
}
