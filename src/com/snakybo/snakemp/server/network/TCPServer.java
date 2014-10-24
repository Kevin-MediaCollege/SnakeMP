package com.snakybo.snakemp.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer implements Runnable {
	private TCPServerMessageHandler handler;
	
	private ServerSocket socket;
	
	private Thread thread;
	
	public TCPServer(int port, TCPServerMessageHandler handler) {
		this.handler = handler;
		
		thread = new Thread(this);
		
		try {
			socket = new ServerSocket(port);
			
			thread.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(!socket.isClosed()) {
			try {
				Socket connection = socket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String a = in.readLine();
				
				handler.onMessageReceived(a, connection);
				
				in.close();
			} catch(IOException e) {
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
	
	public void send(String message, Socket socket) {
		if(!isOpen())
			return;
		
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println(message);
			out.close();
		} catch(IOException e) {
			if(!e.getClass().equals(SocketException.class))
				e.printStackTrace();
		}
	}
	
	public boolean isOpen() {
		return !socket.isClosed();
	}
	
	public interface TCPServerMessageHandler {
		void onMessageReceived(String message, Socket socket);
	}
}
