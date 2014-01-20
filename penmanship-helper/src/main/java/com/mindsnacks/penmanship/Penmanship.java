package com.mindsnacks.penmanship;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyc on 1/16/14.
 */
public class Penmanship {
  public static ViewGroup getMarkdroidView(final String name, final Context context) {
    int id = getLayoutId(name, context);
    return (ViewGroup)LayoutInflater.from(context).inflate(id, null);
  }

  private static int getLayoutId(final String name, final Context context) {
    int id = context.getResources().getIdentifier(name, "layout", context.getPackageName());

    if (id == 0) {
      throw new RuntimeException(String.format("Attemped to load Markdroid layout: %s, but it does not exist.", name));
    }

    return id;
  }

  public static List<TextView> getTextViews(ViewGroup container) {
    List<TextView> textViews = new ArrayList<TextView>();

    for (int i = 0; i < container.getChildCount(); i++) {
      View v = container.getChildAt(i);

      if (v instanceof TextView) {
        textViews.add((TextView) container.getChildAt(i));
      } else if (v instanceof ViewGroup) {
        textViews.addAll(getTextViews((ViewGroup) v));
      }
    }

    return textViews;
  }

  public static void applyRichText(ViewGroup container) {
    for (TextView textView : getTextViews(container)) {
      textView.setMovementMethod(LinkMovementMethod.getInstance());
      textView.setText(Html.fromHtml(textView.getText().toString()));
    }
  }
}
