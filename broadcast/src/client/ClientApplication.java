package client;

import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		try (Socket socket = new Socket("localhost", 9999)) {
			Client client = new Client(socket);
			Thread clientThread = new Thread(client);
			clientThread.start();
			while(!client.isDone()) {
				String input = scanner.nextLine();
				if (input.equals("quit")) {
					client.send("quit");
				} else {
					client.send(input);
				}
			}
		}
		scanner.close();
	}
}
