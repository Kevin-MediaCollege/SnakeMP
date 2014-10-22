package com.snakybo.snakemp.client.screen;

import com.snakybo.sengine2d.gui.GUIButton;
import com.snakybo.sengine2d.gui.GUIText;
import com.snakybo.sengine2d.rendering.Window;
import com.snakybo.sengine2d.resource.Texture;
import com.snakybo.sengine2d.utils.Bounds;
import com.snakybo.sengine2d.utils.Vector2f;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.Main;

public class ScreenMain extends Screen {
	private static final ScreenMain instance = new ScreenMain();
	
	private final Texture buttonHostNormal;
	private final Texture buttonHostHover;
	private final Texture buttonHostPress;
	
	private final Texture buttonJoinNormal;
	private final Texture buttonJoinHover;
	private final Texture buttonJoinPress;
	
	private final Texture buttonQuitNormal;
	private final Texture buttonQuitHover;
	private final Texture buttonQuitPress;
	
	private ScreenMain() {
		super();
		
		buttonHostNormal = new Texture("Button_Host_Normal.png");
		buttonHostHover = new Texture("Button_Host_Hover.png");
		buttonHostPress = new Texture("Button_Host_Press.png");
		
		buttonJoinNormal = new Texture("Button_Join_Normal.png");
		buttonJoinHover = new Texture("Button_Join_Hover.png");
		buttonJoinPress = new Texture("Button_Join_Press.png");
		
		buttonQuitNormal = new Texture("Button_Quit_Normal.png");
		buttonQuitHover = new Texture("Button_Quit_Hover.png");
		buttonQuitPress = new Texture("Button_Quit_Press.png");
		
		initComponents();
	}
	
	private void initComponents() {
		Vector2f size = new Vector2f(Window.getWidth(), Window.getHeight());
		
		GUIText titleText = new GUIText("Comic Sans.ttf", 48);
		titleText.setPosition(new Vector2f(size.x / 2, 40));
		titleText.setText("Snake Multiplayer");
		titleText.setAnchor(GUIText.CENTER);
		
		GUIButton hostButton = new GUIButton(	new Bounds((int)(size.x / 2), (int)(size.x / 2) + 250, 100, 175),
												buttonHostNormal,
												buttonHostHover,
												buttonHostPress,
												GUIButton.CENTER,
												() -> {
													Client.setActiveMenu(ScreenHost.getInstance());
												}
											);
		
		GUIButton joinButton = new GUIButton(	new Bounds((int)(size.x / 2), (int)(size.x / 2) + 250, 200, 275),
												buttonJoinNormal,
												buttonJoinHover,
												buttonJoinPress,
												GUIButton.CENTER,
												() -> {
													Client.setActiveMenu(ScreenJoin.getInstance());
												}
											);
		
		GUIButton quitButton = new GUIButton(	new Bounds((int)(size.x / 2), (int)(size.x / 2) + 250, 300, 375),
												buttonQuitNormal,
												buttonQuitHover,
												buttonQuitPress,
												GUIButton.CENTER,
												() -> {
													Main.stop();
												}
											);
		
		addComponent(titleText);
		
		addComponent(hostButton);
		addComponent(joinButton);
		addComponent(quitButton);
	}
	
	public static ScreenMain getInstance() {
		return instance;
	}
}
