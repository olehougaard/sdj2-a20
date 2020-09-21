package server;

import java.io.*;
import java.net.Socket;

public class DedicatedServer implements Runnable {
	private Socket socket;
	
	public DedicatedServer(Socket socket) {
		this.socket = socket;
	}
	
	private String reverse(String original) {
		StringBuilder sb = new StringBuilder(original);
		sb.reverse();
		return sb.toString();
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
