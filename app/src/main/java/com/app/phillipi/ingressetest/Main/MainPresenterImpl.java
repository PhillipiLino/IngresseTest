package com.app.phillipi.ingressetest.Main;

import android.support.v7.widget.CardView;
import android.view.View;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.R;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, GetShowsService.OnFinishedListener {

    private MainView mainView;
    private GetShowsService getShowsService;

    public MainPresenterImpl(MainView mainView, GetShowsService findItemsInteractor) {
        this.mainView = mainView;
        this.getShowsService = findItemsInteractor;
    }

    @Override
    public void onFinished(List<CatalogItem> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    @Override
    public void doTheSearch(String search) {
        if (mainView != null) {
            mainView.showProgress();
        }

        getShowsService.findItems(this, search);
    }

    @Override
    public void onItemClicked(View v) {
        CardView cv = v.findViewById(R.id.item_card_view);
        CatalogItem cardItem = (CatalogItem) cv.getTag();
        mainView.changeActivity(cardItem.getShow());
    }

}
