package com.snakybo.snakemp.client.player;

public class SnakePart {	
	private int x;
	private int y;
	
	private int oldX;
	private int oldY;
	
	private int direction;
	
	private int oldDirection;
	
	public SnakePart(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void setX(int x) {
		oldX = this.x;
		
		this.x = x;
	}
	
	public void setY(int y) {
		oldY = this.y;
		
		this.y = y;
	}
	
	public void setDirection(int direction) {
		oldDirection = this.direction;
		
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}
	
	public int getOldX() {
		return oldX;
	}
	
	public int getY() {
		return y;
	}
	
	public int getOldY() {
		return oldY;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getOldDirection() {
		return oldDirection;
	}
}
