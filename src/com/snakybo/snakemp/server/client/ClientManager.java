package com.snakybo.snakemp.server.client;

import java.net.InetAddress;

import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.ServerLogger;
import com.snakybo.snakemp.server.network.ServerConnection;

public class ClientManager {
	private ClientServerData[] clients;
	
	private Server server;
	
	private int numClients;
	
	public ClientManager(Server server) {
		clients = new ClientServerData[Config.MAX_CLIENTS];
		
		numClients = 0;
		this.server = server;
	}
	
	public void onClientRequestJoin(String[] message, InetAddress address, int port) {
		// Check if the server is in-game
		if(server.getServerWorld().isIngame()) {
			ServerConnection.sendToAddress(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_PLAYING, "");
			ServerLogger.log("Rejected client: " + message[1] + ". Reason: Server is currently in-game.");
			return;
		}
		
		// Check if the server is full
		if(numClients >= clients.length) {
			ServerConnection.sendToAddress(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_FULL, "");
			ServerLogger.log("Rejected client: " + message[1] + ". Reason: Server is full.");
			return;
		}
		
		// Check if client with same IP and port is already ingame
		for(ClientServerData data : clients) {
			if(data != null) {
				if(data.getAddress().getCanonicalHostName().equals(address.getCanonicalHostName()) && data.getPort() == port) {
					ServerConnection.sendToAddress(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_EXISTS, "");
					ServerLogger.log("Rejected client: " + message[1] + ". Reason: Player is already in the game.");
					return;
				}
			}
		}
		
		ClientServerData client = new ClientServerData(getNextClientId(), message[1], address, port);
		client.setColor(
				new Vector3f(
						Float.parseFloat(message[2]), 
						Float.parseFloat(message[3]),
						Float.parseFloat(message[4])
					)
				);
		
		clients[client.getId()] = client;
		
		ServerConnection.sendToAddress(address, port, ENetworkMessages.SERVER_WELCOME_CLIENT, String.valueOf(client.getId()));
		ServerLogger.log(client.getName() + " joined the game!");
		
		numClients++;
	}
	
	public void onClientLeave(String stringId) {
		if(stringId != null && stringId != "") {
			int id = (int)Float.parseFloat(stringId);
			
			for(ClientServerData data : clients) {
				if(id == data.getId()) {
					ServerConnection.sendToAll(ENetworkMessages.SERVER_CLIENT_LEFT, stringId);
					ServerLogger.log(data.getName() + " left.");
					
					clients[id] = null;
					break;
				}
			}
		}
	}
	
	private int getNextClientId() {
		final int length = clients.length;
		
		if(clients.length == 0)
			return -1;
		
		for(int id = 0; id < clients.length; id++) {
			
			for(int p = 0; p < length; p++) {
				if(clients[p] != null)
					if(id == clients[p].getId())
						break;
				
				if(p == length - 1)
					return id;
			}
		}
		
		return -1;
	}
	
	public ClientServerData[] getClients() {
		return clients;
	}
}
