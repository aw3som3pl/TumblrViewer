package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.data.DataManager;
import com.jnsoftware.tumblr.data.parsers.TumblrXmlParser;
import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.ui.base.BasePresenter;
import com.jnsoftware.tumblr.utils.rx.SchedulerProvider;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {
    @Inject
    public MainPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                        .getTumblrPostXmlStream("diana-prince")
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().computation())
                        .subscribe(response -> {
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();
                            TumblrXmlParser parser = new TumblrXmlParser();
                            List<TumblrPost> list = parser.parse(response.byteStream());
                            int count = list.size();
                        }, error -> {
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();

                            /**
                             * manage all kind of error in single place
                             */
                            handleApiError(error);
                        }));
    }
}
