package com.jnsoftware.tumblr.data;

import com.jnsoftware.tumblr.data.db.dao.UserDao;
import com.jnsoftware.tumblr.data.network.RestApiHelper;
import com.jnsoftware.tumblr.data.prefs.PreferencesHelper;
import com.jnsoftware.tumblr.data.utils.LoggedInMode;

public interface DataManager extends UserDao, PreferencesHelper, RestApiHelper {
    void updateApiHeader(Long userId, String accessToken);

    void setUserLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);
}
