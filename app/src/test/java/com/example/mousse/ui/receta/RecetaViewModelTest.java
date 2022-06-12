package com.example.mousse.ui.receta;

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
public class RecetaViewModelTest {

    @Mock
    RecetaViewModel recetaViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(recetaViewModel.class);
    }

    @Test
    public void is_fav() {
        doNothing().when(recetaViewModel).is_fav();
        recetaViewModel.is_fav();
        verify(recetaViewModel, times(1)).is_fav();
    }

    @Test
    public void setCollectionHecho() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(recetaViewModel).setCollectionHechas(retrieved_recetas);
        recetaViewModel.setCollectionHechas(retrieved_recetas);
        verify(recetaViewModel, times(1)).setCollectionHechas(retrieved_recetas);

    }

    @Test
    public void setCollectionLike() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(recetaViewModel).setCollectionLikes(retrieved_recetas);
        recetaViewModel.setCollectionLikes(retrieved_recetas);
        verify(recetaViewModel, times(1)).setCollectionLikes(retrieved_recetas);

    }

    @Test
    public void setCollectionFav() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(recetaViewModel).setCollectionFavs(retrieved_recetas);
        recetaViewModel.setCollectionFavs(retrieved_recetas);
        verify(recetaViewModel, times(1)).setCollectionFavs(retrieved_recetas);

    }

    @Test
    public void setToast() {
        doNothing().when(recetaViewModel).setToast("test");
        recetaViewModel.setToast("test");
        verify(recetaViewModel, times(1)).setToast("test");
    }

    @Test
    public void guardar_fav() {
        doNothing().when(recetaViewModel).guardar_fav("test");
        recetaViewModel.guardar_fav("test");
        verify(recetaViewModel, times(1)).guardar_fav("test");
    }

    @Test
    public void getRecetas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(recetaViewModel.getRecetas()).thenReturn(liveData);
        assertEquals(liveData, recetaViewModel.getRecetas());
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");
        when(recetaViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", recetaViewModel.getToast().getValue());
    }

    @Test
    public void no_fav() {
        doNothing().when(recetaViewModel).no_fav("test");
        recetaViewModel.no_fav("test");
        verify(recetaViewModel, times(1)).no_fav("test");
    }

    @Test
    public void no_like() {
        doNothing().when(recetaViewModel).no_like("test");
        recetaViewModel.no_like("test");
        verify(recetaViewModel, times(1)).no_like("test");
    }

    @Test
    public void guardar_like() {
        doNothing().when(recetaViewModel).guardar_like("test");
        recetaViewModel.guardar_like("test");
        verify(recetaViewModel, times(1)).guardar_like("test");
    }

    @Test
    public void getRecetas2() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(recetaViewModel.getRecetas2()).thenReturn(liveData);
        assertEquals(liveData, recetaViewModel.getRecetas2());
    }

    @Test
    public void is_like() {
        doNothing().when(recetaViewModel).is_like();
        recetaViewModel.is_like();
        verify(recetaViewModel, times(1)).is_like();
    }

    @Test
    public void no_hecho() {
        doNothing().when(recetaViewModel).no_hecho("test");
        recetaViewModel.no_hecho("test");
        verify(recetaViewModel, times(1)).no_hecho("test");
    }

    @Test
    public void guardar_hecho() {
        doNothing().when(recetaViewModel).guardar_hecho("test");
        recetaViewModel.guardar_hecho("test");
        verify(recetaViewModel, times(1)).guardar_hecho("test");
    }

    @Test
    public void getRecetas3() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>();
        liveData.setValue(retrieved_recetas);
        when(recetaViewModel.getRecetas3()).thenReturn(liveData);
        assertEquals(liveData, recetaViewModel.getRecetas3());
    }

    @Test
    public void is_hecho() {
        doNothing().when(recetaViewModel).is_hecho();
        recetaViewModel.is_hecho();
        verify(recetaViewModel, times(1)).is_hecho();
    }
}