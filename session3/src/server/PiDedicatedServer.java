package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;

import pi.PiService;

public class PiDedicatedServer implements Runnable {
	private Socket socket;
	private PiService piService;
	
	public PiDedicatedServer(Socket socket) {
		this.socket = socket;
		this.piService = new PiService();
	}
	
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			int decimals = objectInputStream.readInt();
			BigDecimal pi = piService.computePi(decimals);
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(pi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
