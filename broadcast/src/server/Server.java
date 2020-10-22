package server;

import java.io.*;
import java.net.Socket;

public class Server implements Runnable {
	private Socket socket;
	
	public Server(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		BroadcastSystem.getInstance().subscribe(socket);
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);
			boolean finished = false;
			while(!finished) {
				String message = reader.readLine();
				if (message.equals("quit")) {
					writer.println("quitting");
					writer.flush();
					finished = true;
				} else {
					BroadcastSystem.getInstance().broadcast(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			BroadcastSystem.getInstance().unsubscribe(socket);
		}
	}
}
