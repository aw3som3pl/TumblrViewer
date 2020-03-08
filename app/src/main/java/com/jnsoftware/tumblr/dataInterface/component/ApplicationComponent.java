package com.jnsoftware.tumblr.dataInterface.component;

import android.app.Application;
import android.content.Context;

import com.jnsoftware.tumblr.data.DataManager;
import com.jnsoftware.tumblr.dataInterface.ApplicationContext;
import com.jnsoftware.tumblr.dataInterface.module.ApplicationModule;
import com.jnsoftware.tumblr.root.TumblrViewerApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(TumblrViewerApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
//    @Component.Builder
//    interface Builder {
//        ApplicationComponent build();
//
//        Builder applicationModule(ApplicationModule applicationModule);
//
//        DataManager getDataManager();
//    }


}
