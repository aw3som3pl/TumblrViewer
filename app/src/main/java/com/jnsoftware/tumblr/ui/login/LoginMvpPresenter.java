package com.jnsoftware.tumblr.ui.login;

import com.jnsoftware.tumblr.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
    void onLoginClick();
}
