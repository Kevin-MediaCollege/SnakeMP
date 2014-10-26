package com.snakybo.snakemp.client.world;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.client.player.SnakePart;
import com.snakybo.snakemp.common.data.Config;

public class ClientWorld implements IUpdatable {
	public static boolean checkForCollision = false;
	
	private ClientData[] clientData;
	
	private int ticksSinceUpdate;
	
	private int collisionCycles;
	
	private boolean shouldUpdate;
	
	public ClientWorld(ClientData[] clientData) {
		this.clientData = clientData;
		
		ticksSinceUpdate = 0;
		collisionCycles = 0;
		shouldUpdate = true;
	}

	public void update(Input input, float delta) {
		ticksSinceUpdate++;
		
		if(ticksSinceUpdate >= Config.GAME_UPDATE_RATE) {
			if(collisionCycles <= 5) {
				collisionCycles++;
			} else {
				checkForCollision = true;
			}
			
			for(ClientData client : clientData) {
				if(client != null) {
					for(int i = 1; i < client.getParts().length; i++) {
						SnakePart part = client.getParts()[i];
						SnakePart prevPart = client.getParts()[i - 1];
						
						if(i > 1) {
							part.setX(prevPart.getOldX());
							part.setY(prevPart.getOldY());
							part.setDirection(prevPart.getOldDirection());
						} else {
							part.setX(prevPart.getX());
							part.setY(prevPart.getY());
							part.setDirection(prevPart.getDirection());
						}
					}
				}
			}
			
			ticksSinceUpdate = 0;
		}
	}
	
	public void end() {
		shouldUpdate = false;
	}
	
	public boolean should() {
		return shouldUpdate;
	}
}
