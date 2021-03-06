package server;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;

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
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);
			Gson gson = new Gson();
			String message = reader.readLine();
			Request request = gson.fromJson(message, Request.class);
			String original = request.getOriginal();
			if (request.isReverse()) {
				String reversed = reverse(original);
				Response response = new Response(original, reversed);
				String json = gson.toJson(response);
				writer.println(json);
				writer.flush();
			} else {
				Response response = new Response(original, original);
				String json = gson.toJson(response);
				writer.println(json);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
