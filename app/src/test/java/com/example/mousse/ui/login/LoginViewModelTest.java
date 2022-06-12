package com.example.mousse.ui.login;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModelTest {

    @Mock
    LoginViewModel loginViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp(){
        //MockitoAnnotations.initMocks(loginViewModel.class);
    }

    @Test
    public void loginUsuario() {
        doNothing().when(loginViewModel).loginUsuario("user", "test");
        loginViewModel.loginUsuario("user", "test");
        verify(loginViewModel, times(1)).loginUsuario("user", "test");
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(loginViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", loginViewModel.getToast().getValue());
    }

    @Test
    public void setToast() {
        doNothing().when(loginViewModel).setToast("test");
        loginViewModel.setToast("test");
        verify(loginViewModel, times(1)).setToast("test");
    }

    @Test
    public void setSuccesfull() {
        doNothing().when(loginViewModel).setSuccesfull(true);
        loginViewModel.setSuccesfull(true);
        verify(loginViewModel, times(1)).setSuccesfull(true);
    }

    @Test
    public void getSuccesfull() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        liveData.setValue(true);

        when(loginViewModel.getSuccesfull()).thenReturn(liveData);
        assertEquals(liveData, loginViewModel.getSuccesfull());
    }
}