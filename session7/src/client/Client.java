package client;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import server.Request;
import server.Response;

public class Client {
	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket("localhost", 9999)) {
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			Gson gson = new Gson();
			Request request = new Request("Hallo", true);
			String json = gson.toJson(request);
			printWriter.println(json);
			printWriter.flush();
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String message = reader.readLine();
			Response response = gson.fromJson(message, Response.class);
			System.out.println(response);
		}
	}
}
