package com.snakybo.snakemp.common.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.data.Textures;
import com.snakybo.snakemp.common.network.ENetworkMessages;

public class ScreenLobby extends Screen {
	private List<ClientData> clientsToAdd;
	
	private Map<Integer, GUIText> clients;
	
	public ScreenLobby() {
		super();
		
		clientsToAdd = new ArrayList<ClientData>();
		clients = new HashMap<Integer, GUIText>();
		
		// Text
		addTitleText();
		addText("Comic Sans.ttf", 34, new Vector2f(Window.getWidth() / 2, 87), "Lobby", GUIText.CENTER);
		
		addBackButton();
		
		// Button ready
		// TODO: Ready texture
		GUIButton readyButton = new GUIButton(
				new Vector2i(Window.getWidth() - 275, Window.getHeight() - 87),
				new Vector2i(250, 75),
				Textures.BUTTON_JOIN_NORMAL,
                Textures.BUTTON_JOIN_HOVER,
                Textures.BUTTON_JOIN_PRESS,
				GUIButton.RIGHT,
				() -> {
					ClientData client = SnakeMultiplayer.getInstance().getClient().getData();
					
					client.setIsReady(!client.isReady());
					ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_READY, 
							String.valueOf(client.getId()),
							(client.isReady() ? "1" : "0")
						);
				}
			);
		
		addComponent(readyButton);
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(clientsToAdd.size() > 0) {
			for(int i = 0; i < clientsToAdd.size(); i++) {
				GUIText text = addText("Comic Sans.ttf", 16, new Vector2f(10, (20 * clientsToAdd.get(i).getId()) + 20), "[" + clientsToAdd.get(i).getId() + "]" + clientsToAdd.get(i).getName(), GUIText.LEFT);
				
				clients.put(clientsToAdd.get(i).getId(), text);
			}
			
			clientsToAdd.clear();
		}	
	}
	
	public void addClient(ClientData client) {
		clientsToAdd.add(client);
	}
	
	public void removeClient(int id) {
		removeComponent(clients.get(id));
		clients.remove(id);
	}
}
