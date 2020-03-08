package com.jnsoftware.tumblr.ui.base;

import androidx.annotation.StringRes;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

}

