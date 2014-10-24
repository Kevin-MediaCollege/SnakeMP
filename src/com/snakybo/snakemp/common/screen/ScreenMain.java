package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.Main;
import com.snakybo.snakemp.common.data.Textures;

public class ScreenMain extends Screen {
	public ScreenMain() {
		super();
		
		// Title text
		addTitleText();
		
		// Button host server
        GUIButton hostButton = new GUIButton(
        		new Vector2i((int)(Window.getWidth() / 2), 100),
				new Vector2i(250, 75),
                Textures.BUTTON_HOST_NORMAL,
                Textures.BUTTON_HOST_HOVER,
                Textures.BUTTON_HOST_PRESS,
                GUIButton.CENTER,
                () -> {
                    Client.setActiveScreen(Screen.SCREEN_HOST);
                }
            );
		
        // Button join server
        GUIButton joinButton = new GUIButton(
        		new Vector2i((int)(Window.getWidth() / 2), 200),
				new Vector2i(250, 75),
                Textures.BUTTON_JOIN_NORMAL,
                Textures.BUTTON_JOIN_HOVER,
                Textures.BUTTON_JOIN_PRESS,
                GUIButton.CENTER,
				() -> {
	                Client.setActiveScreen(Screen.SCREEN_JOIN);
				}
            );
		
        // Button quit game
		GUIButton quitButton = new GUIButton(
				new Vector2i((int)(Window.getWidth() / 2), 300),
				new Vector2i(250, 75),
				Textures.BUTTON_QUIT_NORMAL,
                Textures.BUTTON_QUIT_HOVER,
                Textures.BUTTON_QUIT_PRESS,
				GUIButton.CENTER,
				() -> {
					Main.stop();
				}
			);
		
		// Add components
		addComponent(hostButton);
		addComponent(joinButton);
		addComponent(quitButton);
	}
}