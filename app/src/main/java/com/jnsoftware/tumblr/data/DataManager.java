package com.jnsoftware.tumblr.data;

import com.jnsoftware.tumblr.data.network.rest.RestApiHelper;
import com.jnsoftware.tumblr.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper, RestApiHelper{

    void updateUserInfo(
            String userName,
            String profilePicPath);
}
