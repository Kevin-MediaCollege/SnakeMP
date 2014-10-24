package com.snakybo.snakemp.client.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class TCPClient implements Runnable {
	private TCPClientMessageHandler handler;
	
	private Socket socket;
	
	private Thread thread;
	
	public TCPClient(InetAddress address, int port, TCPClientMessageHandler handler) {
		this.handler = handler;
		
		thread = new Thread(this);
		
		try {
			socket = new Socket(address, port);
			
			socket.setKeepAlive(true);
			
			thread.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(isOpen()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				handler.onMessageReceived(in.readLine());
				
				in.close();
			} catch(IOException e) {
				if(e.getClass().equals(SocketException.class))
					break;
				
				if(isOpen())
					e.printStackTrace();
			}
		}
	}
	
	public void close() {
		try {
			socket.close();
			thread.join();
			
			socket = null;
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String message) {
		DataOutputStream out;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			
			out.writeBytes(message + '\n');
			out.flush();
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOpen() {
		if(socket == null)
			return false;
		
		return !socket.isClosed();
	}
	
	public interface TCPClientMessageHandler {
		void onMessageReceived(String message);
	}
}
