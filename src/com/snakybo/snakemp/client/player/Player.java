package com.snakybo.snakemp.client.player;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.core.Input.KeyCode;
import com.snakybo.snakemp.client.network.ClientConnection;
import com.snakybo.snakemp.client.world.ClientWorld;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.network.ENetworkMessages;

public class Player extends ClientData implements IUpdatable {
	private ClientData[] clients;
	
	public Player(int id) {
		super(id);
	}
	
	@Override
	public void update(Input input, float delta) {
		SnakePart head = getPart(0);
		
		if(clients == null)
			clients = SnakeMP.getInstance().getClient().getClientList().getClients();
		
		if(isAlive()) {
			if(ClientWorld.checkForCollision) {
				for(ClientData client : clients) {
					if(client != null) {
						for(int i = 0; i < client.getParts().length; i++) {
							SnakePart part = client.getPart(i);
							
							if(head.getX() == part.getX() && head.getY() == part.getY()) {
								if(client.getId() != getId()) {
									if(!part.isHead()) {
										ClientConnection.sendUDP(ENetworkMessages.CLIENT_STOLE_PARTS, client.getId(), i);
										
										for(int j = i; j < client.getParts().length; j++)
											ClientConnection.sendUDP(ENetworkMessages.CLIENT_GROWN, getId());
									} else {
										System.err.println("REASON DIED: Rammed another head");
										ClientConnection.sendUDP(ENetworkMessages.CLIENT_DIED, getId());
									}
								}
							}
						}
					}
				}
			}
			
			handleInput(input, head);
		}
	}
	
	private void handleInput(Input input, SnakePart head) {
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
