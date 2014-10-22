package com.snakybo.snakemp.server.client;

import com.snakybo.snakemp.common.data.SnakeData;
import com.snakybo.snakemp.common.network.ENetworkMessages;

public class ClientManager {
	private SnakeData[] clients;
	
	public void sendToAll(ENetworkMessages message, String string) {
		string = message.id() + "#" + string;
	}
	
	public SnakeData[] getClients() {
		return clients;
	}
}
