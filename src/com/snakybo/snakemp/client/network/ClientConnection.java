package com.snakybo.snakemp.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.snakybo.sengine2d.network.UDPClient;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.client.screen.ScreenError;
import com.snakybo.snakemp.client.screen.ScreenMain;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.network.ENetworkMessages;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ClientConnection {
	private static UDPClient udpClient;
	
	private static Client client;
	
	public static void initialize(Client client, String address, int port) {
		if(udpClient != null)
			return;
		
		ClientConnection.client = client;
		
		try {
			udpClient = new UDPClient(InetAddress.getByName(address), port, (message) -> {
				onMessageReceived(message);
			});
			
			ClientData data = client.getClientData();
			
			send(ENetworkMessages.CLIENT_REQUEST_JOIN, data.getName() + "#" + data.getColor().x + "#" + data.getColor().y + "#" + data.getColor().z);
		} catch(SocketException e) {
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		if(udpClient == null)
			return;
		
		// TODO: Send message on leave
		//send(ENetworkMessages.CLIENT_LEAVE, "");
		
		udpClient.close();
		udpClient = null;
	}
	
	public static void send(ENetworkMessages type, String message) {
		if(udpClient == null)
			return;
			
		message = type.id() + "#" + message;
		
		try {
			udpClient.send(message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void onMessageReceived(String message) {
		final String[] parts = message.split("#");
		final ENetworkMessages id = ENetworkMessages.toId(parts[0]);
		
		if(id == null)
			return;
		
		switch(id) {
		case SERVER_WELCOME_CLIENT:
			client.onServerJoin(parts[1]);
			break;
		case SERVER_REJECT_CLIENT_EXISTS:
		case SERVER_REJECT_CLIENT_PLAYING:
		case SERVER_REJECT_CLIENT_FULL:
			Client.setActiveScreen(ScreenError.class);
			break;
		case SERVER_CLIENT_LEFT:
			System.out.println("Client left: " + parts[1]);
			break;
		case SERVER_STOPPED:
			destroy();
			Client.setActiveScreen(ScreenMain.class);
		default:
			System.err.println("Client received an invalid message ID: " + id);
			break;
		}
	}
}
