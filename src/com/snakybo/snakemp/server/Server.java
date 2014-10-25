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
		ServerLog.initialize();
		ServerLog.log("Initializing server");
		
		clientManager = new ClientManager(this);
		serverWorld = new ServerWorld();
		
		countdownInProgress = false;
		
		ServerConnection.initialize(this);
		ServerLog.log("Server started");
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
				if(!countdownInProgress)
					ServerConnection.sendUDP(ENetworkMessages.SERVER_COUNTDOWN_CHANGE, String.valueOf(1));
			} else {
				if(countdownInProgress)
					ServerConnection.sendUDP(ENetworkMessages.SERVER_COUNTDOWN_CHANGE, String.valueOf(0));
			}
		}
	}
	
	public void destroy() {
		ServerLog.log("Shutting down");
		
		ServerConnection.destroy();
		
		ServerLog.log("Shut down");
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
			ServerConnection.sendUDP(ENetworkMessages.SERVER_START_GAME);
	}
	
	public ClientManager getClientManager() {
		return clientManager;
	}
	
	public ServerWorld getServerWorld() {
		return serverWorld;
	}
}
