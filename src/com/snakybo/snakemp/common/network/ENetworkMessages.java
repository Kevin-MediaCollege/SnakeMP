package com.snakybo.snakemp.common.network;

public enum ENetworkMessages {
	CLIENT_REQUEST_JOIN                 (0x01),    // Format: 1[name]
	CLIENT_LEAVE                        (0x02),    // Format: 1[id]

	CLIENT_UPDATE_READY                 (0x03),    // Format: 1[id] 2[ready (0/1)]
	CLIENT_UPDATE_COLOR                 (0x04),    // Format: 1[id] 2[color.r] 3[color.g] 4[color.b]
	CIENT_UPDATE                        (0x05),    // Format: 1[id] 2[x] 3[y] 4[direction]
	CLIENT_UPDATE_DIRECTION             (0x06),    // Format: 1[id] 2[direction]
	
	CLIENT_LOADED                       (0x07),    // Format: 1[id]
	CLIENT_SPAWNED                      (0x08),    // Format: 1[id] 2-X[x;y;dir/]
	
	SERVER_REJECT_CLIENT_FULL           (0x09),
	SERVER_REJECT_CLIENT_PLAYING        (0x10),
	SERVER_REJECT_CLIENT_EXISTS         (0x11),
	
	SERVER_WELCOME_CLIENT               (0x12),    // Format: 1[id]
	
	SERVER_CLIENT_JOINED                (0x13),    // Format: 1[id] 2[name]
	SERVER_CLIENT_LEFT                  (0x14),    // Format: 1[id]
	SERVER_CLIENT_INFO                  (0x15),    // Format: 1[id] 2[name] 3[color.r] 4[color.g] 5[color.b]
	
	SERVER_COUNTDOWN_CHANGE             (0x16),    // Format: 1[state (0/1)]
	SERVER_START_GAME                   (0x17),
	
	CLIENT_STOLE_PARTS                  (0x18),    // Format: 2[id] 3[fromIndex]
	CLIENT_DIED                         (0x19),    // Format: 1[id]
	CLIENT_GROWN                        (0x20),    // Format: 1[id]
	
	SERVER_END                          (0x21);    // Format: 1[id]
	
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
