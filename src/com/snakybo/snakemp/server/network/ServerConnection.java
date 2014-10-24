package com.snakybo.snakemp.server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import com.snakybo.sengine2d.network.UDPServer;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.client.ClientServerData;

/** @author Kevin Krol
 * @date Oct 24, 2014 */
public class ServerConnection {
	private static UDPServer udpServer;
	
	private static Server server;
	
	public static void initialize(Server server, int port) {
		if(udpServer != null)
			return;
		
		ServerConnection.server = server;
		
		try {
			ServerConnection.udpServer = new UDPServer(1337, (m, a, p)->onMessageReceived(m, a, p));
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		if(udpServer == null)
			return;
		
		// TODO: Send leave message
		//sendToAll(ENetworkMessages.SERVER_STOPPED, "");
		
		udpServer.close();
		udpServer = null;
	}
	
	public static void sendToAll(ENetworkMessages messageId, String message) {
		message = messageId.id() + "#" + message;
		
		for(ClientServerData client : server.getClientManager().getClients())
			sendToClient(client, messageId, message);
	}
	
	public static void sendToClient(ClientServerData client, ENetworkMessages messageId, String message) {
		if(client != null)
			sendToAddress(client.getAddress(), client.getPort(), messageId, message);
	}
	
	public static void sendToAddress(InetAddress address, int port, ENetworkMessages messageId, String message) {
		if(udpServer == null)
			return;
		
		message = messageId.id() + "#" + message;
		
		try {
			udpServer.send(address, port, message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void onMessageReceived(String message, InetAddress address, int port) {
		final String[] parts = message.split("#");
		final ENetworkMessages id = ENetworkMessages.toId(parts[0]);
		
		if(id == null)
			return;
		
		switch(id) {
		case CLIENT_REQUEST_JOIN:
			server.getClientManager().onClientRequestJoin(parts, address, port);
			break;
		case CLIENT_LEAVE:
			server.getClientManager().onClientLeave(parts[1]);
		default:
			System.err.println("Server received an invalid message ID: " + id);
			break;
		}
	}
}
