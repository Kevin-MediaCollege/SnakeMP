package com.snakybo.snakemp.client;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.snakemp.client.screen.Screen;
import com.snakybo.snakemp.client.screen.ScreenMain;

public class Client implements IUpdatable, IRenderable {
	private static Screen activeScreen;
	
	public Client() {
		setActiveMenu(ScreenMain.getInstance());
	}
	
	@Override
	public void update(Input input, float delta) {
		activeScreen.update(input, delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		activeScreen.render(renderer);
	}
	
	public void destroy() {
		
	}
	
	public static void setActiveMenu(Screen activeScreen) {
		Client.activeScreen = activeScreen;
	}
}
