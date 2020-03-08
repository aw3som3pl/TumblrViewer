package com.jnsoftware.tumblr.dataInterface.component;


import com.jnsoftware.tumblr.dataInterface.PerActivity;
import com.jnsoftware.tumblr.dataInterface.module.ActivityModule;
import com.jnsoftware.tumblr.ui.main.MainActivity;

import dagger.Component;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}