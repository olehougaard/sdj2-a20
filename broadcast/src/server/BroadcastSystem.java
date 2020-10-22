package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class BroadcastSystem {
    private static BroadcastSystem instance;

    private ArrayList<Socket> clients;

    private BroadcastSystem() {
        clients = new ArrayList<>();
    }

    public static synchronized BroadcastSystem getInstance() {
        if (instance == null) {
            instance = new BroadcastSystem();
        }
        return instance;
    }

    public synchronized void subscribe(Socket socket) {
        clients.add(socket);
    }

    public synchronized void unsubscribe(Socket socket) {
        clients.remove(socket);
    }

    public synchronized void broadcast(String message) throws IOException {
        for(Socket socket: clients) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.println(message);
            writer.flush();
        }
    }
}
