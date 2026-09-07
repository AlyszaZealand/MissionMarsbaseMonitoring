package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class SensorClient implements Runnable {
    private final String type;
    private final int port;

    public SensorClient(String type, int port){
        this.type = type;
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 8080;

        Thread tempThread = new Thread(new SensorClient("TEMP", port));
        Thread o2Thread   = new Thread(new SensorClient("O2", port));
        Thread co2Thread  = new Thread(new SensorClient("CO2", port));

        tempThread.start();
        o2Thread.start();
        co2Thread.start();
    }


    @Override
    public void run () {
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Random random = new Random();
            while (true) {
                double value = switch (type) {
                    case "TEMP" -> 20 + random.nextDouble() * 10;
                    case "O2" -> 18 + random.nextDouble() * 5;
                    case "CO2" -> 400 + random.nextDouble() * 2000;
                    default -> random.nextDouble() * 100;
                };

                out.println(type + ":" + String.format("%.1f", value));
                System.out.println("Sendte: " + type + ":" + String.format("%.1f", value));

                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

