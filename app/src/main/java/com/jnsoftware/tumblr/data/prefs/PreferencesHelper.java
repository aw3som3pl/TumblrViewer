package com.jnsoftware.tumblr.data.prefs;

public interface PreferencesHelper {

    String getLastSearchedUserName();

    void setLastSearchedUserName(String userName);

    boolean isFirstTime();

    void setFirstTime(boolean firstTime);

}
