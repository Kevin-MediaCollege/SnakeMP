package com.snakybo.snakemp.common.screen.components;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.common.data.Textures;
import com.snakybo.snakemp.common.screen.Screen;

public class GUITextButton extends GUIButton {
	public static final Vector2i SIZE = new Vector2i(175, 53);
	
	private GUIText text;
	
	public GUITextButton(Vector2i position, int anchorPoint, ButtonHandler handler) {
		super(position, SIZE, Textures.BUTTON_NORMAL, Textures.BUTTON_HOVER, Textures.BUTTON_PRESS, anchorPoint, handler);
	}
	
	@Override
	public void render(Renderer renderer) {
		super.render(renderer);
		
		text.render(renderer);
	}
	
	public GUIText setText(int size, String string) {
		text = new GUIText(Screen.PIXELMIX_FONT, size);
		
		text.setPosition(new Vector2i(bounds.left + ((bounds.right - bounds.left) / 2) + 5, bounds.top + ((bounds.bottom - bounds.top) / 4)));
		text.setText(string);
		text.setAnchor(GUIText.CENTER);
		
		return text;
	}
}
