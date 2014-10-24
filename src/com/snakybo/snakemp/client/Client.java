package com.snakybo.snakemp.client;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.screen.Screen;
import com.snakybo.snakemp.client.screen.ScreenLobby;
import com.snakybo.snakemp.client.screen.ScreenMain;
import com.snakybo.snakemp.common.data.ClientData;

public class Client implements IUpdatable, IRenderable {
	private static volatile Class<? extends Screen> newScreen;
	
	private static Screen activeScreen;
		
	private ClientData clientData;
	
	public Client() {
		clientData = new ClientData();
		
		setActiveScreen(ScreenMain.class);
	}
	
	@Override
	public void update(Input input, float delta) {
		if(newScreen != null) {
			try {
				activeScreen = newScreen.newInstance();
			} catch(InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
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
		
		clientData.setId(id);
		
		setActiveScreen(ScreenLobby.class);
	}
	
	public ClientData getClientData() {
		return clientData;
	}
	
	public static void setActiveScreen(Class<? extends Screen> newScreen) {
		Client.newScreen = newScreen;
	}
}
