package dk.via.echo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import utility.observer.subject.RemoteSubject;

public interface IEcho extends Remote, RemoteSubject<String, String> {
	public String echo(String s) throws RemoteException;
	public String reverse(String s) throws RemoteException;
}
