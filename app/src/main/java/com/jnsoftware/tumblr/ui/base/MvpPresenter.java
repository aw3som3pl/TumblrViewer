package com.jnsoftware.tumblr.ui.base;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(Throwable error);

}

