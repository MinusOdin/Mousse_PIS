package com.example.mousse.ui.otro_perfil;

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
public class OtroPerfilViewModelTest {

    @Mock
    OtroPerfilViewModel otroPerfilViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(otroPerfilViewModel.class);
    }

    @Test
    public void init() {
        doNothing().when(otroPerfilViewModel).init("test");
        otroPerfilViewModel.init("test");
        verify(otroPerfilViewModel, times(1)).init("test");
    }

    @Test
    public void getRecetasPublicadas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(otroPerfilViewModel.getRecetasPublicadas()).thenReturn(liveData);

        assertEquals(retrieved_recetas, otroPerfilViewModel.getRecetasPublicadas().getValue());
    }

    @Test
    public void getRecetasHechas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(otroPerfilViewModel.getRecetasHechas()).thenReturn(liveData);

        assertEquals(retrieved_recetas, otroPerfilViewModel.getRecetasHechas().getValue());
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(otroPerfilViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", otroPerfilViewModel.getToast().getValue());
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(otroPerfilViewModel).setCollectionPublicadas(retrieved_recetas);
        otroPerfilViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(otroPerfilViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }

    @Test
    public void setCollectionHechas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(otroPerfilViewModel).setCollectionHechas(retrieved_recetas);
        otroPerfilViewModel.setCollectionHechas(retrieved_recetas);
        verify(otroPerfilViewModel, times(1)).setCollectionHechas(retrieved_recetas);
    }

   @Test
    public void setToast() {
       doNothing().when(otroPerfilViewModel).setToast("test");
       otroPerfilViewModel.setToast("test");
       verify(otroPerfilViewModel, times(1)).setToast("test");
    }

    @Test
    public void getNombre() {
        doNothing().when(otroPerfilViewModel).getNombre("user");
        otroPerfilViewModel.getNombre("user");
        verify(otroPerfilViewModel, times(1)).getNombre("user");
    }
}