package com.snakybo.snakemp.client;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.network.ClientList;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.screen.Screen;

public class Client implements IUpdatable, IRenderable {
	private static volatile Screen newScreen;
	
	private static Screen activeScreen;
		
	private ClientList clientList;
	
	public Client() {
		clientList = new ClientList();
		clientList.addClient(new ClientData());
		
		setActiveScreen(Screen.SCREEN_MAIN);
	}
	
	@Override
	public void update(Input input, float delta) {
		if(newScreen != null) {
			activeScreen = newScreen;
			newScreen = null;
		}
		
		activeScreen.update(input, delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		activeScreen.render(renderer);
	}
	
	public void destroy() {
		ClientConnection.destroy();
	}
	
	public void onServerJoin(String stringId) {
		int id = (int)Float.parseFloat(stringId);
		
		clientList.getClientAt(0).setId(id);
		
		setActiveScreen(Screen.SCREEN_LOBBY);
		
		Screen.SCREEN_LOBBY.addClient(clientList.getClientAt(0));
		
		System.out.println("Connected to server");
	}
	
	public ClientData getData() {
		return clientList.getClientAt(0);
	}
	
	public ClientList getClientList() {
		return clientList;
	}
	
	public static void setActiveScreen(Screen newScreen) {
		Client.newScreen = newScreen;
	}
}
