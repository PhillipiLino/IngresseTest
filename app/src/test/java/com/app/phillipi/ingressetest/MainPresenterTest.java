package com.app.phillipi.ingressetest;

import com.app.phillipi.ingressetest.Main.GetShowsService;
import com.app.phillipi.ingressetest.Main.MainPresenterImpl;
import com.app.phillipi.ingressetest.Main.MainView;
import com.app.phillipi.ingressetest.Objects.CatalogItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView view;
    @Mock
    GetShowsService getShowsService;

    private MainPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenterImpl(view, getShowsService);
    }

    @Test
    public void checkIfItemsArePassedToView() {
        List<CatalogItem> items = Arrays.asList(new CatalogItem(), new CatalogItem(), new CatalogItem());
        presenter.onFinished(items);
        verify(view, times(1)).setItems(items);
        verify(view, times(1)).hideProgress();
    }

    @Test
    public void checkIfListEmptyIfEmptySearch(){
        presenter.doTheSearch("");
        verify(view, never()).showProgress();
    }

    @Test
    public void checkIfListEmptyIfNullSearch(){
        presenter.doTheSearch(null);
        verify(view, never()).showProgress();
    }

    @Test
    public void checkIfListEmptyIfOnlySpacesOnSearch(){
        presenter.doTheSearch("    ");
        verify(view, never()).showProgress();
    }

    @Test
    public void checkIfListFilledIfNormalStringOnSearch(){
        presenter.doTheSearch("test");
        verify(view, times(1)).showProgress();
    }

    @Test
    public void checkIfTryToGoToNextActivityIfSelectItemViewIsNull(){
        presenter.onItemClicked(null);
    }

}
