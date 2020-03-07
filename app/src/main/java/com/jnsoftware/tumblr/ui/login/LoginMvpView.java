package com.jnsoftware.tumblr.ui.login;

import com.jnsoftware.tumblr.data.network.pojo.UserProfile;
import com.jnsoftware.tumblr.ui.base.MvpView;

public interface LoginMvpView extends MvpView {
    void onLoginSuccess(UserProfile mUser);

    String getEmail();

    String getPassword();

    void showInputError();

    void setPassword(String userId);

    void setEmail(String password);
}
