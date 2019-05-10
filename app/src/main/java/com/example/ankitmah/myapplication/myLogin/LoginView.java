package com.example.ankitmah.myapplication.myLogin;

/**
 * Created by cybage on 08-May-19.
 */
public interface LoginView {
    String getUserName();
    String getPassword();
    void showUsernameError(int resId);
    void showPasswordError(int resId);

    void startEmptyActivity();

    void showInvalidUsernameAndPasswordError(int resId);
}
