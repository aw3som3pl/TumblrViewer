package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.ui.base.MvpView;

import java.util.List;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public interface MainMvpView extends MvpView {

    void updateFeed(List<TumblrPost> feedItemList);

    void initializeFeed(List<TumblrPost> feedItemList);

    void showDuplicateSearchToast();
}
