package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket("localhost", 9999)) {
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			printWriter.println("Hallo");
			printWriter.flush();
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String message = reader.readLine();
			System.out.println(message);
		}
	}
}
