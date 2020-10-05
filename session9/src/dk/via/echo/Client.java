package dk.via.echo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) {
		try {
			IEcho echo = (IEcho) Naming.lookup("echo");
			String response = echo.echo("Hello");
			System.out.println(response);
			String reversed = echo.reverse("Hello");
			System.out.println(reversed);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
