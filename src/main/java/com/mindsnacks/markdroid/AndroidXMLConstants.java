package com.mindsnacks.markdroid;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony Cosentini Date: 1/13/14 Time: 11:26 AM
 */
public class AndroidXMLConstants {
  public static final String XMLNS_ANDROID = "xmlns:android";
  public static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";

  public static final String LAYOUT_WIDTH = "android:layout_width";
  public static final String LAYOUT_HEIGHT = "android:layout_height";

  public static final String MATCH_PARENT = "match_parent";
  public static final String WRAP_CONTENT = "wrap_content";

  public static final String ID = "android:id";

  public static final String TAG = "android:tag";

  public static final String STYLE = "style";
  public static final String STYLE_MARKDOWN_CONTENT = "@style/markdown_content";

  public static final String ORIENTATION = "android:orientation";
  public static final String VERTICAL = "vertical";

  public static final String LINEAR_LAYOUT = "LinearLayout";
  public static final String TEXT_VIEW = "TextView";
  public static final String IMAGE_VIEW = "ImageView";

  public static final String ADJUST_VIEW_BOUNDS = "android:adjustViewBounds";
  public static final String SOURCE = "android:src";

  public static final String TRUE = "true";

  public static final String TEXT = "android:text";

  public static Map<String, String> getDefaultLayoutSizes() {
    Map<String, String> attributes = new HashMap<String, String>();

    attributes.put(AndroidXMLConstants.LAYOUT_WIDTH, AndroidXMLConstants.MATCH_PARENT);
    attributes.put(AndroidXMLConstants.LAYOUT_HEIGHT, AndroidXMLConstants.WRAP_CONTENT);

    return attributes;
  }

  public static Map<String, String> getDefaultLinearLayoutAttributes() {
    Map<String, String> attributes = getDefaultLayoutSizes();

    attributes.put(AndroidXMLConstants.LAYOUT_WIDTH, AndroidXMLConstants.MATCH_PARENT);
    attributes.put(AndroidXMLConstants.LAYOUT_HEIGHT, AndroidXMLConstants.WRAP_CONTENT);
    attributes.put(AndroidXMLConstants.ORIENTATION, AndroidXMLConstants.VERTICAL);

    return attributes;
  }
}
