package com.jnsoftware.tumblr.dataInterface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}