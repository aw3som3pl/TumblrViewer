package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.ui.base.MvpPresenter;

/**
 * Created on : Feb 11, 2019
 * Author     : AndroidWave
 */
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
