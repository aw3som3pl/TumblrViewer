package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.data.DataManager;
import com.jnsoftware.tumblr.data.network.rest.NetworkService;
import com.jnsoftware.tumblr.data.parsers.TumblrXmlParser;
import com.jnsoftware.tumblr.ui.base.BasePresenter;
import com.jnsoftware.tumblr.utils.CommonUtils;
import com.jnsoftware.tumblr.utils.NetworkUtils;
import com.jnsoftware.tumblr.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    private static int DEFAULT_POST_COUNT = 5;
    private static int DEFAULT_POST_START = 0;

    @Inject
    public TumblrXmlParser mTumblrXmlParser;

    @Inject
    public MainPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        if(getDataManager().getLastSearchedUserName() != null && getMvpView().isNetworkConnected()){
            fetchNewTumblrFeedBatch(getDataManager().getLastSearchedUserName());
        } else {
            fetchNewTumblrFeedBatch("demo");
        }
    }

    @Override
    public void loadFreshTumblrPostBatch(String userName, Map<String, String> params){
        if(getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager()
                    .getTumblrPostXmlStream(userName, params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().computation())
                    .map(responseBody -> mTumblrXmlParser.parse(responseBody.byteStream()))
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(outcome -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();

                        getDataManager().setLastSearchedUserName(userName);
                        getMvpView().initializeFeed(outcome);

                    }, error -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();


                        handleApiError(error);
                    }));
        }else{
            getMvpView().showMessage("No internet connection");
        }
    }

    @Override
    public void loadNextTumblrPostBatch(String userName, Map<String, String> params){
        if(getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager()
                    .getTumblrPostXmlStream(userName, params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().computation())
                    .map(responseBody -> mTumblrXmlParser.parse(responseBody.byteStream()))
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(outcome -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();
                        getMvpView().updateFeed(outcome);

                    }, error -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();


                        handleApiError(error);
                    }));
        } else {
            getMvpView().showMessage("No network connection");
        }

    }

    @Override
    public void fetchNextTumblrFeedBatch(int startOffset){
            Map<String, String> params = new HashMap<>();
            params.put("start", String.valueOf(startOffset));
            params.put("num", String.valueOf(DEFAULT_POST_COUNT));

            loadNextTumblrPostBatch(getDataManager().getLastSearchedUserName(), params);

    }

    @Override
    public void refreshTumblrFeed(){
            Map<String, String> params = new HashMap<>();
            params.put("start", String.valueOf(DEFAULT_POST_START));
            params.put("num", String.valueOf(DEFAULT_POST_COUNT));

            loadFreshTumblrPostBatch(getDataManager().getLastSearchedUserName(), params);
    }

    @Override
    public void fetchNewTumblrFeedBatch(String userName){
        Map<String, String> params = new HashMap<>();
        params.put("start", String.valueOf(DEFAULT_POST_START));
        params.put("num", String.valueOf(DEFAULT_POST_COUNT));

        loadFreshTumblrPostBatch(userName, params);
    }

    private boolean checkDuplicateSearch(String query){
        return query.equals(getDataManager().getLastSearchedUserName());
    }
}
