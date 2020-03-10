package com.jnsoftware.tumblr.data;

import android.content.Context;

import com.jnsoftware.tumblr.data.network.rest.RestApiHelper;
import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.data.prefs.PreferencesHelper;
import com.jnsoftware.tumblr.dataInterface.ApplicationContext;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.ResponseBody;

public class BaseDataManager implements DataManager {
    private static final String TAG = "BaseDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final RestApiHelper mApiHelper;

    @Inject
    public BaseDataManager(@ApplicationContext Context context,
                           PreferencesHelper preferencesHelper,
                           RestApiHelper apiHelper){
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void updateUserInfo(String userName, String profilePicPath) {
        mPreferencesHelper.setLastSearchedUserName(userName);
        mPreferencesHelper.setLastSearchedUserProfilePicUrl(profilePicPath);
    }

    @Override
    public Single<ResponseBody> getTumblrPostXmlStream(String user) {
        return mApiHelper.getTumblrPostXmlStream(user);
    }

    @Override
    public String getLastSearchedUserName() {
        return mPreferencesHelper.getLastSearchedUserName();
    }

    @Override
    public void setLastSearchedUserName(String userId) {
        mPreferencesHelper.setLastSearchedUserName(userId);
    }

    @Override
    public String getLastSearchedUserProfilePicUrl() {
        return mPreferencesHelper.getLastSearchedUserProfilePicUrl();
    }

    @Override
    public void setLastSearchedUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setLastSearchedUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public boolean isFirstTime() {
        return mPreferencesHelper.isFirstTime();
    }

    @Override
    public void setFirstTime(boolean firstTime) {
        mPreferencesHelper.setFirstTime(firstTime);
    }

}
