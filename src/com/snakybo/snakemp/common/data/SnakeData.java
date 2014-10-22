package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.utils.Vector2i;
import com.snakybo.sengine2d.utils.Vector3f;

public class SnakeData {
	private final int id;
	
	private Vector3f color;
	private Vector2i position;
	
	private int length;
	
	public SnakeData(int id) {
		this.id = id;
		
		color = new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random());
		position = new Vector2i();
		length = 1;
	}
	
	public void grow() {
		length++;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public void setPosition(Vector2i position) {
		this.position = position;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public int getId() {
		return id;
	}
	
	public int getLength() {
		return length;
	}
}
