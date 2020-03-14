package com.example.mvplogin.login.presenter;

public interface ILoginPresenter {
    void clear();

    void doLogin(String name, String password);

    void setProgressBarVisibility(int visibility);
}
