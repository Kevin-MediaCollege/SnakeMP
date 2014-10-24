package com.snakybo.snakemp.common.screen;

import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;

public class ScreenError extends Screen {
	public ScreenError() {
		super();
		
		// Text
		addTitleText();
		addText("Comic Sans.ttf", 34, new Vector2f(Window.getWidth() / 2, 87), "Error", GUIText.CENTER);
		
		addBackButton();
	}
}
