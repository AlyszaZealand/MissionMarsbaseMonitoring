package org.example;

import java.util.Random;

public class SensorClientTemperature extends SensorClient {
    public SensorClientTemperature(int port) {
        super("TEMP", port);
    }

    @Override
    protected double generateValue(Random random) {
        return 20 + random.nextDouble() * 10;
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new SensorClientTemperature(8080));
        thread.start();
    }
}
