package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Textures;

public class ScreenJoin extends Screen {
	public ScreenJoin() {
		super();
		
		// Text
		addTitleText();
		addText("Comic Sans.ttf", 34, new Vector2f(Window.getWidth() / 2, 87), "Join a server", GUIText.CENTER);
		
		addBackButton();
		
		// Button join server
		GUIButton joinServerButton = new GUIButton(
				new Vector2i(Window.getWidth() - 275, Window.getHeight() - 87),
				new Vector2i(250, 75),
				Textures.BUTTON_JOIN_NORMAL,
                Textures.BUTTON_JOIN_HOVER,
                Textures.BUTTON_JOIN_PRESS,
				GUIButton.RIGHT,
				() -> {
					// TODO: Address
					// TODO: Port
					ClientConnection.initialize(SnakeMultiplayer.getInstance().getClient(), "localhost", 1337, 1338);
				}
			);
		
		addComponent(joinServerButton);
	}
}
