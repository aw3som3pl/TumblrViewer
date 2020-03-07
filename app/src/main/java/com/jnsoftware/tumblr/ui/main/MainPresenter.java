package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.data.DataManager;
import com.jnsoftware.tumblr.ui.base.BasePresenter;
import com.jnsoftware.tumblr.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Feb 11, 2019
 * Author     : AndroidWave
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
                        .getFeedList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();
                                /**
                                 * Update view here
                                 */
                                getMvpView().updateFeed(response.getData());
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
