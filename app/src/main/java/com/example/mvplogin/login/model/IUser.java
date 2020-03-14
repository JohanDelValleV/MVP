package com.example.mvplogin.login.model;

public interface IUser {
    String getName();

    String getPassword();

    int checkUserValidity(String name, String password);
}
