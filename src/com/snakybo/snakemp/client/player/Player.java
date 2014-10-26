package com.snakybo.snakemp.client.player;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.Input.KeyCode;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.common.network.ENetworkMessages;

public class Player extends ClientData implements IUpdatable {
	public Player(int id) {
		super(id);
	}
	
	@Override
	public void update(Input input, float delta) {
		SnakePart head = getPart(0);
		
		if(input.isKeyDown(KeyCode.W) || input.isKeyDown(KeyCode.UP)) {
			if(head.getDirection() != 0) {
				head.setDirection(2);
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_DIRECTION, getId(), 2);
			}
		} else if(input.isKeyDown(KeyCode.A) || input.isKeyDown(KeyCode.LEFT)) {
			if(head.getDirection() != 1) {
				head.setDirection(3);
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_DIRECTION, getId(), 3);
			}
		} else if(input.isKeyDown(KeyCode.S) || input.isKeyDown(KeyCode.DOWN)) {
			if(head.getDirection() != 2) {
				head.setDirection(0);
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_DIRECTION, getId(), 0);
			}
		} else if(input.isKeyDown(KeyCode.D) || input.isKeyDown(KeyCode.RIGHT)) {
			if(head.getDirection() != 3) {
				head.setDirection(1);		
				ClientConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_DIRECTION, getId(), 1);
			}
		}
	}
}
