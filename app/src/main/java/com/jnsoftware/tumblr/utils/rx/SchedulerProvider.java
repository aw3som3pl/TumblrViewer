package com.jnsoftware.tumblr.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created on : Feb 07, 2020
 * Author     : JNsoftware
 */


public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
