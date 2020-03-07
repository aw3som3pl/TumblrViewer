package com.jnsoftware.tumblr.di.module;

import android.content.Context;

import com.jnsoftware.tumblr.di.ActivityContext;
import com.jnsoftware.tumblr.di.PerActivity;
import com.jnsoftware.tumblr.ui.login.LoginMvpPresenter;
import com.jnsoftware.tumblr.ui.login.LoginMvpView;
import com.jnsoftware.tumblr.ui.login.LoginPresenter;
import com.jnsoftware.tumblr.ui.main.MainMvpPresenter;
import com.jnsoftware.tumblr.ui.main.MainMvpView;
import com.jnsoftware.tumblr.ui.main.MainPresenter;
import com.jnsoftware.tumblr.ui.main.RssAdapter;
import com.jnsoftware.tumblr.utils.rx.AppSchedulerProvider;
import com.jnsoftware.tumblr.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }


    @Provides
    RssAdapter provideRssAdapter() {
        return new RssAdapter(new ArrayList<>());
    }
}