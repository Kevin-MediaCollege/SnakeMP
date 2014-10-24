package com.snakybo.snakemp.client.network;

import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.screen.Screen;

public class ClientList {
	private ClientData[] clients;
	
	private int numClients;
	
	public ClientList() {
		clients = new ClientData[Config.MAX_CLIENTS];
		
		numClients = 0;
	}
	
	public void onClientJoin(String[] message) {
		ClientData data = new ClientData();
		
		data.setId((int)Float.parseFloat(message[1]));
		data.setName(message[2]);
		data.setColor(
				new Vector3f(
						Float.parseFloat(message[3]),
						Float.parseFloat(message[4]),
						Float.parseFloat(message[5])
						)
				);
		
		addClient(data);
		Screen.SCREEN_LOBBY.addClient(data);
	}
	
	public void onClientLeave(int id) {
		removeClient(id);
		Screen.SCREEN_LOBBY.removeClient(id);
	}
	
	public void addClient(ClientData client) {
		for(ClientData c : clients)
			if(c != null)
				if(c.getId() == client.getId())
					return;
		
		clients[numClients] = client;
		numClients++;
		
		System.out.println("Added client " + client.getName() + "(" + client.getId() + ")");
	}
	
	public void removeClient(int id) {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null) {
				if(clients[i].getId() == id) {
					System.out.println("Removed client " + clients[i].getName() + "(" + clients[i].getId() + ")");
					
					for(int j = i + 1; j < numClients; j++)
						clients[j - 1] = clients[j];
					
					clients[numClients - 1] = null;
					numClients--;
					
					break;
				}
			}
		}
	}
	
	public ClientData[] getClients() {
		return clients;
	}
	
	public ClientData getClientAt(int index) {
		return clients[index];
	}
	
	public ClientData getClientWithId(int id) {
		for(ClientData c : clients)
			if(c != null)
				if(c.getId() == id)
					return c;
		
		return null;
	}
}
