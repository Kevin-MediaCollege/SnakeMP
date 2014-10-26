package com.snakybo.snakemp.server;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.client.ClientManager;
import com.snakybo.snakemp.server.client.ClientServerData;
import com.snakybo.snakemp.server.network.ServerConnection;
import com.snakybo.snakemp.server.world.ServerWorld;

public class Server implements IUpdatable {
	private ClientManager clientManager;
	private ServerWorld serverWorld;
	
	private boolean countdownInProgress;
	
	public Server() {
		clientManager = new ClientManager(this);
		serverWorld = new ServerWorld(this);
		
		countdownInProgress = false;
		
		ServerConnection.initialize(this);
	}
	
	@Override
	public void update(Input input, float delta) {
		if(!serverWorld.isIngame()) {
			boolean everyoneReady = true;
			
			for(ClientServerData client : clientManager.getClients()) {
				if(client != null) {
					if(!client.isReady()) {
						everyoneReady = false;
						break;
					}
				}
			}
			
			if(everyoneReady) {
				if(!countdownInProgress) {
					ServerConnection.sendUDP(ENetworkMessages.SERVER_COUNTDOWN_CHANGE, 1);
					countdownInProgress = true;
				}
			} else {
				if(countdownInProgress) {
					ServerConnection.sendUDP(ENetworkMessages.SERVER_COUNTDOWN_CHANGE, 0);
					countdownInProgress = false;
				}
			}
		} else {
			serverWorld.update(input, delta);
		}
	}
	
	public void destroy() {
		ServerLog.log("Shutting down");
		
		ServerConnection.destroy();
	}
	
	public void checkIfEveryoneIsReady() {
		boolean everyoneLoaded = true;
		
		for(ClientServerData client : clientManager.getClients()) {
			if(client != null) {
				if(!client.isDoneLoading()) {
					everyoneLoaded = false;
					break;
				}
			}
		}
		
		if(everyoneLoaded)
			serverWorld.spawnPlayers(clientManager.getClients());
	}
	
	public ClientManager getClientManager() {
		return clientManager;
	}
	
	public ServerWorld getServerWorld() {
		return serverWorld;
	}
}
