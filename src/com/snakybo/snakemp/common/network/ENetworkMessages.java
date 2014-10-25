package com.snakybo.snakemp.common.network;

public enum ENetworkMessages {
	CLIENT_REQUEST_JOIN                 (0x01),    // Format: 1[name]
	CLIENT_LEAVE                        (0x02),    // Format: 1[id]

	CLIENT_UPDATE_READY                 (0x03),    // Format: 1[id] 2[ready (0/1)]
	CLIENT_UPDATE_COLOR                 (0x04),    // Format: 1[id] 2[color.r] 3[color.g] 4[color.b]
	
	CLIENT_LOADED                       (0x05),    // Format: 1[id]
	
	SERVER_REJECT_CLIENT_FULL           (0x06),
	SERVER_REJECT_CLIENT_PLAYING        (0x07),
	SERVER_REJECT_CLIENT_EXISTS         (0x08),
	
	SERVER_WELCOME_CLIENT               (0x09),    // Format: 1[id]
	
	SERVER_CLIENT_JOINED                (0x10),    // Format: 1[id] 2[name]
	SERVER_CLIENT_LEFT                  (0x11),    // Format: 1[id]
	SERVER_CLIENT_INFO                  (0x12),    // Format: 1[id] 2[name] 3[color.r] 4[color.g] 5[color.b]
	
	SERVER_COUNTDOWN_CHANGE             (0x13),    // Format: 1[state (0/1)]
	SERVER_START_GAME                   (0x14);
	
	/*// Lobby client to server messages
	CLIENT_REQUEST_JOIN					(0x24),
	
	CLIENT_UPDATE_READY                 (0x01),
	CLIENT_UPDATE_CHAT                  (0x02),
	
	// Lobby server to client messages
	SERVER_WELCOME_CLIENT               (0x03),
	
	SERVER_ACCEPT_CLIENT                (0x04),
	
	SERVER_REJECT_CLIENT_FULL			(0x05),
	SERVER_REJECT_CLIENT_INVALID_NAME	(0x06),
	SERVER_REJECT_CLIENT_PLAYING		(0x07),
	SERVER_REJECT_CLIENT_ANOTHER_INSTANCE	(0x25),
	
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
	KEEP_ALIVE							(0x99);*/
	
	private final int id;
	
	private ENetworkMessages(int id) {
		this.id = id;
	}
	
	public int id() {
		return id;
	}
	
	public static ENetworkMessages toId(String stringId) {
		if(stringId != null) {
			final ENetworkMessages[] values = values();
			final int intId = Integer.parseInt(stringId);			
			
			for(ENetworkMessages message : values)
				if(intId == message.id())
					return message;
		}
		
		return null;
	}
}
