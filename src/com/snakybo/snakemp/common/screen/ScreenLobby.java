package com.snakybo.snakemp.common.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.Input.KeyCode;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.Bounds;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.components.GUIInputField;
import com.snakybo.snakemp.common.screen.components.GUITextButton;


public class ScreenLobby extends Screen {
	private List<ClientData> clientsToAdd;
	
	private Map<ClientData, List<GUIText>> clients;
	
	private GUIText lobbyText;
	private GUIText countdownText;
	
	private GUIInputField redField;
	private GUIInputField greenField;
	private GUIInputField blueField;
	
	private String newCountdownText;
	
	private boolean init;
	
	public ScreenLobby() {
		super(true);
		
		clientsToAdd = new ArrayList<ClientData>();
		clients = new ConcurrentHashMap<ClientData, List<GUIText>>();
		
		init = true;
		
		// Text
		addTitleText();
		lobbyText = addText(PIXELMIX_FONT, 34, new Vector2i(Window.getWidth() / 2, 67), "Player's Lobby", GUIText.CENTER);
		
		addBackButton();
		
		// Button ready
		GUITextButton readyButton = new GUITextButton(
				new Vector2i(Window.getWidth() - (GUITextButton.SIZE.x / 2) - 10, Window.getHeight() - 123),
				GUIButton.LEFT,
				() -> {
					ClientData client = SnakeMultiplayer.getInstance().getClient().getPlayer();
					
					client.setIsReady(!client.isReady());
					ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_READY, 
							String.valueOf(client.getId()),
							(client.isReady() ? "1" : "0")
						);
				}
			);
		
