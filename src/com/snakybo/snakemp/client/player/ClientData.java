package com.snakybo.snakemp.client.player;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.util.Utils;

public class ClientData {
	private final int id;
	
	protected List<SnakePart> parts;
	
	private Vector3f color;
	
	private String name;
	
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
		this.length = Config.START_SNAKE_LENGTH;
		
		this.isReady = false;
		this.isAlive = true;
	}
	
	public void spawn(int x, int y, int direction) {
		parts.clear();
		System.out.println(x + " " + y);
		
		addPart(x, y, direction);
		
		for(int i = 1; i < getLength(); i++) {
			int pX = x;
			int pY = y;
			
			switch(direction) {
			case 0:
				pY = y - i;
				break;
			case 1:
				pX = x - i;
				break;
			case 2:
				pY = y + i;
				break;
			case 3:
				pX = x + i;
				break;
			}
			
			addPart(pX, pY, direction);
		}
	}
	
	public void addPart(int x, int y, int direction) {
		parts.add(new SnakePart(x, y, direction));
	}
	
	public void update(int x, int y, int direction) {
		parts.get(0).setX(x);
		parts.get(0).setY(y);
		parts.get(0).setDirection(direction);
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
	
	public SnakePart getPart(int index) {
		if(parts.size() > 0)
			return parts.get(index);
		
		return null;
	}
	
	public SnakePart[] getParts() {
		return parts.toArray(new SnakePart[parts.size()]);
	}
	
	public int getId() {
		return id;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector2i getPosition() {
		return new Vector2i(parts.get(0).getX(), parts.get(0).getY());
	}
	
	public String getName() {
		return name;
	}
	
	public int getDirection() {
		return parts.get(0).getDirection();
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
