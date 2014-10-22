package com.snakybo.snakemp.common.network;

public enum ENetworkMessages {
	// Lobby client to server messages
	CLIENT_UPDATE_READY					(0x01),
	CLIENT_UPDATE_CHAT					(0x02),
	
	// Lobby server to client messages
	SERVER_WELCOME_CLIENT				(0x03),
	
	SERVER_ACCEPT_CLIENT				(0x04),
	
	SERVER_REJECT_CLIENT_FULL			(0x05),
	SERVER_REJECT_CLIENT_INVALID_NAME	(0x06),
	SERVER_REJECT_CLIENT_PLAYING		(0x07),
	
	SERVER_CLIENT_KICK					(0x08),
	
	SERVER_UPDATE_LOBBY_LIST			(0x09),
	SERVER_UPDATE_LOBBY_CHAT			(0x10),
	
	SERVER_PREPARE_MATCH_START			(0x11),
	SERVER_PREPARE_MATCH_END			(0x12),
	
	SERVER_MATCH_START					(0x13),
	SERVER_MATCH_END					(0x14),
	
	// Game client to server messages
	CLIENT_SNAKE_UPDATE					(0x15),
	CLIENT_SANKE_DEATH					(0x16),
	
	CLENT_REQUEST_PICKUP				(0x17),
	
	// Game server to client messages
	SERVER_SNAKE_POSITION				(0x18),
	SERVER_SNAKE_UPDATE					(0x19),
	SERVER_SNAKE_DIED					(0x20),
	SERVER_SNAKE_GROWN					(0x21),
	SERVER_SNAKE_SPAWNED				(0x22),
	
	SERVER_NEW_PICKUP					(0x23),
	
	// Keep-alive message
	KEEP_ALIVE							(0x99);
	
	private final int id;
	
	private ENetworkMessages(int id) {
		this.id = id;
	}
	
	public int id() {
		return id;
	}
}
