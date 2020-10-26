package utility.observer.listener;

import utility.observer.event.ObserverEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteListener<S, T> extends Remote, GeneralListener<S, T>
{
   void propertyChange(ObserverEvent<S, T> event) throws RemoteException;
}
