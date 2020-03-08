package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.ui.base.MvpPresenter;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
