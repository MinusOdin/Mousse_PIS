package com.example.mousse.ui.crear_receta;

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
public class CrearRecetaViewModelTest {

    @Mock
    CrearRecetaViewModel crearRecetaViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(crearRecetaViewModel.class);
    }

    @Test
    public void getRecetas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(crearRecetaViewModel.getRecetas()).thenReturn(liveData);
        assertEquals(liveData, crearRecetaViewModel.getRecetas());
    }

    @Test
    public void addReceta() {
        ArrayList<String> strings = new ArrayList<>();
        doNothing().when(crearRecetaViewModel).addReceta(true, "test", "test", strings, strings, strings,null);
        crearRecetaViewModel.addReceta(true, "test", "test", strings, strings, strings,null);
        verify(crearRecetaViewModel, times(1)).addReceta(true, "test", "test", strings, strings, strings,null);
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(crearRecetaViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", crearRecetaViewModel.getToast().getValue());
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(crearRecetaViewModel).setCollectionPublicadas(retrieved_recetas);
        crearRecetaViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(crearRecetaViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }

    @Test
    public void setToast() {
        doNothing().when(crearRecetaViewModel).setToast("test");
        crearRecetaViewModel.setToast("test");
        verify(crearRecetaViewModel, times(1)).setToast("test");
    }

    @Test
    public void setSuccesfull() {
        doNothing().when(crearRecetaViewModel).setSuccesfull(true);
        crearRecetaViewModel.setSuccesfull(true);
        verify(crearRecetaViewModel, times(1)).setSuccesfull(true);
    }

    @Test
    public void getSuccesfull() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        liveData.setValue(true);

        when(crearRecetaViewModel.getSuccesfull()).thenReturn(liveData);
        assertEquals(liveData, crearRecetaViewModel.getSuccesfull());
    }
}