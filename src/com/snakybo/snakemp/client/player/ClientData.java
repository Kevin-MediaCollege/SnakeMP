package com.snakybo.snakemp.client.player;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
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
		
		addPart(x, y, direction, true);
		
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
			
			addPart(pX, pY, direction, false);
		}
	}
	
	public void steal(int from) {
		for(int i = from; i < parts.size(); i++) 
			parts.remove(i);
		
		if(parts.size() <= 3) {
			System.err.println("REASON DIED: parts <= 3");
			ClientConnection.sendUDP(ENetworkMessages.CLIENT_DIED, getId());
		}
	}
	
	public void addPart(int x, int y, int direction, boolean isHead) {
		parts.add(new SnakePart(x, y, direction, isHead));
	}
	
	public void addLastPart() {
		SnakePart last = parts.get(parts.size() - 1);
		
		int x = last.getX();
		int y = last.getY();
		int direction = last.getDirection();
		
		switch(direction) {
		case 0:
			y -= 1;
			break;
		case 1:
			x =- 1;
			break;
		case 2:
			y += 1;
			break;
		case 3:
			x += 1;
			break;
		}
		
		addPart(x, y, direction, false);
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
