package com.markdroid.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.mindsnacks.markdroid.Markdroid;

/**
 * Created by tonyc on 1/16/14.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      ScrollView markdownHolder = (ScrollView)findViewById(R.id.markdroid_container);

      ViewGroup demoMarkdownLayout = Markdroid.getMarkdroidView("demo", this);
      Markdroid.applyRichText(demoMarkdownLayout);

      markdownHolder.addView(demoMarkdownLayout);
    }
}
