package com.example.vitaplant.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String email;
    private String Uid;
    private List<Device> devices;


    // Constructor vacío requerido por Firebase
    public User() {
    }

    public User(String username, String email, String Uid) {
        this.username= username;
        this.email = email;
        this.Uid = Uid;
    }

    public List<Device> getDevices() {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        return devices;
    }

    // Getters y setters para los atributos
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid){
        this.Uid = Uid;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    // Método para agregar un dispositivo
    public void addDevice(Device device) {
        getDevices().add(device);
    }
}