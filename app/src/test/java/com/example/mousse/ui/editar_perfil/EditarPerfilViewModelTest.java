package com.example.mousse.ui.editar_perfil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EditarPerfilViewModelTest {

    @Mock
    EditarPerfilViewModel editarPerfilViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp(){        
        //MockitoAnnotations.initMocks(editarPerfilViewModel.class);
    }

    @Test
    public void guardarDatos() {
        Uri foto = null;
        doNothing().when(editarPerfilViewModel).guardarDatos("test", foto);
        editarPerfilViewModel.guardarDatos("test", foto);
        verify(editarPerfilViewModel, times(1)).guardarDatos("test", foto);
    }

    @Test
    public void guardarNombre() {
        doNothing().when(editarPerfilViewModel).guardarNombre("test");
        editarPerfilViewModel.guardarNombre("test");
        verify(editarPerfilViewModel, times(1)).guardarNombre("test");
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(editarPerfilViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", editarPerfilViewModel.getToast().getValue());
    }
    
    @Test
    public void setToast() {
        doNothing().when(editarPerfilViewModel).setToast("test");
        editarPerfilViewModel.setToast("test");
        verify(editarPerfilViewModel, times(1)).setToast("test");
    }
    
    @Test
    public void getNombre() {
        doNothing().when(editarPerfilViewModel).getNombre("test");
        editarPerfilViewModel.getNombre("test");
        verify(editarPerfilViewModel, times(1)).getNombre("test");
    }
}