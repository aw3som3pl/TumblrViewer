package com.jnsoftware.tumblr.data;

import android.content.Context;

import com.jnsoftware.tumblr.data.network.RestApiHelper;
import com.jnsoftware.tumblr.data.network.pojo.FeedItem;
import com.jnsoftware.tumblr.data.network.pojo.UserProfile;
import com.jnsoftware.tumblr.data.network.pojo.WrapperResponse;
import com.jnsoftware.tumblr.data.prefs.PreferencesHelper;
import com.jnsoftware.tumblr.dataInterface.ApplicationContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class BaseDataManager implements DataManager {
    private static final String TAG = "BaseDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final RestApiHelper mApiHelper;

    @Inject
    public BaseDataManager(@ApplicationContext Context context,
                           PreferencesHelper preferencesHelper,
                           RestApiHelper apiHelper) {
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
    public Single<WrapperResponse<List<FeedItem>>> getFeedList() {
        return mApiHelper.getFeedList();
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
