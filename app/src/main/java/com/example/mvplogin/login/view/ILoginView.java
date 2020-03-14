package com.example.mvplogin.login.view;

public interface ILoginView {
    void onClearText();

    void onLoginResult(Boolean result, int code, String token);

    void onSetProgressBarVisibility(int visibility);
}
