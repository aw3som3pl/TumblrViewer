// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.ui.base;

import com.jnsoftware.tumblr.data.DataManager;
import com.jnsoftware.tumblr.utils.rx.SchedulerProvider;
import dagger.internal.Factory;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Provider;

public final class BasePresenter_Factory<V extends MvpView> implements Factory<BasePresenter<V>> {
  private final Provider<DataManager> managerProvider;

  private final Provider<SchedulerProvider> schedulerProvider;

  private final Provider<CompositeDisposable> compositeDisposableProvider;

  public BasePresenter_Factory(
      Provider<DataManager> managerProvider,
      Provider<SchedulerProvider> schedulerProvider,
      Provider<CompositeDisposable> compositeDisposableProvider) {
    this.managerProvider = managerProvider;
    this.schedulerProvider = schedulerProvider;
    this.compositeDisposableProvider = compositeDisposableProvider;
  }

  @Override
  public BasePresenter<V> get() {
    return new BasePresenter<V>(
        managerProvider.get(), schedulerProvider.get(), compositeDisposableProvider.get());
  }

  public static <V extends MvpView> BasePresenter_Factory<V> create(
      Provider<DataManager> managerProvider,
      Provider<SchedulerProvider> schedulerProvider,
      Provider<CompositeDisposable> compositeDisposableProvider) {
    return new BasePresenter_Factory<V>(
        managerProvider, schedulerProvider, compositeDisposableProvider);
  }
}
