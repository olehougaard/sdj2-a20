package client;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private boolean done;

    public Client(Socket socket) {
        this.socket = socket;
        this.done = false;
    }

    public void send(String message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println(message);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDone() {
        return done;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String message = "";
            while (!message.equals("quitting")) {
                message = reader.readLine();
                System.out.println("Received:" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        done = true;
    }
}
