package binclient;

import java.io.*;
import java.net.Socket;

import binserver.Request;
import binserver.Response;

public class Client {
	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket("localhost", 9999)) {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream printWriter = new ObjectOutputStream(outputStream);
			Request request = new Request("Hallo", true);
			printWriter.writeObject(request);
			printWriter.flush();
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream reader = new ObjectInputStream(inputStream);
			Response response = (Response) reader.readObject();
			System.out.println(response);
		}
	}
}
