// Generated code from Butter Knife. Do not modify!
package com.example.khairy.ditchnews;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsAdapter$HomeNewsViewHolder_ViewBinding implements Unbinder {
  private NewsAdapter.HomeNewsViewHolder target;

  @UiThread
  public NewsAdapter$HomeNewsViewHolder_ViewBinding(NewsAdapter.HomeNewsViewHolder target,
      View source) {
    this.target = target;

    target.cardView = Utils.findRequiredViewAsType(source, R.id.card, "field 'cardView'", CardView.class);
    target.Card_imagev = Utils.findRequiredViewAsType(source, R.id.news_image, "field 'Card_imagev'", ImageView.class);
    target.Card_titlev = Utils.findRequiredViewAsType(source, R.id.news_title, "field 'Card_titlev'", TextView.class);
    target.Card_timev = Utils.findRequiredViewAsType(source, R.id.news_time, "field 'Card_timev'", TextView.class);
    target.Card_contentv = Utils.findRequiredViewAsType(source, R.id.news_content, "field 'Card_contentv'", TextView.class);
    target.SaveCheckbox = Utils.findRequiredViewAsType(source, R.id.save_CheckBox, "field 'SaveCheckbox'", CheckBox.class);
    target.widgetBtn = Utils.findRequiredViewAsType(source, R.id.wid_brn, "field 'widgetBtn'", TextView.class);
    target.imgShare = Utils.findRequiredViewAsType(source, R.id.share, "field 'imgShare'", ImageView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.day, "field 'linearLayout'", LinearLayout.class);
    target.dayText = Utils.findRequiredViewAsType(source, R.id.daytext, "field 'dayText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsAdapter.HomeNewsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.Card_imagev = null;
    target.Card_titlev = null;
    target.Card_timev = null;
    target.Card_contentv = null;
    target.SaveCheckbox = null;
    target.widgetBtn = null;
    target.imgShare = null;
    target.linearLayout = null;
    target.dayText = null;
  }
}
