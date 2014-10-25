package com.snakybo.snakemp.common.data;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.world.SnakePart;
import com.snakybo.snakemp.common.util.Utils;

public class ClientData {
	private final int id;
	
	private List<SnakePart> parts;
	
	private Vector3f color;
	
	private String name;
	
	private int x;
	private int y;
	private int direction;
	
	private int length;
	
	private boolean isReady;
	private boolean isAlive;
	
	public ClientData(int id) {
		this(id, Config.playerName, new Vector3f(Utils.randFloat(0.3f, 0.7f), Utils.randFloat(0.3f, 0.7f), Utils.randFloat(0.3f, .7f)));
	}
	
	public ClientData(int id, String name, Vector3f color) {
		parts = new ArrayList<SnakePart>();
		
		this.id = id;
		this.name = name;
		this.color = color;
		this.length = 7;
		
		this.isReady = false;
		this.isAlive = true;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public int getId() {
		return id;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLength() {
		return length;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
}
