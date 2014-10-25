package com.snakybo.snakemp.common.data;

import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;

public class ClientData {
	private int id;
	
	private Vector3f color;
	private Vector2i position;
	
	private String name;
	
	private int length;
	private int ping;
	
	private boolean isAlive;
	private boolean isReady;
	
	public ClientData() {
		id = 0;
		name = Config.playerName;
		color = new Vector3f((float)Math.random(), (float)Math.random(), (float)Math.random());
		position = new Vector2i();
		length = 1;
		ping = 999;
		
		isAlive = false;
		isReady = false;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public void setPosition(Vector2i position) {
		this.position = position;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setPing(int ping) {
		this.ping = ping;
	}
	
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public int getId() {
		return id;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getPing() {
		return ping;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isReady() {
		return isReady;
	}
}
