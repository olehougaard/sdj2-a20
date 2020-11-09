package dk.via.echo;

import java.rmi.RemoteException;
import utility.observer.subject.PropertyChangeProxy;

import utility.observer.listener.GeneralListener;

public class RemoteEcho implements IEcho {
	private PropertyChangeProxy<String, String> property;
	
	public RemoteEcho() {
		property = new PropertyChangeProxy<>(this);
	}

	@Override
	public String echo(String s) {
		property.firePropertyChange("Echo", null, s);
		return s;
	}

	@Override
	public String reverse(String s) throws RemoteException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(s);
		buffer.reverse();
		String response = buffer.toString();
		property.firePropertyChange("Reverse", null, response);
		return response;
	}

	@Override
	public boolean addListener(GeneralListener<String, String> listener, String... propertyNames)
			throws RemoteException {
		return property.addListener(listener, propertyNames);
	}

	@Override
	public boolean removeListener(GeneralListener<String, String> listener, String... propertyNames)
			throws RemoteException {
		return property.removeListener(listener, propertyNames);
	}
}
