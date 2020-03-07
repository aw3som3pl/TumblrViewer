package com.jnsoftware.tumblr.root;

import android.app.Application;

import com.jnsoftware.tumblr.di.component.ApplicationComponent;
import com.jnsoftware.tumblr.di.component.DaggerApplicationComponent;
import com.jnsoftware.tumblr.di.module.ApplicationModule;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
public class TumblrViewerApp extends Application {


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
