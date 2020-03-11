package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.ui.base.MvpPresenter;

import java.util.Map;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onViewPrepared();

    void loadFreshTumblrPostBatch(String userName, Map<String, String> params);

    void loadNextTumblrPostBatch(String userName, Map<String, String> params);

    void fetchNextTumblrFeedBatch(int startOffset);

    void fetchNewTumblrFeedBatch(String userName);

    void refreshTumblrFeed();

}
