package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.resource.Texture;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class Textures {
	public static Texture BUTTON_HOST_NORMAL;
	public static Texture BUTTON_HOST_HOVER;
	public static Texture BUTTON_HOST_PRESS;
	
	public static Texture BUTTON_JOIN_NORMAL;
	public static Texture BUTTON_JOIN_HOVER;
	public static Texture BUTTON_JOIN_PRESS;
	
	public static Texture BUTTON_QUIT_NORMAL;
	public static Texture BUTTON_QUIT_HOVER;
	public static Texture BUTTON_QUIT_PRESS;
	
	public static Texture BUTTON_CREATE_SERVER_NORMAL;
	public static Texture BUTTON_CREATE_SERVER_HOVER;
	public static Texture BUTTON_CREATE_SERVER_PRESS;
	
	public static Texture BUTTON_BACK_NORMAL;
	public static Texture BUTTON_BACK_HOVER;
	public static Texture BUTTON_BACK_PRESS;
	
	public static void initialize() {
		BUTTON_HOST_NORMAL = new Texture("Button_Host_Normal.png");
		BUTTON_HOST_HOVER = new Texture("Button_Host_Hover.png");
		BUTTON_HOST_PRESS = new Texture("Button_Host_Press.png");
		
		BUTTON_JOIN_NORMAL = new Texture("Button_Join_Normal.png");
		BUTTON_JOIN_HOVER = new Texture("Button_Join_Hover.png");
		BUTTON_JOIN_PRESS = new Texture("Button_Join_Press.png");
		
		BUTTON_QUIT_NORMAL = new Texture("Button_Quit_Normal.png");
		BUTTON_QUIT_HOVER = new Texture("Button_Quit_Hover.png");
		BUTTON_QUIT_PRESS = new Texture("Button_Quit_Press.png");
		
		BUTTON_CREATE_SERVER_NORMAL = new Texture("Button_CreateServer_Normal.png");
		BUTTON_CREATE_SERVER_HOVER = new Texture("Button_CreateServer_Hover.png");
		BUTTON_CREATE_SERVER_PRESS = new Texture("Button_CreateServer_Press.png");
		
		BUTTON_BACK_NORMAL = new Texture("Button_Back_Normal.png");
		BUTTON_BACK_HOVER = new Texture("Button_Back_Hover.png");
		BUTTON_BACK_PRESS = new Texture("Button_Back_Press.png");
	}
}