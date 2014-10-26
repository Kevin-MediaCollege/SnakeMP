package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.Input.KeyCode;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.screen.components.GUIInputField;
import com.snakybo.snakemp.common.screen.components.GUITextButton;

public class ScreenJoin extends Screen {
	private GUIInputField ipField;
	private GUIInputField udpPortField;
	private GUIInputField tcpPortField;
	
	private String newError;
	
	public ScreenJoin() {
		super(true);
		
		// Text
		addTitleText();
		addText(PIXELMIX_FONT, 34, new Vector2i(Window.getWidth() / 2, 67), "Join a server", GUIText.CENTER);
		
		addBackButton();
		
		// Button join server
		GUITextButton joinServerButton = new GUITextButton(
				new Vector2i(Window.getWidth() - (GUITextButton.SIZE.x / 2) - 10, Window.getHeight() - 123),
				GUIButton.LEFT,
				() -> {
					if(udpPortField.getValue().length() > 0 && tcpPortField.getValue().length() > 0 && ipField.getValue().length() > 0) {
						int udpPortValue = Integer.parseInt(udpPortField.getValue());
						int tcpPortValue = Integer.parseInt(tcpPortField.getValue());
						
						if(udpPortValue <= 0 || udpPortValue > 65535 || tcpPortValue <= 0 || tcpPortValue > 65535) {
							setErrorText("UDP or TCP port is out of range (0-65535)", 450);
							return;
						}
						
						Config.serverAddress = ipField.getValue();
						Config.udpPort = udpPortValue;
						Config.tcpPort = tcpPortValue;
							
						ClientConnection.initialize(SnakeMP.getInstance().getClient());
					} else {
						if(ipField.getValue().length() <= 0) {
							setErrorText("You have to enter an IP address", 450);
						} else {
							setErrorText("Both the UDP and TCP port must have a value", 450);
						}
					}
				}
			);
		
		joinServerButton.setText(24, "JOIN");
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, 180), "Server IP", GUIText.LEFT);
		ipField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, 200, 150, 30, ()->{});
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, 260), "Server UDP Port", GUIText.LEFT);
		udpPortField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, 280, 150, 30, ()->{});
		
		addText(PIXELMIX_FONT, 14, new Vector2i(Window.getWidth() / 2 - 75, 340), "Server TCP Port", GUIText.LEFT);
		tcpPortField = new GUIInputField(PIXELMIX_FONT, 14, Window.getWidth() / 2 - 75, 360, 150, 30, ()->{});
		
		ipField.setText(Config.serverAddress);
		ipField.setCursorPos(99);
		ipField.setBorder(new Vector3f(0, 1, 0));
		ipField.setIsActive(true);
		ipField.setMaxCharacters(15);
		
		udpPortField.setText(String.valueOf(Config.udpPort));
		udpPortField.setCursorPos(99);
		udpPortField.setBorder(new Vector3f(0, 1, 0));
		udpPortField.setMaxCharacters(5);
		
		tcpPortField.setText(String.valueOf(Config.tcpPort));
		tcpPortField.setCursorPos(99);
		tcpPortField.setBorder(new Vector3f(0, 1, 0));
		tcpPortField.setMaxCharacters(5);
		
		ipField.setAllowBit(GUIInputField.BIT_KEYS, false);
		ipField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		ipField.setAllowCustom(KeyCode.PERIOD, true);
		
		udpPortField.setAllowBit(GUIInputField.BIT_KEYS, false);
		udpPortField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		
		tcpPortField.setAllowBit(GUIInputField.BIT_KEYS, false);
		tcpPortField.setAllowBit(GUIInputField.BIT_SPECIALS, false);
		
		addComponent(joinServerButton);
		addComponent(ipField);
		addComponent(udpPortField);
		addComponent(tcpPortField);
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(newError != null) {
			setErrorText(newError, 500);
			newError = null;
		}
	}
	
	@Override
	public void reset() {
		removeErrorText();
	}
	
	public void setErrorMessage(String error) {
		newError = error;
	}
}
