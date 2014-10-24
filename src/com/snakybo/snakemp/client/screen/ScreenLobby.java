package com.snakybo.snakemp.client.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Textures;

public class ScreenLobby extends Screen {
	public ScreenLobby() {
		super();
		
		// Title text
		GUIText titleText = new GUIText("Comic Sans.ttf", 48);
		titleText.setPosition(new Vector2f(Window.getWidth() / 2, 40));
		titleText.setText("Snake Multiplayer");
		titleText.setAnchor(GUIText.CENTER);
		
		// Create server text
		GUIText createServerText = new GUIText("Comic Sans.ttf", 34);
		createServerText.setPosition(new Vector2f(Window.getWidth() / 2, 87));
		createServerText.setText("Lobby");
		createServerText.setAnchor(GUIText.CENTER);
		
		// Button join server
		GUIButton joinServerButton = new GUIButton(
				new Vector2i(Window.getWidth() - 275, Window.getHeight() - 87),
				new Vector2i(250, 75),
				Textures.BUTTON_JOIN_NORMAL,
                Textures.BUTTON_JOIN_HOVER,
                Textures.BUTTON_JOIN_PRESS,
				GUIButton.RIGHT,
				() -> {
					
				}
			);
		
		// Button back to menu
		GUIButton backButton = new GUIButton(
				new Vector2i(Window.getWidth() - 12, Window.getHeight() - 87),
				new Vector2i(250, 75),
				Textures.BUTTON_BACK_NORMAL,
                Textures.BUTTON_BACK_HOVER,
                Textures.BUTTON_BACK_PRESS,
				GUIButton.RIGHT,
				() -> {
					SnakeMultiplayer.getInstance().stopServer();
					
					ClientConnection.destroy();
					Client.setActiveScreen(ScreenMain.class);
				}
			);
		
		addComponent(titleText);
		addComponent(createServerText);
		
		addComponent(joinServerButton);
		addComponent(backButton);
	}
}
