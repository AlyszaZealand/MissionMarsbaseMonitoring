package org.example;

/*
Server (med trådpool)
Brug ExecutorService med fx 5 tråde
For hver klient: læs linje for linje og parse typen + værdi
Tjek mod grænser og skriv til logfil
Hvis alarm: skriv alarm i konsol og til klient
 */

// Thread pool
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SensorServer {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server kører på port 8080...");
            while (true) {
                Socket client = serverSocket.accept(); // venter på klienter
                pool.execute(() -> handleClient(client));
            }
        }
    }

    private static void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Modtaget fra "
                        + client.getRemoteSocketAddress() + ": " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}