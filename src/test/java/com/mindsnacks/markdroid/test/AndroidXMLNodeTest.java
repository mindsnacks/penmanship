package com.mindsnacks.markdroid.test;

import com.mindsnacks.markdroid.AndroidXMLNode;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tony Cosentini Date: 1/13/14 Time: 11:02 AM
 */
public class AndroidXMLNodeTest {
  @Test
  public void testRendering() {
    Map<String, String> rootAttributes = new HashMap<String, String>();
    rootAttributes.put("testOne", "\"This is a test attribute.");
    rootAttributes.put("testTwo", "This is another test attribute.");

    AndroidXMLNode rootNode = new AndroidXMLNode("rootNode", rootAttributes);

    AndroidXMLNode childNode = new AndroidXMLNode("childNode", new HashMap<String, String>());
    rootNode.addChild(childNode);

    assertEquals("<rootNode testOne=\"&quot;This is a test attribute.\" testTwo=\"This is another test attribute.\" >\n"
        + "<childNode />\n"
        + "</rootNode>\n", rootNode.render());
  }
}