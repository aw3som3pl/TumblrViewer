// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.dataInterface.module;

import com.jnsoftware.tumblr.data.parsers.TumblrXmlParser;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ApplicationModule_ProvideXmlPullParserFactory
    implements Factory<TumblrXmlParser> {
  private final ApplicationModule module;

  public ApplicationModule_ProvideXmlPullParserFactory(ApplicationModule module) {
    this.module = module;
  }

  @Override
  public TumblrXmlParser get() {
    return Preconditions.checkNotNull(
        module.provideXmlPullParser(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static ApplicationModule_ProvideXmlPullParserFactory create(ApplicationModule module) {
    return new ApplicationModule_ProvideXmlPullParserFactory(module);
  }

  public static TumblrXmlParser proxyProvideXmlPullParser(ApplicationModule instance) {
    return Preconditions.checkNotNull(
        instance.provideXmlPullParser(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
