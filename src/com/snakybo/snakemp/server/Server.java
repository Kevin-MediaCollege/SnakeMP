package com.snakybo.snakemp.server;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.snakemp.server.client.ClientManager;
import com.snakybo.snakemp.server.network.ServerConnection;
import com.snakybo.snakemp.server.world.ServerWorld;

public class Server implements IUpdatable {
	private ClientManager clientManager;
	private ServerWorld serverWorld;
	
	public Server(int port) {
		ServerLogger.initialize();
		ServerLogger.log("Initializing");
		
		clientManager = new ClientManager(this);
		serverWorld = new ServerWorld();
		
		ServerConnection.initialize(this, port);
		ServerLogger.log("Server started");
	}
	
	@Override
	public void update(Input input, float delta) {
		
	}
	
	public void destroy() {
		ServerLogger.log("Shutting down");
		
		ServerConnection.destroy();
	}
	
	public ClientManager getClientManager() {
		return clientManager;
	}
	
	public ServerWorld getServerWorld() {
		return serverWorld;
	}
}
