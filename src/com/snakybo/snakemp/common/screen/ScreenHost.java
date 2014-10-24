package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Textures;

public class ScreenHost extends Screen {
	public ScreenHost() {
		super();
		
		// Text		
		addTitleText();
		addText("Comic Sans.ttf", 34, new Vector2f(Window.getWidth() / 2, 87), "Create a server", GUIText.CENTER);
		
		addBackButton();
		
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
					SnakeMultiplayer.getInstance().startServer(1337, 1338);
					
					ClientConnection.initialize(SnakeMultiplayer.getInstance().getClient(),"127.0.0.1", 1337, 1338);
				}
			);
		
		addComponent(createServerButton);
	}
}
