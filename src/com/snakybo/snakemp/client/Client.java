package com.snakybo.snakemp.client;

import javax.swing.Timer;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.network.ClientList;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.client.player.Player;
import com.snakybo.snakemp.client.world.ClientWorld;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.Screen;

public class Client implements IUpdatable, IRenderable {
	private static volatile Screen newScreen;
	
	private static Screen activeScreen;
		
	private ClientWorld clientWorld;
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
			if(countdownIterations == 1) { // FIXME: set to 5
				countdownIterations = 0;
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_LOADED, String.valueOf(getPlayer().getId()));
				countdownTimer.stop();
			}
		}
		
		if(newScreen != null) {
			if(activeScreen != null)
				activeScreen.reset();
			
			activeScreen = newScreen;
			newScreen = null;
		}
		
		if(clientWorld != null) {
			getPlayer().update(input, delta);
			clientWorld.update(input, delta);
		}
		
		activeScreen.update(input, delta);
	}
	
	public void startGame() {
		clientWorld = new ClientWorld(clientList.getClients());
		
		Client.setActiveScreen(Screen.SCREEN_GAME);
		Screen.SCREEN_LOBBY.reset();
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
		
		ClientData self = new Player(id);
		
		ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, self.getId(), self.getColor().x, self.getColor().y, self.getColor().z);
		
		clientList.addClient(self);
		
		setActiveScreen(Screen.SCREEN_LOBBY);
		
		Screen.SCREEN_LOBBY.addClient(clientList.getClientAt(0));
		
		System.out.println("Connected to server");
	}
	
	public Player getPlayer() {
		return (Player)clientList.getClientAt(0);
	}
	
	public ClientWorld getClientWorld() {
		return clientWorld;
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
			countdownTimer.restart();
			Screen.SCREEN_LOBBY.setCountdownText(String.valueOf(5));
			break;
		}
	}
	
	public static void setActiveScreen(Screen newScreen) {
		Client.newScreen = newScreen;
	}
}
