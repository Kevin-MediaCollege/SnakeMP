package com.snakybo.snakemp.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.snakybo.sengine2d.network.UDPClient;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.common.data.ClientData;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.Screen;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ClientConnection {
	private static UDPClient udpClient;
	
	private static Client client;
	
	public static void initialize(Client client) {
		if(udpClient != null)
			return;
		
		ClientConnection.client = client;
		
		try {
			System.out.println("Connecting to server UDP socket: " + Config.serverAddress + ":" + Config.udpPort);
			udpClient = new UDPClient(InetAddress.getByName(Config.serverAddress), Config.udpPort, (m)->onUDPMessageReceived(m));
			
			final ClientData data = client.getData();
			
			sendUDP(ENetworkMessages.CLIENT_REQUEST_JOIN,
					data.getName(),
					String.valueOf(data.getColor().x),
					String.valueOf(data.getColor().y),
					String.valueOf(data.getColor().z)
				);
		} catch(SocketException e) {
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void destroy() {
		if(udpClient == null)
			return;
		
		System.out.println("Leaving lobby");
		
		sendUDP(ENetworkMessages.CLIENT_LEAVE, String.valueOf(client.getData().getId()));
		
		udpClient.close();
		udpClient = null;
	}
	
	public static void sendUDP(ENetworkMessages type, String... messages) {
		if(udpClient == null || !udpClient.isOpen())
			return;
			
		String message = type.id() + "#";
		
		for(int i = 0; i < messages.length; i++)
			message += (messages[i] + "#");
		
		try {
			udpClient.send(message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void onUDPMessageReceived(String message) {
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
			Screen.SCREEN_ERROR.setErrorMessage(id);
			Client.setActiveScreen(Screen.SCREEN_ERROR);
			break;
		case SERVER_CLIENT_LEFT:
			client.getClientList().onClientLeave((int)Float.parseFloat(parts[1]));
			break;
		case SERVER_CLIENT_JOINED:
			client.getClientList().onClientJoin(parts);
			break;
		case SERVER_CLIENT_UPDATE_READY:
			client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).setIsReady((int)Float.parseFloat(parts[2]) == 0 ? false : true);
			break;
		case SERVER_STOPPED:
			destroy();
			Client.setActiveScreen(Screen.SCREEN_MAIN);
			break;
		default:
			System.err.println("[UDP] Client received an invalid message ID: " + id);
			break;
		}
	}
}
