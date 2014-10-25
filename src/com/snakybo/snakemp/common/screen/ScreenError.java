package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.common.network.ENetworkMessages;

public class ScreenError extends Screen {
	private volatile String newError;
	
	public ScreenError() {
		super(true);
		
		// Text
		addTitleText();
		addText(PIXELMIX_FONT, 34, new Vector2i(Window.getWidth() / 2, 67), "Error", GUIText.CENTER);
		
		addBackButton();
	}
	
	@Override
	public void update(Input input, float delta) {
		super.update(input, delta);
		
		if(newError != null) {
			setErrorText(newError, 300);
			newError = null;
		}
	}
	
	public void setErrorMessage(ENetworkMessages type) {
		newError = "Server has rejected your connection: " + type;
	}
}
