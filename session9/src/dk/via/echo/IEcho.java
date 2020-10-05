package dk.via.echo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEcho extends Remote {
	public String echo(String s) throws RemoteException;
	public String reverse(String s) throws RemoteException;
}
