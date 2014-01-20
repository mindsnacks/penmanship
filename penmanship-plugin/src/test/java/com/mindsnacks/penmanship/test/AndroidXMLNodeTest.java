package com.mindsnacks.penmanship.test;

import com.mindsnacks.penmanship.AndroidXMLNode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    assertEquals("<rootNode testOne=\"\"This is a test attribute.\" testTwo=\"This is another test attribute.\" >\n"
        + "<childNode />\n"
        + "</rootNode>\n", rootNode.render());
  }
}
