package com.example.vitaplant;  // Asegúrate de que este sea el paquete correcto

public class Device {
    private String name;
    private int humidity;

    public Device(String name, int humidity) {
        this.name = name;
        this.humidity = humidity;
    }

    public String getName() {
        return name;
    }

    public int getHumidity() {
        return humidity;
    }
}
