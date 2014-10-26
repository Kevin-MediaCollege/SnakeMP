package com.snakybo.snakemp.server.world;

import com.snakybo.sengine2d.component.IUpdatable;
import com.snakybo.sengine2d.core.Input;
import com.snakybo.sengine2d.utils.math.Vector2i;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.client.ClientServerData;
import com.snakybo.snakemp.server.network.ServerConnection;

/** @author Kevin Krol
 * @date Oct 24, 2014 */
public class ServerWorld implements IUpdatable {
	private Server server;
	
	private boolean isIngame;
	
	private int ticksSinceUpdate;
	
	public ServerWorld(Server server) {
		this.server = server;
		
		isIngame = false;
		
		ticksSinceUpdate = 0;
	}
	
	public void spawnPlayers(ClientServerData[] clients) {
		Vector2i[] positions = new Vector2i[clients.length];
		
		for(ClientServerData client : clients) {
			if(client != null) {
				Vector2i position = null;
				
				while(true) {
					position = generatePosition();
					
					boolean contains = false;
					
					for(Vector2i pos : positions) {
						if(pos != null) {
							if(pos.x != position.x && pos.y != position.y) {
								contains = true;
								break;
							}
						}
					}
					
					if(!contains)
						break;
				}
				
				int direction = (int)(1 + (Math.random() * (4 - 1)));
				
				
				client.setX(position.x);
				client.setY(position.y);
				client.setDirection(direction);
				
				ServerConnection.sendUDP(ENetworkMessages.CLIENT_SPAWNED, client.getId(), position.x, position.y, direction);
			}
		}
		
		ServerConnection.sendUDP(ENetworkMessages.SERVER_START_GAME);
		isIngame = true;
	}
	
	@Override
	public void update(Input input, float delta) {
		ticksSinceUpdate++;
		
		if(ticksSinceUpdate >= Config.GAME_UPDATE_RATE) {
			for(int i = 0; i < Config.MAX_CLIENTS; i++) {
				ClientServerData client = server.getClientManager().getClient(i);
				
				if(client != null) {
					//System.out.println("swag " + j++);
					switch(client.getDirection()) {
					case 0:
						client.setY(client.getY() + 1);
						break;
					case 1:
						client.setX(client.getX() + 1);
						break;
					case 2:
						client.setY(client.getY() - 1);
						break;
					case 3:
						client.setX(client.getX() - 1);
						break;
					}
					
					ServerConnection.sendUDP(ENetworkMessages.CIENT_UPDATE, client.getId(), client.getX(), client.getY(), client.getDirection());
				}
			}
			
			ticksSinceUpdate = 0;
		}
	}
	
	private Vector2i generatePosition() {
		final int MIN_X = Config.START_SNAKE_LENGTH;
		final int MIN_Y = Config.START_SNAKE_LENGTH;
		
		final int MAX_X = Config.GRID_WIDTH_HEIGHT.x - Config.START_SNAKE_LENGTH;
		final int MAX_Y = Config.GRID_WIDTH_HEIGHT.y - Config.START_SNAKE_LENGTH;
		
		int xPos = (int)(MIN_X + (Math.random() * (MAX_X - MIN_X)));
		int yPos = (int)(MIN_Y + (Math.random() * (MAX_Y - MIN_Y)));
		
		return new Vector2i(xPos, yPos);
	}
	
	public boolean isIngame() {
		return isIngame;
	}
}
