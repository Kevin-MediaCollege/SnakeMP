package com.snakybo.snakemp.server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import com.snakybo.sengine2d.network.UDPServer;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.server.Server;
import com.snakybo.snakemp.server.ServerLogger;
import com.snakybo.snakemp.server.client.ClientServerData;

/** @author Kevin Krol
 * @date Oct 24, 2014 */
public class ServerConnection {
	private static UDPServer udpServer;
	private static TCPServer tcpServer;
	
	private static Server server;
	
	public static void initialize(Server server, int udpPort, int tcpPort) {
		if(udpServer != null)
			return;
		
		ServerConnection.server = server;
		
		try {
			udpServer = new UDPServer(udpPort, (m, a, p)->onUDPMessageReceived(m, a, p));
			ServerLogger.log("UDP server started");
			
			//tcpServer = new TCPServer(tcpPort, (m, s)->onTCPMessageReceived(m, s));
			ServerLogger.log("TCP server started");
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		if(udpServer == null || tcpServer == null)
			return;
		
		sendUDP(ENetworkMessages.SERVER_STOPPED);
		
		tcpServer.close();
		tcpServer = null;
		
		udpServer.close();
		udpServer = null;
	}
	
	public static void sendUDP(ENetworkMessages messageId, String... messages) {
		final ClientServerData[] clients = server.getClientManager().getClients();
		
		for(int i = 0; i < clients.length; i++)
			sendUDP(clients[i], messageId, messages);
	}
	
	public static void sendUDP(ClientServerData client, ENetworkMessages messageId, String... messages) {
		if(client != null)
			sendUDP(client.getAddress(), client.getPort(), messageId, messages);
	}
	
	public static void sendUDP(InetAddress address, int port, ENetworkMessages messageId, String... messages) {
		if(udpServer == null || !udpServer.isOpen())
			return;
		
		String message = messageId.id() + "#";
		
		for(int i = 0; i < messages.length; i++)
			message += (messages[i] + "#");
		
		try {
			udpServer.send(address, port, message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void sendTCP(ENetworkMessages messageId, String... messages) {
		final ClientServerData[] clients = server.getClientManager().getClients();
		
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null)
				ServerConnection.sendUDP(clients[i].getAddress(), clients[i].getPort(), messageId, messages);
	}
	
	public static void sendTCP(Socket socket, ENetworkMessages messageId, String... messages) {
		if(tcpServer == null || !tcpServer.isOpen())
			return;
		
		String message = messageId.id() + "#";
		
		for(int i = 0; i < messages.length; i++)
			message += (messages[i] + "#");
		
		tcpServer.send(message, socket);
	}*/
	
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
		default:
			System.err.println("[UDP] Server received an invalid message ID: " + id);
			break;
		}
	}
	
	/*private static void onTCPMessageReceived(String message, Socket socket) {
		final String[] parts = message.split("#");
		final ENetworkMessages id = ENetworkMessages.toId(parts[0]);
		
		if(id == null)
			return;
		
		switch(id) {
		case CLIENT_REQUEST_JOIN:
			server.getClientManager().onClientRequestJoin(parts, socket);
			break;
		case CLIENT_LEAVE:
			server.getClientManager().onClientLeave(parts[1]);
			break;
		case CLIENT_UPDATE_READY:
			server.getClientManager().onClientReadyChange(parts[1], parts[2]);
			break;
		default:
			System.err.println("[TCP] Server received an invalid message ID: " + id);
			break;
		}
	}*/
}
