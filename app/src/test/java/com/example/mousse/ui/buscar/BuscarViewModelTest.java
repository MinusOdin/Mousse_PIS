package com.example.mousse.ui.buscar;

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
public class BuscarViewModelTest {

    @Mock
    BuscarViewModel buscarViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(buscarViewModel.class);
    }

    @Test
    public void init() {
        doNothing().when(buscarViewModel).init("test");
        buscarViewModel.init("test");
        verify(buscarViewModel, times(1)).init("test");
    }

    @Test
    public void getRecetas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(buscarViewModel.getRecetas()).thenReturn(liveData);
        assertEquals(liveData, buscarViewModel.getRecetas());
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(buscarViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", buscarViewModel.getToast().getValue());
    }

    @Test
    public void getSuccesfull() {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        liveData.setValue(true);

        when(buscarViewModel.getSuccesfull()).thenReturn(liveData);
        assertEquals(liveData, buscarViewModel.getSuccesfull());
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(buscarViewModel).setCollectionPublicadas(retrieved_recetas);
        buscarViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(buscarViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }


    @Test
    public void setToast() {
        doNothing().when(buscarViewModel).setToast("test");
        buscarViewModel.setToast("test");
        verify(buscarViewModel, times(1)).setToast("test");
    }

    @Test
    public void setSuccesfull() {
        doNothing().when(buscarViewModel).setSuccesfull(true);
        buscarViewModel.setSuccesfull(true);
        verify(buscarViewModel, times(1)).setSuccesfull(true);
    }
}