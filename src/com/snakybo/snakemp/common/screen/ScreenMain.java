package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.Main;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.screen.components.GUIInputField;
import com.snakybo.snakemp.common.screen.components.GUITextButton;

public class ScreenMain extends Screen {
	private GUIInputField playerNameField;
	
	public ScreenMain() {
		super();
		
		// Title text
		addTitleText();
		
		// Button host server
		GUITextButton hostButton = new GUITextButton(
        		new Vector2i((int)(Window.getWidth() / 2), 250),
                GUIButton.CENTER,
                () -> {
                	if(playerNameField.getValue().length() > 0) {
                		Config.playerName = playerNameField.getValue();
                		SnakeMultiplayer.getInstance().getClient().getData().setName(playerNameField.getValue());
                		
						Client.setActiveScreen(Screen.SCREEN_HOST);
					} else {
						setErrorText("You must have a player name", 500);
					}
                }
            );
		
        // Button join server
		GUITextButton joinButton = new GUITextButton(
        		new Vector2i((int)(Window.getWidth() / 2), 325),
                GUIButton.CENTER,
				() -> {
					if(playerNameField.getValue().length() > 0) {
						Config.playerName = playerNameField.getValue();
						SnakeMultiplayer.getInstance().getClient().getData().setName(playerNameField.getValue());
						
						Client.setActiveScreen(Screen.SCREEN_JOIN);
					} else {
						setErrorText("You must have a player name", 500);
					}
				}
            );
		
        // Button quit game
		GUITextButton quitButton = new GUITextButton(
				new Vector2i((int)(Window.getWidth() / 2), 400),
				GUIButton.CENTER,
				() -> {
					Main.stop();
				}
			);
		
		hostButton.setText(24, "HOST");
		joinButton.setText(24, "JOIN");
		quitButton.setText(24, "QUIT");
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 150, 180), "Player Name", GUIText.LEFT);
		playerNameField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 150, 200, 300, 30, ()->{});
		
		playerNameField.setText(Config.playerName);
		playerNameField.setCursorPos(99);
		playerNameField.setBorder(new Vector3f(0, 1, 0));
		playerNameField.setIsActive(true);
		playerNameField.setMaxCharacters(24);
		
		// Add components
		addComponent(hostButton);
		addComponent(joinButton);
		addComponent(quitButton);
		
		addComponent(playerNameField);
	}
}