		readyButton.setText(24, "READY");
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 245, Window.getHeight() - 60), "Red color", GUIText.LEFT);
		redField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 245, Window.getHeight() - 40, 150, 30, () -> {
			if(redField.getValue().length() <= 0)
				redField.setText("0.3");
			
			Float redValue = Float.parseFloat(redField.getValue());
			
			if(redValue < 0.3f) {
				redValue = 0.3f;
				redField.setText("0.3");
			} else if(redValue > 0.7f) {
				redValue = 0.7f;
				redField.setText("0.7");
			}
			
			ClientData data = SnakeMultiplayer.getInstance().getClient().getPlayer();
			Vector3f oldColor = data.getColor();
			
			data.setColor(new Vector3f(redValue, oldColor.y, oldColor.z));
			ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, String.valueOf(data.getId()), String.valueOf(data.getColor().x), String.valueOf(data.getColor().y), String.valueOf(data.getColor().z));
		});
		
		redField.setBorder(new Vector3f(1, 0, 0));
		redField.setMaxCharacters(10);
		
		redField.setAllowBit(GUIInputField.BIT_KEYS, false);
		redField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		redField.setAllowCustom(KeyCode.PERIOD, true);
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, Window.getHeight() - 60), "Green color", GUIText.LEFT);
		greenField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, Window.getHeight() - 40, 150, 30, () -> {
			if(greenField.getValue().length() <= 0)
				greenField.setText("0.3");
			
			Float greenValue = Float.parseFloat(greenField.getValue());
			
			if(greenValue < 0.3f) {
				greenValue = 0.3f;
				greenField.setText("0.3");
			} else if(greenValue > 0.7f) {
				greenValue = 0.7f;
				greenField.setText("0.7");
			}
			
			ClientData data = SnakeMultiplayer.getInstance().getClient().getPlayer();
			Vector3f oldColor = data.getColor();
			
			data.setColor(new Vector3f(oldColor.x, greenValue, oldColor.z));
			ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, String.valueOf(data.getId()), String.valueOf(data.getColor().x), String.valueOf(data.getColor().y), String.valueOf(data.getColor().z));
		});
		
		greenField.setBorder(new Vector3f(0, 1, 0));
		greenField.setMaxCharacters(10);
		
		greenField.setAllowBit(GUIInputField.BIT_KEYS, false);
		greenField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		greenField.setAllowCustom(KeyCode.PERIOD, true);
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 + 95, Window.getHeight() - 60), "Blue color", GUIText.LEFT);
		blueField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 + 95, Window.getHeight() - 40, 150, 30, () -> {
			if(blueField.getValue().length() <= 0)
				blueField.setText("0.3");
			
			Float blueValue = Float.parseFloat(blueField.getValue());
			
			if(blueValue < 0.3f) {
				blueValue = 0.3f;
				blueField.setText("0.3");
			} else if(blueValue > 0.7f) {
				blueValue = 0.7f;
				blueField.setText("0.7");
			}
			
			ClientData data = SnakeMultiplayer.getInstance().getClient().getPlayer();
			Vector3f oldColor = data.getColor();
			
			data.setColor(new Vector3f(oldColor.x, oldColor.y, blueValue));
			ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, String.valueOf(data.getId()), String.valueOf(data.getColor().x), String.valueOf(data.getColor().y), String.valueOf(data.getColor().z));
		});
		
		blueField.setBorder(new Vector3f(0, 0, 1));
		blueField.setMaxCharacters(10);
		
		blueField.setAllowBit(GUIInputField.BIT_KEYS, false);
		blueField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		blueField.setAllowCustom(KeyCode.PERIOD, true);
		
		addComponent(readyButton);
		
		addComponent(redField);
		addComponent(greenField);
		addComponent(blueField);
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(newCountdownText != null) {
			if(countdownText == null)
				countdownText = addText(PIXELMIX_FONT, 32, new Vector2i(Window.getWidth() / 2, Window.getHeight() / 2), "", GUIText.CENTER);
			
			countdownText.setText(newCountdownText);
			newCountdownText = null;
		}
		
		if(!redField.isActive())
			redField.setText(String.valueOf(SnakeMultiplayer.getInstance().getClient().getPlayer().getColor().x));
		
		if(!greenField.isActive())
			greenField.setText(String.valueOf(SnakeMultiplayer.getInstance().getClient().getPlayer().getColor().y));
		
		if(!blueField.isActive())
			blueField.setText(String.valueOf(SnakeMultiplayer.getInstance().getClient().getPlayer().getColor().z));
		
		if(clientsToAdd.size() > 0) {
			for(int i = 0; i < clientsToAdd.size(); i++) {
				int yPos = (27 * clientsToAdd.get(i).getId()) + 150;
				
				List<GUIText> texts = new ArrayList<GUIText>(2);
				
				texts.add(addText(PIXELMIX_FONT, 16, new Vector2i(435, yPos), String.valueOf(clientsToAdd.get(i).getId()), GUIText.LEFT));
				texts.add(addText(PIXELMIX_FONT, 16, new Vector2i(460, yPos), clientsToAdd.get(i).getName(), GUIText.LEFT));
				
				clients.put(clientsToAdd.get(i), texts);
			}
			
			clientsToAdd.clear();
		}
		
		if(init) {
			for(ClientData client : clients.keySet()) {
				if(client.getId() == 0) {
					lobbyText.setText(client.getName() + "'s lobby");
					init = false;
					break;
				}
			}
		}
	}
	
	@Override
	public void render(Renderer renderer) {
		try {
			for(ClientData client : clients.keySet()) {
				int yPos = (27 * client.getId()) + 150;
				
				renderer.drawQuad(new Bounds(430, 826, yPos - 3, yPos + 21), client.getColor());
				renderer.drawQuad(new Bounds(826, 850, yPos - 3, yPos + 21), client.isReady() ? new Vector3f(0, 1, 0) : new Vector3f(1, 0, 0));
			}
		} finally {
			super.render(renderer);
		}
	}
	
	@Override
	public void reset() {
		removeCountdownText();
		countdownText = null;
		
		clients.clear();
	}
	
	public void setCountdownText(String countdownText) {
		newCountdownText = countdownText;
	}
	
	public void removeCountdownText() {
		removeComponent(countdownText);
		
		countdownText = null;
	}
	
	public void addClient(ClientData client) {
		clientsToAdd.add(client);
	}
	
	public void removeClient(int id) {
		Iterator<Map.Entry<ClientData, List<GUIText>>> entries = clients.entrySet().iterator();
		
		while(entries.hasNext()) {
			Map.Entry<ClientData, List<GUIText>> entry = entries.next();
			
			ClientData key = entry.getKey();
			List<GUIText> value = entry.getValue();
			
			if(key.getId() == id) {
				for(GUIText text : value) {
					removeComponent(text);
				}
				
				clients.remove(key);
				break;
			}
			
			entries.remove();
		}
	}
}
