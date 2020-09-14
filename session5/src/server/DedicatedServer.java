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
			String message = reader.readLine();
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);
			writer.println(message);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
