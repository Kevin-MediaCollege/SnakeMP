package com.snakybo.snakemp.common.screen;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIComponent;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.Main;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Textures;

public class Screen implements IUpdatable, IRenderable {
	public static final ScreenMain SCREEN_MAIN = new ScreenMain();
	public static final ScreenHost SCREEN_HOST = new ScreenHost();
	public static final ScreenJoin SCREEN_JOIN = new ScreenJoin();
	public static final ScreenLobby SCREEN_LOBBY = new ScreenLobby();
	public static final ScreenError SCREEN_ERROR = new ScreenError();
	
	private List<GUIComponent> components;
	
	public Screen() {
		components = new ArrayList<GUIComponent>();
		
		GUIText versionText = new GUIText("Comic Sans.ttf", 12);
		versionText.setPosition(new Vector2f(5, Window.getHeight() - 10));
		versionText.setText("Version: " + Main.VERSION);
		versionText.setAnchor(GUIText.CENTER);
		
		addComponent(versionText);
	}
	
	@Override
	public void update(Input input, float delta) {
		for(GUIComponent component : components)
			component.update(input, delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		for(GUIComponent component : components)
			component.render(renderer);
	}
	
	protected GUIComponent addComponent(GUIComponent component) {
		components.add(component);
		
		return component;
	}
	
	protected void removeComponent(GUIComponent component) {
		components.remove(component);
	}
	
	protected GUIText addTitleText() {
		return addText("Comic Sans.ttf", 48, new Vector2f(Window.getWidth() / 2, 30), "Snake Multiplayer", GUIText.CENTER);
	}
	
	protected GUIText addText(String font, int size, Vector2f position, String string, int anchor) {
		GUIText text = new GUIText(font, size);
		
		text.setPosition(position);
		text.setText(string);
		text.setAnchor(anchor);
		
		return (GUIText)addComponent(text);
	}
	
	protected GUIButton addBackButton() {
		GUIButton button = new GUIButton(
			new Vector2i(Window.getWidth() - 12, Window.getHeight() - 87),
			new Vector2i(250, 75),
			Textures.BUTTON_BACK_NORMAL,
            Textures.BUTTON_BACK_HOVER,
            Textures.BUTTON_BACK_PRESS,
			GUIButton.RIGHT,
			() -> {
				SnakeMultiplayer.getInstance().stopServer();
					
				ClientConnection.destroy();
				Client.setActiveScreen(Screen.SCREEN_MAIN);
			}
		);
		
		return (GUIButton)addComponent(button);
	}
}
