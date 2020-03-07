package com.jnsoftware.tumblr.data.network;

import com.jnsoftware.tumblr.data.network.pojo.FeedItem;
import com.jnsoftware.tumblr.data.network.pojo.LoginRequest;
import com.jnsoftware.tumblr.data.network.pojo.UserProfile;
import com.jnsoftware.tumblr.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface NetworkService {
    /**
     * @return Observable feed response
     */
    @GET("feed.json")
    Single<WrapperResponse<List<FeedItem>>> getFeedList();


    @POST("login")
    Single<WrapperResponse<UserProfile>> doLoginApiCall(@Body LoginRequest mRequest);
}
