// Generated by Dagger (https://google.github.io/dagger).
package com.jnsoftware.tumblr.ui.main;

import com.jnsoftware.tumblr.data.parsers.TumblrXmlParser;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MainPresenter_MembersInjector<V extends MainMvpView>
    implements MembersInjector<MainPresenter<V>> {
  private final Provider<TumblrXmlParser> mTumblrXmlParserProvider;

  public MainPresenter_MembersInjector(Provider<TumblrXmlParser> mTumblrXmlParserProvider) {
    this.mTumblrXmlParserProvider = mTumblrXmlParserProvider;
  }

  public static <V extends MainMvpView> MembersInjector<MainPresenter<V>> create(
      Provider<TumblrXmlParser> mTumblrXmlParserProvider) {
    return new MainPresenter_MembersInjector<V>(mTumblrXmlParserProvider);
  }

  @Override
  public void injectMembers(MainPresenter<V> instance) {
    injectMTumblrXmlParser(instance, mTumblrXmlParserProvider.get());
  }

  public static <V extends MainMvpView> void injectMTumblrXmlParser(
      MainPresenter<V> instance, TumblrXmlParser mTumblrXmlParser) {
    instance.mTumblrXmlParser = mTumblrXmlParser;
  }
}
