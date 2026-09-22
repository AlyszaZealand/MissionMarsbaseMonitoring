package org.example;

import java.util.Random;

public class SensorClientOxygen extends SensorClient {
    public SensorClientOxygen(int port) {
        super("O2", port);
    }

    @Override
    protected double generateValue(Random random) {
        return 18 + random.nextDouble() * 5;
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new SensorClientOxygen(8080));
        thread.start();
    }
}
