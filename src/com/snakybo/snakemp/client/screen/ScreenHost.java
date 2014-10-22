package com.snakybo.snakemp.client.screen;

public class ScreenHost extends Screen {
	private static final ScreenHost instance = new ScreenHost();
	
	private ScreenHost() {
		super();
		
		initComponents();
	}
	
	private void initComponents() {
		
	}
	
	public static ScreenHost getInstance() {
		return instance;
	}
}
