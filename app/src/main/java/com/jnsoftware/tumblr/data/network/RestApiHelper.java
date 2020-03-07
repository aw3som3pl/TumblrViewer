package com.jnsoftware.tumblr.data.network;

import com.jnsoftware.tumblr.data.network.pojo.FeedItem;
import com.jnsoftware.tumblr.data.network.pojo.LoginRequest;
import com.jnsoftware.tumblr.data.network.pojo.UserProfile;
import com.jnsoftware.tumblr.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request);

    Single<WrapperResponse<List<FeedItem>>> getFeedList();
}
