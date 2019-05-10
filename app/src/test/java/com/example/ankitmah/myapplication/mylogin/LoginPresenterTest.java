package com.example.ankitmah.myapplication.mylogin;

import com.example.ankitmah.myapplication.R;
import com.example.ankitmah.myapplication.myLogin.LoginPresenter;
import com.example.ankitmah.myapplication.myLogin.LoginService;
import com.example.ankitmah.myapplication.myLogin.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by cybage on 08-May-19.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    private LoginPresenter mLoginPresenter;
    @Mock
    private LoginService mLoginService;
    @Mock
    private LoginView mLoginView;

    @Before
    public void setUp() throws Exception {
        mLoginPresenter = new LoginPresenter(mLoginView, mLoginService);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(mLoginView.getUserName()).thenReturn("test");
        mLoginPresenter.onLoginClicked();
        verify(mLoginView).showUsernameError(R.string.username_error);

    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(mLoginView.getUserName()).thenReturn("qwerty");
        when(mLoginView.getPassword()).thenReturn("");
        mLoginPresenter.onLoginClicked();
        verify(mLoginView).showPasswordError(R.string.password_error);
    }

    @Test
    public void shouldStartEmptyActivityWhenUsernameAndPasswordAreCorrect() {
        when(mLoginView.getUserName()).thenReturn("qwerty");
        when(mLoginView.getPassword()).thenReturn("12345");

        when(mLoginService.login("qwerty","12345")).thenReturn(true);

        mLoginPresenter.onLoginClicked();

        verify(mLoginView).startEmptyActivity();
    }

    @Test
    public void shouldShowErrorWhenUsernameAndPasswordAreInvalid() {
        when(mLoginView.getUserName()).thenReturn("qwerty");
        when(mLoginView.getPassword()).thenReturn("12345");

        when(mLoginService.login("qwerty","12345")).thenReturn(false);

        mLoginPresenter.onLoginClicked();

        verify(mLoginView).showInvalidUsernameAndPasswordError(R.string.invalid_username_and_password);
    }
}