// Generated code from Butter Knife. Do not modify!
package com.example.khairy.ditchnews;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230867;

  private View view2131230858;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.news_Recyclerview = Utils.findRequiredViewAsType(source, R.id.main_recyclerView, "field 'news_Recyclerview'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.activity_main_progressBar, "field 'progressBar'", ProgressBar.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.activity_main, "field 'drawerLayout'", DrawerLayout.class);
    target.gridLayout = Utils.findRequiredViewAsType(source, R.id.gridnews, "field 'gridLayout'", GridLayout.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinator, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.noconnection, "field 'imageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.saved, "method 'onClickSaved'");
    view2131230867 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickSaved();
      }
    });
    view = Utils.findRequiredView(source, R.id.refresh, "method 'onClickRefresh'");
    view2131230858 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickRefresh();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.news_Recyclerview = null;
    target.progressBar = null;
    target.drawerLayout = null;
    target.gridLayout = null;
    target.coordinatorLayout = null;
    target.toolbar = null;
    target.imageView = null;

    view2131230867.setOnClickListener(null);
    view2131230867 = null;
    view2131230858.setOnClickListener(null);
    view2131230858 = null;
  }
}
