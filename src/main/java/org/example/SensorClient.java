package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Random;

public abstract class SensorClient implements Runnable {
    private final String type;
    private final int port;

    protected SensorClient(String type, int port) {
        this.type = type;
        this.port = port;
    }

    protected abstract double generateValue(Random random);

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Thread alarmListener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        if (message.startsWith("ALARM")) {
                            System.out.println("[ALARM] " + type + ": " + message);
                        } else {
                            System.out.println("[SERVER] " + message);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("[ERROR] Sensor " + type + " mistede alarmforbindelsen.");
                }
            });
            alarmListener.setDaemon(true);
            alarmListener.start();

            Random random = new Random();
            while (!Thread.currentThread().isInterrupted()) {
                double value = generateValue(random);
                String formattedValue = String.format(Locale.US, "%.1f", value);
                out.println(type + ":" + formattedValue);
                System.out.println("Sendte: " + type + ":" + formattedValue);
                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("[ERROR] Sensor " + type + " mistede forbindelsen.");
        }
    }
}
