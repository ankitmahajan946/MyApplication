package com.example.ankitmah.myapplication.myLogin;

import com.example.ankitmah.myapplication.R;

/**
 * Created by cybage on 08-May-19.
 */
public class LoginPresenter {
    private LoginView mLoginView;
    private LoginService mLoginService;

    public LoginPresenter(LoginView loginView, LoginService loginService) {
        this.mLoginView = loginView;
        this.mLoginService = loginService;
    }

    public void onLoginClicked() {

        String username = mLoginView.getUserName();
        if (username.isEmpty()) {
            mLoginView.showUsernameError(R.string.username_error);
            return;
        }

        String password = mLoginView.getPassword();
        if (password.isEmpty()) {
            mLoginView.showPasswordError(R.string.password_error);
            return;
        }

        boolean isLoggedIn = mLoginService.login(username, password);

        if (isLoggedIn) {
            mLoginView.startEmptyActivity();
        }else{
            mLoginView.showInvalidUsernameAndPasswordError(R.string.invalid_username_and_password);
        }
    }
}
