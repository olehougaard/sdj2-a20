package dk.via.echo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements Runnable {
	private Scanner scanner = new Scanner(System.in); 
	
	public void run() {
		try {
			int port = scanner.nextInt();
			scanner.nextLine();
			IEcho echo = (IEcho) Naming.lookup("echo");
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
}
