// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.data.parsers;

import dagger.internal.Factory;

public final class TumblrXmlParser_Factory implements Factory<TumblrXmlParser> {
  private static final TumblrXmlParser_Factory INSTANCE = new TumblrXmlParser_Factory();

  @Override
  public TumblrXmlParser get() {
    return new TumblrXmlParser();
  }

  public static TumblrXmlParser_Factory create() {
    return INSTANCE;
  }
}
