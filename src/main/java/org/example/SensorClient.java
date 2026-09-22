package org.example;

import java.io.IOException;
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
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

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
            e.printStackTrace();
        }
    }
}
