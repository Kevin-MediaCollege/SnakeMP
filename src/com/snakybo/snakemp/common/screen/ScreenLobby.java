package com.snakybo.snakemp.common.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.components.GUITextButton;

public class ScreenLobby extends Screen {
	private List<ClientData> clientsToAdd;
	
	private Map<Integer, GUIText> clients;
	
	public ScreenLobby() {
		super();
		
		clientsToAdd = new ArrayList<ClientData>();
		clients = new HashMap<Integer, GUIText>();
		
		// Text
		addTitleText();
		addText(PIXELMIX_FONT, 34, new Vector2i(Window.getWidth() / 2, 87), "Lobby", GUIText.CENTER);
		
		addBackButton();
		
		// Button ready
		GUITextButton readyButton = new GUITextButton(
				new Vector2i(Window.getWidth() - 275, Window.getHeight() - 87),
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
		
		readyButton.setText(24, "READY");
		
		addComponent(readyButton);
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(clientsToAdd.size() > 0) {
			for(int i = 0; i < clientsToAdd.size(); i++) {
				GUIText text = addText(PIXELMIX_FONT, 16, new Vector2i(10, (20 * clientsToAdd.get(i).getId()) + 20), "[" + clientsToAdd.get(i).getId() + "]" + clientsToAdd.get(i).getName(), GUIText.LEFT);
				
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
