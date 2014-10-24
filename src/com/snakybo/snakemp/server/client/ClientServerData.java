package com.snakybo.snakemp.server.client;

import java.net.InetAddress;

import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.server.ServerLogger;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ClientServerData extends ClientData {
	private InetAddress address;
	
	private int port;
	
	private boolean isDisconnected;
	private boolean hasLoaded;
	
	public ClientServerData(int id, String name, InetAddress address, int port) {
		super();
		
		setId(id);
		setName(name);
		
		this.address = address;
		this.port = port;
		
		isDisconnected = false;
		hasLoaded = false;
	}
	
	public void onDisconnect() {
		ServerLogger.log(getName() + " disconnected!");
		
		SnakeMultiplayer.getInstance().getClient().destroy();
	}
	
	public void setHasLoaded(boolean hasLoaded) {
		this.hasLoaded = hasLoaded;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean isDisconnected() {
		return isDisconnected;
	}
	
	public boolean hasLoaded() {
		return hasLoaded;
	}
}
