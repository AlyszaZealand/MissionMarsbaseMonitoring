package org.example;

import java.util.Random;

public class SensorClientCO2 extends SensorClient {
    public SensorClientCO2(int port) {
        super("CO2", port);
    }

    @Override
    protected double generateValue(Random random) {
        return 400 + random.nextDouble() * 2000;
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new SensorClientCO2(8080));
        thread.start();
    }
}
