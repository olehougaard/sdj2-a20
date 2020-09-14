package client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PiClient {
	public static void main(String[] args) throws Exception {
		try(Socket socket = new Socket("localhost", 8888)) {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream printWriter = new ObjectOutputStream(outputStream);
			printWriter.writeInt(28);
			printWriter.flush();
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream reader = new ObjectInputStream(inputStream);
			Object pi = reader.readObject();
			System.out.println(pi);
		}
	}
}
