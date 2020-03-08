package com.jnsoftware.tumblr.data.prefs;

public interface PreferencesHelper {

    String getLastSearchedUserName();

    void setLastSearchedUserName(String userName);

    String getLastSearchedUserProfilePicUrl();

    void setLastSearchedUserProfilePicUrl(String profilePicUrl);

    boolean isFirstTime();

    void setFirstTime(boolean firstTime);

}
