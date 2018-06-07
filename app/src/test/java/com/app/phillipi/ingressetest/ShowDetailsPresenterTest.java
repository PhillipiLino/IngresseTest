package com.app.phillipi.ingressetest;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.phillipi.ingressetest.Main.GetShowsService;
import com.app.phillipi.ingressetest.Main.MainPresenterImpl;
import com.app.phillipi.ingressetest.Main.MainView;
import com.app.phillipi.ingressetest.ShowDetails.ShowDetailsPresenterImpl;
import com.app.phillipi.ingressetest.ShowDetails.ShowDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class ShowDetailsPresenterTest {

    SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
    Context context = Mockito.mock(Context.class);

    @Mock
    ShowDetailsView view;

    private ShowDetailsPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new ShowDetailsPresenterImpl();
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
    }

    @Test
    public void checkIfReturnBooleanWhenPassNormalString(){
        Mockito.when(presenter.getDataInPreferences(context, "teste")).thenReturn(anyBoolean());
    }

    @Test
    public void checkIfReturnBooleanWhenPassNull(){
        Mockito.when(presenter.getDataInPreferences(context, null)).thenReturn(anyBoolean());
    }

    @Test
    public void checkIfTryToSaveNull(){
        presenter.saveFavoriteShowInPreferences(context, null, false);
    }

}
