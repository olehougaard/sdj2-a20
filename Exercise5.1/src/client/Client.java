package client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		try (Socket socket = new Socket("localhost", 5678)) {
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			printWriter.println("connect");
			printWriter.flush();
			String message = reader.readLine();
			if (!message.equals("username?")) {
				throw new RuntimeException("Bad protocol");
			}
			printWriter.println("me");
			printWriter.flush();
			message = reader.readLine();
			if (!message.equals("password?")) {
				throw new RuntimeException("Bad protocol");
			}
			printWriter.println("secret");
			printWriter.flush();
			message = reader.readLine();
			if (message.equals("approved")) {
				System.out.println("Login succeeded");
			} else {
				System.out.println("Login failed");
			}
		}
	}
}
