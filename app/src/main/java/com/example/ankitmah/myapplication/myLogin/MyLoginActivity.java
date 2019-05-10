package com.example.ankitmah.myapplication.myLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankitmah.myapplication.R;

public class MyLoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private EditText mEtUsername, mEtPassword;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        mLoginPresenter = new LoginPresenter(this, new LoginService());
        setupUI();
    }

    private void setupUI() {
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            onLoginClicked();
        }
    }

    private void onLoginClicked() {
        mLoginPresenter.onLoginClicked();

    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public void showUsernameError(int resId) {
        mEtUsername.setError(getString(resId));
    }

    @Override
    public void showPasswordError(int resId) {
        mEtPassword.setError(getString(resId));
    }

    @Override
    public void startEmptyActivity() {
        new ActivityUtil(this).startEmptyActivity();
    }

    @Override
    public void showInvalidUsernameAndPasswordError(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }
}
