package com.snakybo.snakemp.client.screen;

public class ScreenJoin extends Screen {
	private static final ScreenJoin instance = new ScreenJoin();
	
	private ScreenJoin() {
		super();
		
		initComponents();
	}
	
	private void initComponents() {
		
	}
	
	public static ScreenJoin getInstance() {
		return instance;
	}
}
