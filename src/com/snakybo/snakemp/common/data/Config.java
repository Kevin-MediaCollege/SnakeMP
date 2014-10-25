package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.utils.math.Vector2i;

public class Config {
	public static final Vector2i GRID_SIZE = new Vector2i(40, 40);
	
	public static final float GAME_SPEED = 1;
	
	public static final int SNAKE_PART_SIZE_PIXELS = 4;
	
	public static final int MAX_CLIENTS = 9;
	public static final int MAX_CHAT_LOGS = 24;
	
	public static String serverAddress = "127.0.0.1";
	public static String playerName = "Player";
	
	public static int udpPort = 1337;
	public static int tcpPort = 1338;
}
