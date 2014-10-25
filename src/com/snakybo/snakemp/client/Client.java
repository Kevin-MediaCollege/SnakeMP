package com.snakybo.snakemp.client;

import javax.swing.Timer;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.network.ClientList;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.Screen;

public class Client implements IUpdatable, IRenderable {
	private static volatile Screen newScreen;
	
	private static Screen activeScreen;
		
	private ClientList clientList;
	
	private Timer countdownTimer;
	
	private int countdownIterations;
	
	public Client() {
		clientList = new ClientList();
		
		countdownTimer = new Timer(1000, (evt) -> {			
			countdownIterations++;
			
			Screen.SCREEN_LOBBY.setCountdownText(String.valueOf(5 - countdownIterations));
		});
		
		setActiveScreen(Screen.SCREEN_MAIN);
	}
	
	@Override
	public void update(Input input, float delta) {
		if(countdownTimer.isRunning()) {
			if(countdownIterations == 5) {
				countdownIterations = 0;
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_LOADED, String.valueOf(getData().getId()));
				countdownTimer.stop();
				Screen.SCREEN_LOBBY.removeCountdownText();
			}
		}
		
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
		final int id = (int)Float.parseFloat(stringId);
		
		ClientData self = new ClientData(id);
		
		ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, self.getId(), self.getColor().x, self.getColor().y, self.getColor().z);
		
		clientList.addClient(self);
		
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
	
	public void setCountdownState(int state) {
		switch(state) {
		case 0:
			countdownTimer.stop();
			countdownIterations = 0;
			Screen.SCREEN_LOBBY.removeCountdownText();
			break;
		case 1:
			if(!countdownTimer.isRunning()) {
				countdownTimer.restart();
				Screen.SCREEN_LOBBY.setCountdownText(String.valueOf(5));
			}
			break;
		}
	}
	
	public static void setActiveScreen(Screen newScreen) {
		Client.newScreen = newScreen;
	}
}
