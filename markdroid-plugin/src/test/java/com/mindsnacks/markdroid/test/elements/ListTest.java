package com.mindsnacks.markdroid.test.elements;

import com.mindsnacks.markdroid.AndroidXMLConstants;
import com.mindsnacks.markdroid.AndroidXMLNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tonyc on 1/20/14.
 */
public class ListTest extends ElementTest {
  @Test
  public void testUnorderedLists() {
    AndroidXMLNode rootNode = convertMarkdownToAndroidNodes("* This\n"
        + "* is an\n"
        + "* unordered **list**.");

    AndroidXMLNode firstItem = rootNode.getChildren().get(0);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, firstItem.getName());
    assertEquals("\u2022 This", firstItem.getAttributes().get(AndroidXMLConstants.TEXT));

    AndroidXMLNode secondItem = rootNode.getChildren().get(1);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, secondItem.getName());
    assertEquals("\u2022 is an", secondItem.getAttributes().get(AndroidXMLConstants.TEXT));

    AndroidXMLNode thirdItem = rootNode.getChildren().get(2);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, thirdItem.getName());
    assertEquals("\u2022 unordered &lt;b&gt;list&lt;/b&gt;.", thirdItem.getAttributes().get(AndroidXMLConstants.TEXT));
  }

  @Test
  public void testOrderedLists() {
    AndroidXMLNode rootNode = convertMarkdownToAndroidNodes("1. This\n"
        + "2. is an\n"
        + "3. ordered *list*.");

    AndroidXMLNode firstItem = rootNode.getChildren().get(0);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, firstItem.getName());
    assertEquals("1. This", firstItem.getAttributes().get(AndroidXMLConstants.TEXT));

    AndroidXMLNode secondItem = rootNode.getChildren().get(1);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, secondItem.getName());
    assertEquals("2. is an", secondItem.getAttributes().get(AndroidXMLConstants.TEXT));

    AndroidXMLNode thirdItem = rootNode.getChildren().get(2);
    assertEquals(AndroidXMLConstants.TEXT_VIEW, thirdItem.getName());
    assertEquals("3. ordered &lt;i&gt;list&lt;/i&gt;.", thirdItem.getAttributes().get(AndroidXMLConstants.TEXT));
  }
}
