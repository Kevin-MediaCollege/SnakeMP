package com.snakybo.snakemp.common.screen;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIButton.ButtonHandler;
import com.snakybo.sengine2d.gui.GUIComponent;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.Main;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.screen.components.GUITextButton;

public class Screen implements IUpdatable, IRenderable {
	public static final ScreenMain SCREEN_MAIN = new ScreenMain();
	public static final ScreenHost SCREEN_HOST = new ScreenHost();
	public static final ScreenJoin SCREEN_JOIN = new ScreenJoin();
	public static final ScreenLobby SCREEN_LOBBY = new ScreenLobby();
	public static final ScreenError SCREEN_ERROR = new ScreenError();
	public static final ScreenGame SCREEN_GAME = new ScreenGame();
	
	public static final String PIXELMIX_FONT = "pixelmix.ttf";
	
	private List<GUIComponent> components;
	
	private GUIText errorText;
	
	public Screen(boolean addVersion) {
		components = new ArrayList<GUIComponent>();
		
		if(addVersion)
			addText(PIXELMIX_FONT, 12, new Vector2i(5, Window.getHeight() - 20), "Version: " + Main.VERSION, GUIText.LEFT);
	}
	
	@Override
	public void update(Input input, float delta) {
		for(int i = 0; i < components.size(); i++)
			components.get(i).update(input, delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		for(int i = 0; i < components.size(); i++)
			components.get(i).render(renderer);
	}
	
	protected GUIComponent addComponent(GUIComponent component) {
		if(!components.contains(component)) {
			components.add(component);
		
			return component;
		}
		
		return null;
	}
	
	public void reset() {}
	
	protected void removeComponent(GUIComponent component) {
		if(components.contains(component))
			components.remove(component);
	}
	
	protected void removeErrorText() {
		removeComponent(errorText);
	}
	
	protected GUIText addTitleText() {
		return addText(PIXELMIX_FONT, 48, new Vector2i(Window.getWidth() / 2, 7), "Snake Multiplayer", GUIText.CENTER);
	}
	
	protected GUIText addText(String font, int size, Vector2i position, String string, int anchor) {
		GUIText text = new GUIText(font, size);
		
		text.setPosition(position);
		text.setText(string);
		text.setAnchor(anchor);
		
		return (GUIText)addComponent(text);
	}
	
	protected void setErrorText(String string, int yPos) {
		removeComponent(errorText);
		
		errorText =  addText(PIXELMIX_FONT, 16, new Vector2i(Window.getWidth() / 2, yPos), string, GUIText.CENTER);
		
		errorText.setColor(Color.red);
		
		addComponent(errorText);
	}
	
	protected GUIButton addBackButton() {
		GUITextButton button = new GUITextButton(
			new Vector2i(Window.getWidth() - (GUITextButton.SIZE.x / 2) - 10, Window.getHeight() - 62),
			GUIButton.LEFT,
			new ButtonHandler() {
				@Override
				public void onClick() {
					SnakeMP.getInstance().stopServer();
					
					ClientConnection.destroy();
					Client.setActiveScreen(Screen.SCREEN_MAIN);
				}
			}
		);
		
		button.setText(24, "BACK");
		
		return (GUIButton)addComponent(button);
	}
}
