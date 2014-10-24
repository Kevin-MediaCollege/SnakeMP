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

public class ScreenHost extends Screen {
	public ScreenHost() {
		super();
		
		// Title text
		GUIText titleText = new GUIText("Comic Sans.ttf", 48);
		titleText.setPosition(new Vector2f(Window.getWidth() / 2, 40));
		titleText.setText("Snake Multiplayer");
		titleText.setAnchor(GUIText.CENTER);
		
		// Create server text
		GUIText createServerText = new GUIText("Comic Sans.ttf", 34);
		createServerText.setPosition(new Vector2f(Window.getWidth() / 2, 87));
		createServerText.setText("Create a server");
		createServerText.setAnchor(GUIText.CENTER);
		
		// Button create server
		GUIButton createServerButton = new GUIButton(
				new Vector2i(Window.getWidth() - 275, Window.getHeight() - 87),
				new Vector2i(250, 75),
				Textures.BUTTON_CREATE_SERVER_NORMAL,
                Textures.BUTTON_CREATE_SERVER_HOVER,
                Textures.BUTTON_CREATE_SERVER_PRESS,
				GUIButton.RIGHT,
				() -> {
					// TODO: Port
					SnakeMultiplayer.getInstance().startServer(1337);
					ClientConnection.initialize(SnakeMultiplayer.getInstance().getClient(),"127.0.0.1", 1337);
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
					
					Client.setActiveScreen(ScreenMain.class);
				}
			);
		
		addComponent(titleText);
		addComponent(createServerText);
		
		addComponent(createServerButton);
		addComponent(backButton);
	}
}
