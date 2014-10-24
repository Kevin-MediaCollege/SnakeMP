package com.snakybo.snakemp.server.world;

/** @author Kevin Krol
 * @date Oct 24, 2014 */
public class ServerWorld {
	private boolean isIngame;
	
	public ServerWorld() {
		isIngame = false;
	}
	
	public void createWorld() {
		isIngame = true;
	}
	
	public boolean isIngame() {
		return isIngame;
	}
}
