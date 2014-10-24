package com.snakybo.snakemp.server.client;

import java.net.InetAddress;

import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.common.data.ClientData;
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
			ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_PLAYING);
			ServerLogger.log("Rejected client: " + message[1] + ". Reason: Server is currently in-game.");
			return;
		}
		
		// Check if the server is full
		if(numClients >= clients.length) {
			ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_FULL);
			ServerLogger.log("Rejected client: " + message[1] + ". Reason: Server is full.");
			return;
		}
		
		// Check if client with same IP and port is already ingame
		for(ClientServerData data : clients) {
			if(data != null) {
				String dataAddress = data.getAddress().getCanonicalHostName();
				
				if(dataAddress.equals(address.getCanonicalHostName()) && data.getPort() == port) {
					ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_EXISTS);
					ServerLogger.log("Rejected client: " + message[1] + ". Reason: Player is already in the game.");
					return;
				}
			}
		}
		
		// Store the client info
		ClientServerData client = new ClientServerData(getNextClientId(), message[1], address, port);
		client.setColor(
				new Vector3f(
						Float.parseFloat(message[2]), 
						Float.parseFloat(message[3]),
						Float.parseFloat(message[4])
					)
				);
		
		// Welcome the client to the server and assign an ID
		ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_WELCOME_CLIENT, String.valueOf(client.getId()));
		
		// Announce that a new client has joined
		ServerConnection.sendUDP(ENetworkMessages.SERVER_CLIENT_JOINED,
				String.valueOf(client.getId()),
				message[1],
				message[2],
				message[3],
				message[4]
			);
		
		ServerLogger.log(client.getName() + " joined the game!");
		
		// Send the new client info about the other players in the game
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null) {
				ClientData c = clients[i];
				ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_CLIENT_JOINED,
						String.valueOf(c.getId()),
						c.getName(),
						String.valueOf(c.getColor().x),
						String.valueOf(c.getColor().y),
						String.valueOf(c.getColor().z)
					);
			}
		}
		
		clients[client.getId()] = client;
		
		numClients++;
	}
	
	public void onClientLeave(String stringId) {
		int id = (int)Float.parseFloat(stringId);
		
		ClientServerData client = getClient(id);
		
		if(client != null) {
			ServerConnection.sendUDP(ENetworkMessages.SERVER_CLIENT_LEFT, stringId);
			ServerLogger.log(client.getName() + " left.");
			
			clients[id] = null;
			
			numClients--;
		}
	}
	
	public void onClientReadyChange(String stringId, String stringIsReady) {
		int id = (int)Float.parseFloat(stringId);
		boolean isReady = (int)Float.parseFloat(stringIsReady) == 0 ? false : true;
		
		ClientServerData client = getClient(id);
		client.setIsReady(isReady);
		
		ServerConnection.sendUDP(ENetworkMessages.SERVER_CLIENT_UPDATE_READY, stringId, stringIsReady);
		
		ServerLogger.log(client.getName() + " is now " + (isReady ? "" : "not ") + "ready.");
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
	
	public ClientServerData getClient(int id) {
		for(ClientServerData client : clients)
			if(client != null)
				if(client.getId() == id)
					return client;
		
		return null;
	}
	
	public ClientServerData[] getClients() {
		return clients;
	}
}
