package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
	public static void main(String[] args) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			while (true) {
				Socket socket = serverSocket.accept();
				Server ds = new Server(socket);
				Thread thread = new Thread(ds);
				thread.start();
			}
		}
	}
}
