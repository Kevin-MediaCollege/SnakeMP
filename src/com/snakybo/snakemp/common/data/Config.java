package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.utils.math.Vector2i;

public class Config {
	public static final Vector2i GRID_WIDTH_HEIGHT = new Vector2i(80, 45);
	
	public static final int START_SNAKE_LENGTH = 7;
	public static final int GAME_UPDATE_RATE = 10;
	public static final int MAX_CLIENTS = 16;
	public static final int GRID_SIZE = 16;	
	
	public static String serverAddress = "127.0.0.1";
	public static String playerName = "Player";
	
	public static int udpPort = 1337;
	public static int tcpPort = 1338;
}
