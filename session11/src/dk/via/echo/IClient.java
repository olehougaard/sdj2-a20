package dk.via.echo;

import java.rmi.Remote;

import utility.observer.listener.RemoteListener;

public interface IClient extends RemoteListener<String, String>, Remote {

}
