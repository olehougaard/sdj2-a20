package server;

import java.io.*;
import java.net.Socket;

public class DedicatedServer implements Runnable {
	private Socket socket;
	
	public DedicatedServer(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);

			String message = reader.readLine();
			if (message.equals("connect")) {
				writer.println("username?");
				writer.flush();
				String username = reader.readLine();
				writer.println("password?");
				writer.flush();
				String password = reader.readLine();
				System.out.println("Received: " + username + "/" + password);
				writer.println("approved");
				writer.flush();
			} else {
				writer.println("disconnected");
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
