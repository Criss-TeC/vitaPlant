package com.example.vitaplant.model;

public class User {

    private String username;
    private String email;
    private String Uid;

    // Constructor vacío requerido por Firebase
    public User() {
    }

    public User(String username, String email, String Uid) {
        this.username= username;
        this.email = email;
        this.Uid = Uid;
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
}