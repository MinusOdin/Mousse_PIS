package com.example.mousse.ui.home;

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
public class HomeViewModelTest {

    @Mock
    HomeViewModel homeViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();



    @Before
    public void setUp(){
        //MockitoAnnotations.initMocks(homeViewModel.class);
    }

    @Test
    public void init() {
        doNothing().when(homeViewModel).init();
        homeViewModel.init();
        verify(homeViewModel, times(1)).init();
    }

    @Test
    public void getReceta() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(homeViewModel.getRecetas()).thenReturn(liveData);
        assertEquals(liveData, homeViewModel.getRecetas());
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(homeViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", homeViewModel.getToast().getValue());
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();

        doNothing().when(homeViewModel).setCollectionPublicadas(retrieved_recetas);
        homeViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(homeViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }

    @Test
    public void setToast() {
        doNothing().when(homeViewModel).setToast("test");
        homeViewModel.setToast("test");
        verify(homeViewModel, times(1)).setToast("test");
    }

}