package com.jnsoftware.tumblr.di.component;


import com.jnsoftware.tumblr.di.PerActivity;
import com.jnsoftware.tumblr.di.module.ActivityModule;
import com.jnsoftware.tumblr.ui.login.LoginActivity;
import com.jnsoftware.tumblr.ui.main.MainActivity;

import dagger.Component;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);
}