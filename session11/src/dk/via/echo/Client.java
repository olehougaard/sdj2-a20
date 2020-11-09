package dk.via.echo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import utility.observer.event.ObserverEvent;

public class Client implements IClient, Runnable {
	private Scanner scanner = new Scanner(System.in); 
	
	public void run() {
		try {
			int port = scanner.nextInt();
			scanner.nextLine();
			IEcho echo = (IEcho) Naming.lookup("echo");
			IClient reference = (IClient) UnicastRemoteObject.exportObject(this, port);
			echo.addListener(reference);
			String input = "";
			while(!input.equals("quit")) {
				input = scanner.nextLine();
				String response = echo.reverse(input);
				System.out.println(response);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(ObserverEvent<String, String> event) throws RemoteException {
		System.out.println("Broadcast: " + event.getValue2());
	}
}
