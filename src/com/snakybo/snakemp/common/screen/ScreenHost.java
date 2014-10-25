package com.snakybo.snakemp.common.screen;

import org.newdawn.slick.Color;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.screen.components.GUIInputField;
import com.snakybo.snakemp.common.screen.components.GUITextButton;

public class ScreenHost extends Screen {
	private GUIInputField udpPortField;
	private GUIInputField tcpPortField;
	
	private GUIText errorText;
	
	public ScreenHost() {
		super();
		
		// Text		
		addTitleText();
		addText(PIXELMIX_FONT, 34, new Vector2i(Window.getWidth() / 2, 70), "Create a server", GUIText.CENTER);
		
		addBackButton();
		
		// Button create server
		GUITextButton createServerButton = new GUITextButton(
				new Vector2i(Window.getWidth() - (GUITextButton.SIZE.x / 2) - 10, Window.getHeight() - 123),
				GUIButton.LEFT,
				() -> {
					if(udpPortField.getValue().length() > 0 && tcpPortField.getValue().length() > 0) {
						int udpPortValue = Integer.parseInt(udpPortField.getValue());
						int tcpPortValue = Integer.parseInt(tcpPortField.getValue());
						
						if(udpPortValue <= 0 || udpPortValue > 65535 || tcpPortValue <= 0 || tcpPortValue > 65535) {
							removeComponent(errorText);
							errorText = addText(PIXELMIX_FONT, 16, new Vector2i(Window.getWidth() / 2, 350), "UDP or TCP port is out of range (0-65535)", GUIText.CENTER);
							errorText.setColor(Color.red);
							return;
						}
						
						Config.udpPort = udpPortValue;
						Config.tcpPort = tcpPortValue;
							
						SnakeMultiplayer.getInstance().startServer();
						ClientConnection.initialize(SnakeMultiplayer.getInstance().getClient());
					} else {
						removeComponent(errorText);
						errorText = addText(PIXELMIX_FONT, 16, new Vector2i(Window.getWidth() / 2, 350), "Both the UDP and TCP port must have a value", GUIText.CENTER);
						errorText.setColor(Color.red);
					}
				}
			);
		
		createServerButton.setText(24, "CREATE");
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, 180), "UDP Port", GUIText.LEFT);
		udpPortField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, 200, 150, 30, ()->{});
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, 260), "TCP Port", GUIText.LEFT);
		tcpPortField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, 280, 150, 30, ()->{});
		
		udpPortField.setText(String.valueOf(Config.udpPort));
		udpPortField.setCursorPos(99);
		udpPortField.setBorder(new Vector3f(0, 1, 0));
		udpPortField.setIsActive(true);
		udpPortField.setMaxCharacters(5);
		
		tcpPortField.setText(String.valueOf(Config.tcpPort));
		tcpPortField.setCursorPos(99);
		tcpPortField.setBorder(new Vector3f(0, 1, 0));
		tcpPortField.setMaxCharacters(5);
		
		udpPortField.setAllowBit(GUIInputField.BIT_KEYS, false);
		udpPortField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		
		tcpPortField.setAllowBit(GUIInputField.BIT_KEYS, false);
		tcpPortField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		
		addComponent(createServerButton);
		addComponent(udpPortField);
		addComponent(tcpPortField);
	}
}
