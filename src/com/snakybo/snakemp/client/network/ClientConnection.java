package com.snakybo.snakemp.client.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.Timer;

import com.snakybo.sengine2d.network.UDPClient;
import com.snakybo.sengine2d.network.UDPClient.ClientMessageHandler;
import com.snakybo.sengine2d.utils.math.Vector3f;
import com.snakybo.snakemp.client.Client;
import com.snakybo.snakemp.client.player.ClientData;
import com.snakybo.snakemp.common.SnakeMP;
import com.snakybo.snakemp.common.data.Config;
import com.snakybo.snakemp.common.network.ENetworkMessages;
import com.snakybo.snakemp.common.screen.Screen;

/** @author Kevin Krol
 * @date Oct 23, 2014 */
public class ClientConnection {
	private static UDPClient udpClient;
	
	private static Client client;
	
	private static boolean isConnected = false;
	
	public static void initialize(Client client) {
		if(udpClient != null)
			return;
		
		ClientConnection.client = client;
		
		try {
			System.out.println("Connecting to: " + Config.serverAddress);
			udpClient = new UDPClient(InetAddress.getByName(Config.serverAddress), Config.udpPort, new UDPClientAction());
			
			sendUDP(ENetworkMessages.CLIENT_REQUEST_JOIN, Config.playerName);
			
			Timer connectionTimer = new Timer(750, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isConnected) {
						Screen.SCREEN_JOIN.setErrorMessage("Unable to connect to: " + Config.serverAddress);
						udpClient = null;
					}
				}
			});
			
			connectionTimer.setRepeats(false);
			connectionTimer.start();
		} catch(SocketException e) {
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroy() {
		if(udpClient == null || !udpClient.isOpen())
			return;
		
		sendUDP(ENetworkMessages.CLIENT_LEAVE, String.valueOf(client.getPlayer().getId()));
		
		udpClient.close();
		udpClient = null;
	}
	
	public static void sendUDP(ENetworkMessages type, Object... messages) {
		if(udpClient == null || !udpClient.isOpen())
			return;
			
		String message = type.id() + "#";
		
		for(int i = 0; i < messages.length; i++)
			message += (String.valueOf(messages[i]) + "#");
		
		try {
			udpClient.send(message);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class UDPClientAction implements ClientMessageHandler {
		@Override
		public void onMessageReceived(String message) {
			final String[] parts = message.split("#");
			final ENetworkMessages id = ENetworkMessages.toId(parts[0]);
			
			if(id == null)
				return;
			
			switch(id) {
			// Client accepted by server
			case SERVER_WELCOME_CLIENT:
				isConnected = true;
				client.onServerJoin(parts[1]);
				break;
			// Client rejected by server
			case SERVER_REJECT_CLIENT_EXISTS:
			case SERVER_REJECT_CLIENT_PLAYING:
			case SERVER_REJECT_CLIENT_FULL:
				Screen.SCREEN_ERROR.setErrorMessage(id);
				Client.setActiveScreen(Screen.SCREEN_ERROR);
				break;
			// Client joined the server
			case SERVER_CLIENT_JOINED:
				client.getClientList().onClientJoin(parts);
				break;
			// Client left the server
			case SERVER_CLIENT_LEFT:
				client.getClientList().onClientLeave((int)Float.parseFloat(parts[1]));
				break;
			// Start or stop counting down
			case SERVER_COUNTDOWN_CHANGE:
				client.setCountdownState((int)Float.parseFloat(parts[1]));
				break;
			// Another client's info
			case SERVER_CLIENT_INFO:
				client.getClientList().onClientInfoReceived(parts);
				break;
			// Update the ready state of a client
			case CLIENT_UPDATE_READY:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).setIsReady((int)Float.parseFloat(parts[2]) == 0 ? false : true);
				break;
			// Update the color of a client
			case CLIENT_UPDATE_COLOR:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).setColor(new Vector3f(Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4])));
				break;
			case CLIENT_SPAWNED:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).spawn((int)Float.parseFloat(parts[2]), (int)Float.parseFloat(parts[3]), (int)Float.parseFloat(parts[4]));
				break;
			case SERVER_START_GAME:
				SnakeMP.getInstance().getClient().startGame();
				break;
			case CIENT_UPDATE:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).update((int)Float.parseFloat(parts[2]), (int)Float.parseFloat(parts[3]), (int)Float.parseFloat(parts[4]));
				break;
			case CLIENT_UPDATE_DIRECTION:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).getPart(0).setDirection((int)Float.parseFloat(parts[2]));
				break;
			case CLIENT_DIED:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).setIsAlive(false);
				break;
			case CLIENT_GROWN:
				client.getClientList().getClientWithId((int)Float.parseFloat(parts[1])).addLastPart();
				break;
			case SERVER_END:
				client.endGame((int)Float.parseFloat(parts[1]));
				break;
			case CLIENT_STOLE_PARTS:
				ClientData target = client.getClientList().getClientWithId((int)Float.parseFloat(parts[1]));
				int from = (int)Float.parseFloat(parts[2]);
				target.steal(from);
				break;
			default:
				System.err.println("[UDP] Client received an invalid message ID: " + id);
				break;
			}
		}
	}
}
