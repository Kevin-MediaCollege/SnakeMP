package com.snakybo.snakemp.server.client;

import java.net.InetAddress;

import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.ServerLog;
import com.snakybo.snakemp.server.network.ServerConnection;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ClientServerData {
	private final int id;
	
	private InetAddress address;
	
	private Vector3f color;
	
	private String name;
	
	private int direction;
	private int port;
	private int x;
	private int y;
	
	private boolean isDoneLoading;
	private boolean isConnected;
	private boolean isAlive;
	private boolean isReady;
	
	public ClientServerData(int id, String name, InetAddress address, int port) {
		this.id = id;
		
		this.name = name;
		this.address = address;
		this.port = port;
		
		isDoneLoading = false;
		isConnected = true;
		isAlive = true;
		isReady = false;
	}
	
	public void onColorChange(float r, float g, float b) {
		this.color = new Vector3f(r, g, b);
		
		ServerConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_COLOR, id, r, g, b);
	}
	
	public void onDoneLoading() {
		isDoneLoading = true;
	}
	
	public void onDisconnected() {
		isConnected = false;
		
		ServerConnection.sendUDP(ENetworkMessages.SERVER_CLIENT_LEFT, id);
		ServerLog.log(name + " left.");
	}
	
	public void onReadyChange(boolean isReady) {
		this.isReady = isReady;
		
		ServerConnection.sendUDP(ENetworkMessages.CLIENT_UPDATE_READY, id, (isReady ? 1 : 0));
		ServerLog.log(name + " is now " + (isReady ? "" : "not ") + "ready.");
	}
	
	public void onDead() {
		if(!isAlive)
			return;
		
		this.isAlive = false;
		
		ServerConnection.sendUDP(ENetworkMessages.CLIENT_DIED, id);
		ServerLog.log(name + " died");
		
		Server server = SnakeMP.getInstance().getServer();
		
		ClientServerData lastOneAlive = null;
		
		for(ClientServerData client : server.getClientManager().getClients()) {
			if(client != null) {
				if(client.isAlive()) {
					if(lastOneAlive == null) { 
						lastOneAlive = client;
					} else {
						lastOneAlive = null;
						break;
					}
				}
			}
		}
		
		if(lastOneAlive != null)
			server.getServerWorld().endGame(lastOneAlive.getId());
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getId() {
		return id;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean isDoneLoading() {
		return isDoneLoading;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isReady() {
		return isReady;
	}
}
