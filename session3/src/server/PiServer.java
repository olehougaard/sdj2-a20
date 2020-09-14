package server;

import java.net.ServerSocket;
import java.net.Socket;

public class PiServer {
	public static void main(String[] args) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(8888)) {
			while (true) {
				Socket socket = serverSocket.accept();
				PiDedicatedServer ds = new PiDedicatedServer(socket);
				Thread thread = new Thread(ds);
				thread.start();
			}
		}
	}
}
