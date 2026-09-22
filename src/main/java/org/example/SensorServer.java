package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SensorServer {
    private static final int PORT = 8080;
    private static final int MAX_CONNECTIONS = 5;
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CONNECTIONS);
        Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server kører på port " + PORT + "...");
            while (true) {
                Socket client = serverSocket.accept();

                if (!semaphore.tryAcquire()) {
                    System.out.println("Maksimalt " + MAX_CONNECTIONS + " aktive klientforbindelser. Afviser ny klient.");
                    try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                        out.println("SERVER_FULL");
                    }
                    client.close();
                    continue;
                }

                pool.execute(() -> handleClient(client, semaphore));
            }
        }
    }

    private static void handleClient(Socket client, Semaphore semaphore) {
        try (Socket socket = client;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                String message = line.trim();
                if (message.isEmpty()) {
                    continue;
                }

                String[] parts = message.split(":", 2);
                if (parts.length != 2) {
                    System.out.println("[ERROR] Ugyldigt format: " + message);
                    continue;
                }

                String type = parts[0].trim().toUpperCase();
                String valueText = parts[1].trim();

                try {
                    double value = Double.parseDouble(valueText);
                    System.out.println("Modtaget fra " + socket.getRemoteSocketAddress()
                            + ": " + type + " = " + value);

                    if (isThresholdExceeded(type, value)) {
                        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
                        String alarmMessage = "[" + timestamp + "] " + type + ": " + value + " -> ALARM!";
                        System.out.println(alarmMessage);
                        out.println("ALARM: " + type + ": " + value);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Ugyldig værdi fra " + type + ": " + valueText);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Sensorforbindelse lukket: " + e.getMessage());
        } finally {
            semaphore.release();
        }
    }

    private static boolean isThresholdExceeded(String type, double value) {
        return switch (type) {
            case "TEMP" -> value < -15 || value > 35;
            case "O2" -> value < 19 || value > 23;
            case "CO2" -> value > 2000;
            default -> false;
        };
    }
}
