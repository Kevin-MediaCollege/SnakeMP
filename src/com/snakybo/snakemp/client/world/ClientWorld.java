package com.snakybo.snakemp.client.world;

import com.snakybo.snakemp.client.Client;

public class ClientWorld {
	public static final int DIRECTION_UP = 0x00;
	public static final int DIRECTION_LEFT = 0x01;
	public static final int DIRECTION_DOWN = 0x02;
	public static final int DIRECTION_RIGHT = 0x03;
	
	private Client client;
	
	public ClientWorld(Client client) {
		this.client = client;
		
		//direction = (int)Math.floor(Math.random() * 3);
	}
}
