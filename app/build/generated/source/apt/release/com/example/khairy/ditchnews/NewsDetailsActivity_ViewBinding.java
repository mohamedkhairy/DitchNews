// Generated code from Butter Knife. Do not modify!
package com.example.khairy.ditchnews;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsDetailsActivity_ViewBinding implements Unbinder {
  private NewsDetailsActivity target;

  @UiThread
  public NewsDetailsActivity_ViewBinding(NewsDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsDetailsActivity_ViewBinding(NewsDetailsActivity target, View source) {
    this.target = target;

    target.web_view = Utils.findRequiredViewAsType(source, R.id.news_details_webview, "field 'web_view'", WebView.class);
    target.progress_bar = Utils.findRequiredViewAsType(source, R.id.news_details_progressbar, "field 'progress_bar'", ProgressBar.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.details_toolbar, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.web_view = null;
    target.progress_bar = null;
    target.toolbar = null;
  }
}
