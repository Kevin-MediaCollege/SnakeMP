package com.snakybo.snakemp.common;

import com.snakybo.sengine2d.core.Game;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.SEngine2D;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.data.Textures;
import com.snakybo.snakemp.server.Server;

public class SnakeMP implements Game {
	private static SnakeMP instance;
	
	private Server server;
	private Client client;
	
	@Override
	public void init(SEngine2D engine) {
		instance = this;
		
		Textures.initialize();
		
		client = new Client();
	}
	
	@Override
	public void update(Input input, float delta) {
		client.update(input, delta);
		
		if(server != null)
			server.update(input, delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		client.render(renderer);
	}
	
	@Override
	public void destroy() {
		client.destroy();
		
		if(server != null)
			server.destroy();
		
		System.exit(0);
	}
	
	public void startServer() {
		if(server != null)
			return;
		
		server = new Server();
	}
	
	public void stopServer() {
		if(server == null)
			return;
		
		server.destroy();
		server = null;
	}
	
	public Client getClient() {
		return client;
	}
	
	public Server getServer() {
		return server;
	}
	
	public static SnakeMP getInstance() {
		return instance;
	}
}
