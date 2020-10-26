package dk.via.echo;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;

public class Server {
	public static void main(String[] args) throws Exception {
		LocateRegistry.createRegistry(1099);
		RemoteEcho echo = new RemoteEcho();
		IEcho remoteObject = (IEcho) UnicastRemoteObject.exportObject(echo, 2200);
		Naming.rebind("echo", remoteObject);
		System.out.println("Server started");
	}
}
