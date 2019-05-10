package com.example.ankitmah.myapplication.myLogin;

/**
 * Created by cybage on 08-May-19.
 */
public class LoginService {

    public boolean login(String username, String password){
        return (username.equals("qwerty") && password.equals("12345"));
    }
}
