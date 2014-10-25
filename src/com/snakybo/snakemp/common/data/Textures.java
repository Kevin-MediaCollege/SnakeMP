package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.resource.Texture;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class Textures {
	public static Texture BUTTON_NORMAL;
	public static Texture BUTTON_HOVER;
	public static Texture BUTTON_PRESS;
	
	public static void initialize() {
		BUTTON_NORMAL = new Texture("normal.png");
		BUTTON_HOVER = new Texture("hover.png");
		BUTTON_PRESS = new Texture("press.png");
	}
}