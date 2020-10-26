package dk.via.echo;

public class RunClient {
	public static void main(String[] args) {
		Client client = new Client();
		Thread clientThread = new Thread(client);
		clientThread.start();
	}
}
