package com.snakybo.snakemp.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine2d.component.IRenderable;
import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.gui.GUIComponent;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Renderer;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.utils.math.Vector2f;
import com.snakybo.snakemp.common.Main;

public class Screen implements IUpdatable, IRenderable {
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
	
	protected void addComponent(GUIComponent component) {
		components.add(component);
	}
}
