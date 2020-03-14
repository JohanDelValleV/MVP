package com.example.mvplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvplogin.login.presenter.ILoginPresenter;
import com.example.mvplogin.login.presenter.LoginPresenter;
import com.example.mvplogin.login.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    private ILoginPresenter loginPresenter;
    private ProgressBar progressBar;

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        editUser = findViewById(R.id.et_login_username);
        editPass = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        progressBar = findViewById(R.id.progress_login);
        progressBar.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.setProgressBarVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
            }
        });

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClearText();
    }

    @Override
    public void onClearText() {
        editUser.setText("");
        editPass.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code, String token) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);
        btnLogin.setEnabled(true);
        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("Token", token);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
