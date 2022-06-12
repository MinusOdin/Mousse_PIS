package com.example.mousse.ui.registrarse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.mousse.Receta;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class RegistrarseViewModelTest {
    @Mock
    RegistrarseViewModel registrarseViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        //MockitoAnnotations.initMocks(registrarseViewModel.class);
    }

    @Test
    public void registrarUsuario() {
        doNothing().when(registrarseViewModel).registrarUsuario("user", "test", null, "nombre");
        registrarseViewModel.registrarUsuario("user", "test", null, "nombre");
        verify(registrarseViewModel, times(1)).registrarUsuario("user", "test", null, "nombre");
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(registrarseViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", registrarseViewModel.getToast().getValue());
    }

    @Test
    public void getSuccesfull() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        liveData.setValue(true);

        when(registrarseViewModel.getSuccesfull()).thenReturn(liveData);
        assertEquals(liveData, registrarseViewModel.getSuccesfull());
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(registrarseViewModel).setCollectionPublicadas(retrieved_recetas);
        registrarseViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(registrarseViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }


    @Test
    public void setToast() {
        doNothing().when(registrarseViewModel).setToast("test");
        registrarseViewModel.setToast("test");
        verify(registrarseViewModel, times(1)).setToast("test");
    }

    @Test
    public void setSuccesfull() {
        doNothing().when(registrarseViewModel).setSuccesfull(true);
        registrarseViewModel.setSuccesfull(true);
        verify(registrarseViewModel, times(1)).setSuccesfull(true);
    }
}
