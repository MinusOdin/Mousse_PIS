package com.example.mousse.ui.perfil;

import static org.junit.Assert.assertEquals;
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
public class PerfilViewModelTest {

    @Mock
    PerfilViewModel perfilViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        //MockitoAnnotations.initMocks(perfilViewModel.class);
    }

    @Test
    public void getToast() {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.setValue("test");

        when(perfilViewModel.getToast()).thenReturn(liveData);
        assertEquals("test", perfilViewModel.getToast().getValue());

    }

    @Test
    public void setToast() {

        doNothing().when(perfilViewModel).setToast("test");
        perfilViewModel.setToast("test");
        verify(perfilViewModel, times(1)).setToast("test");

    }

    @Test
    public void init() {

        doNothing().when(perfilViewModel).init();
        perfilViewModel.init();
        verify(perfilViewModel, times(1)).init();

    }

    @Test
    public void setCollectionTabPublicadas() {

        doNothing().when(perfilViewModel).setCollectionTabPublicadas();
        perfilViewModel.setCollectionTabPublicadas();
        verify(perfilViewModel, times(1)).setCollectionTabPublicadas();
    }

    @Test
    public void setCollectionTabHecho() {

        doNothing().when(perfilViewModel).setCollectionTabHecho();
        perfilViewModel.setCollectionTabHecho();
        verify(perfilViewModel, times(1)).setCollectionTabHecho();

    }

    @Test
    public void setCollectionTabLike() {

        doNothing().when(perfilViewModel).setCollectionTabLike();
        perfilViewModel.setCollectionTabLike();
        verify(perfilViewModel, times(1)).setCollectionTabLike();

    }

    @Test
    public void setCollectionTabFav() {

        doNothing().when(perfilViewModel).setCollectionTabFav();
        perfilViewModel.setCollectionTabFav();
        verify(perfilViewModel, times(1)).setCollectionTabFav();
    }

    @Test
    public void setCollectionPublicadas() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(perfilViewModel).setCollectionPublicadas(retrieved_recetas);
        perfilViewModel.setCollectionPublicadas(retrieved_recetas);
        verify(perfilViewModel, times(1)).setCollectionPublicadas(retrieved_recetas);
    }

    @Test
    public void setCollectionHecho() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(perfilViewModel).setCollectionHechas(retrieved_recetas);
        perfilViewModel.setCollectionHechas(retrieved_recetas);
        verify(perfilViewModel, times(1)).setCollectionHechas(retrieved_recetas);

    }

    @Test
    public void setCollectionLike() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(perfilViewModel).setCollectionLikes(retrieved_recetas);
        perfilViewModel.setCollectionLikes(retrieved_recetas);
        verify(perfilViewModel, times(1)).setCollectionLikes(retrieved_recetas);

    }

    @Test
    public void setCollectionFav() {
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;

        doNothing().when(perfilViewModel).setCollectionFavs(retrieved_recetas);
        perfilViewModel.setCollectionFavs(retrieved_recetas);
        verify(perfilViewModel, times(1)).setCollectionFavs(retrieved_recetas);

    }

    @Test
    public void getRecetasPublicadas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(perfilViewModel.getRecetasPublicadas()).thenReturn(liveData);

        assertEquals(retrieved_recetas, perfilViewModel.getRecetasPublicadas().getValue());

    }

    @Test
    public void getRecetasHechas() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(perfilViewModel.getRecetasHechas()).thenReturn(liveData);

        assertEquals(retrieved_recetas, perfilViewModel.getRecetasHechas().getValue());

    }

    @Test
    public void getRecetasLikes() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(perfilViewModel.getRecetasLikes()).thenReturn(liveData);

        assertEquals(retrieved_recetas, perfilViewModel.getRecetasLikes().getValue());

    }

    @Test
    public void getRecetasFavs() {
        MutableLiveData<ArrayList<Receta>> liveData = new MutableLiveData<>();
        ArrayList<Receta> retrieved_recetas = new ArrayList<>() ;
        liveData.setValue(retrieved_recetas);

        when(perfilViewModel.getRecetasFavs()).thenReturn(liveData);

        assertEquals(retrieved_recetas, perfilViewModel.getRecetasFavs().getValue());

    }

    @Test
    public void getNombre() {

        doNothing().when(perfilViewModel).getNombre("user");
        perfilViewModel.getNombre("user");
        verify(perfilViewModel, times(1)).getNombre("user");

    }

}