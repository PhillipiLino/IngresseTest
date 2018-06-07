package com.app.phillipi.ingressetest.Main;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;

import java.util.List;

public interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<CatalogItem> items);

    void changeActivity(Show show);

}
