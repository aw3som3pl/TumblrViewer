// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.data;

import android.content.Context;
import com.jnsoftware.tumblr.data.db.AppDatabase;
import com.jnsoftware.tumblr.data.network.RestApiHelper;
import com.jnsoftware.tumblr.data.prefs.PreferencesHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class BaseDataManager_Factory implements Factory<BaseDataManager> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> databaseProvider;

  private final Provider<PreferencesHelper> preferencesHelperProvider;

  private final Provider<RestApiHelper> apiHelperProvider;

  public BaseDataManager_Factory(
      Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider,
      Provider<PreferencesHelper> preferencesHelperProvider,
      Provider<RestApiHelper> apiHelperProvider) {
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
    this.preferencesHelperProvider = preferencesHelperProvider;
    this.apiHelperProvider = apiHelperProvider;
  }

  @Override
  public BaseDataManager get() {
    return new BaseDataManager(
        contextProvider.get(),
        databaseProvider.get(),
        preferencesHelperProvider.get(),
        apiHelperProvider.get());
  }

  public static BaseDataManager_Factory create(
      Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider,
      Provider<PreferencesHelper> preferencesHelperProvider,
      Provider<RestApiHelper> apiHelperProvider) {
    return new BaseDataManager_Factory(
        contextProvider, databaseProvider, preferencesHelperProvider, apiHelperProvider);
  }
}
