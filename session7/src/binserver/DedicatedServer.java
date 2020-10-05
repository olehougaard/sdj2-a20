package binserver;

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
			ObjectInputStream reader = new ObjectInputStream(inputStream);
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream writer = new ObjectOutputStream(outputStream);
			Request request = (Request) reader.readObject();
			String original = request.getOriginal();
			if (request.isReverse()) {
				String reversed = reverse(original);
				Response response = new Response(original, reversed);
				writer.writeObject(response);
				writer.flush();
			} else {
				Response response = new Response(original, original);
				writer.writeObject(response);
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
