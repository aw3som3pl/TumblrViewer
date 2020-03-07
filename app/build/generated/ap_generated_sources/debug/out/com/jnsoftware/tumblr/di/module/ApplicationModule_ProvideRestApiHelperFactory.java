// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.di.module;

import com.jnsoftware.tumblr.data.network.RestApiHelper;
import com.jnsoftware.tumblr.data.network.RestApiManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ApplicationModule_ProvideRestApiHelperFactory implements Factory<RestApiHelper> {
  private final ApplicationModule module;

  private final Provider<RestApiManager> restApiManagerProvider;

  public ApplicationModule_ProvideRestApiHelperFactory(
      ApplicationModule module, Provider<RestApiManager> restApiManagerProvider) {
    this.module = module;
    this.restApiManagerProvider = restApiManagerProvider;
  }

  @Override
  public RestApiHelper get() {
    return Preconditions.checkNotNull(
        module.provideRestApiHelper(restApiManagerProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static ApplicationModule_ProvideRestApiHelperFactory create(
      ApplicationModule module, Provider<RestApiManager> restApiManagerProvider) {
    return new ApplicationModule_ProvideRestApiHelperFactory(module, restApiManagerProvider);
  }

  public static RestApiHelper proxyProvideRestApiHelper(
      ApplicationModule instance, RestApiManager restApiManager) {
    return Preconditions.checkNotNull(
        instance.provideRestApiHelper(restApiManager),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
