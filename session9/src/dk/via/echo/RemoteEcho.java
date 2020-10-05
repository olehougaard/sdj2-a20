package dk.via.echo;

import java.rmi.RemoteException;

public class RemoteEcho implements IEcho {

	@Override
	public String echo(String s) {
		return s;
	}

	@Override
	public String reverse(String s) throws RemoteException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(s);
		buffer.reverse();
		return buffer.toString();
	}

}
