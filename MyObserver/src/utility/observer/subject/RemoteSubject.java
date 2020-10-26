package utility.observer.subject;

import utility.observer.listener.GeneralListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSubject<S, T> extends GeneralSubject<S, T>, Remote
{
   boolean addListener(GeneralListener<S, T> listener, String... propertyNames) throws RemoteException;
   boolean removeListener(GeneralListener<S, T> listener,
       String... propertyNames) throws RemoteException;
}
