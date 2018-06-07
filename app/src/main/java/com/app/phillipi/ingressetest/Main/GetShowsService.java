package com.app.phillipi.ingressetest.Main;

import com.app.phillipi.ingressetest.Objects.CatalogItem;

import java.util.List;

public interface GetShowsService {

    interface OnFinishedListener {
        void onFinished(List<CatalogItem> items);
    }

    void findItems(OnFinishedListener listener, String search);

}
