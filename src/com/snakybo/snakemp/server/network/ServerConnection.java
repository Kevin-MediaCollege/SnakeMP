package com.snakybo.snakemp.server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import com.snakybo.sengine2d.network.UDPServer;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.SnakeMultiplayer;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.Screen;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.ServerLog;
import com.snakybo.snakemp.server.client.ClientServerData;

/** @author Kevin Krol
 * @date Oct 24, 2014 */
public class ServerConnection {
	private static UDPServer udpServer;
	
	private static Server server;
	
	public static void initialize(Server server) {
		if(udpServer != null)
			return;
		
		ServerConnection.server = server;
		
		ServerLog.log("Starting server");
		
		try {
			udpServer = new UDPServer(Config.udpPort, (m, a, p)->onUDPMessageReceived(m, a, p));
		} catch(SocketException e) {
			if(udpServer != null) {
				if(udpServer.isOpen())
					udpServer.close();
				
				udpServer = null;
			}
			
			SnakeMultiplayer.getInstance().getClient().destroy();
			
			Screen.SCREEN_ERROR.setErrorMessage("Unable to create server: " + e.getLocalizedMessage());
			Client.setActiveScreen(Screen.SCREEN_ERROR);
		}
		
		ServerLog.log("Server listening on UDP port: " + Config.udpPort);
		
		ServerLog.log("Server started");
	}
	
	public static void destroy() {
		if(udpServer == null)
			return;
		
		udpServer.close();
		udpServer = null;
	}
	
	public static void sendUDP(ENetworkMessages messageId, Object... messages) {
		final ClientServerData[] clients = server.getClientManager().getClients();
		
		for(int i = 0; i < clients.length; i++)
			sendUDP(clients[i], messageId, messages);
	}
	
	public static void sendUDP(ClientServerData client, ENetworkMessages messageId, Object... messages) {
		if(client != null)
			sendUDP(client.getAddress(), client.getPort(), messageId, messages);
	}
	
	public static void sendUDP(InetAddress address, int port, ENetworkMessages messageId, Object... messages) {
		if(udpServer == null || !udpServer.isOpen())
			return;
		
		String message = messageId.id() + "#";
		
		for(int i = 0; i < messages.length; i++)
			message += (String.valueOf(messages[i]) + "#");
		
		try {
			udpServer.send(address, port, message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void onUDPMessageReceived(String message, InetAddress address, int port) {
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
			break;
		case CLIENT_UPDATE_READY:
			server.getClientManager().onClientReadyChange(parts[1], parts[2]);
			break;
		case CLIENT_UPDATE_COLOR:
			server.getClientManager().onClientColorChange(parts[1], parts[2], parts[3], parts[4]);
			break;
		case CLIENT_LOADED:
			server.getClientManager().onClientLoaded(parts[1]);
			break;
		case CLIENT_UPDATE_DIRECTION:
			server.getClientManager().getClient((int)Float.parseFloat(parts[1])).setDirection((int)Float.parseFloat(parts[2]));
			sendUDP(ENetworkMessages.CLIENT_UPDATE_DIRECTION, parts[1], parts[2]);
			break;
		default:
			System.err.println("[UDP] Server received an invalid message ID: " + id);
			break;
		}
	}
}
