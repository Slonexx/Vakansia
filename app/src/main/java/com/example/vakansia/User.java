package com.example.vakansia;

public class User {
    public String id, login, email, oblast, pass;

    public User() {
    }

    public User(String id, String login, String email, String oblast, String pass) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.oblast = oblast;
        this.pass = pass;
    }
    public void setUid(String s){
        id = s;
    }

}
