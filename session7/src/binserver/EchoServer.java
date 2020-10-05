package binserver;

import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			while (true) {
				Socket socket = serverSocket.accept();
				DedicatedServer ds = new DedicatedServer(socket);
				Thread thread = new Thread(ds);
				thread.start();
			}
		}
	}
}
