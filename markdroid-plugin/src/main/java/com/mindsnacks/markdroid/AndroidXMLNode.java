package com.mindsnacks.markdroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony Cosentini Date: 1/13/14 Time: 10:51 AM
 */
public class AndroidXMLNode {
  private String name;
  private Map<String, String> attributes;
  private List<AndroidXMLNode> children = new ArrayList<AndroidXMLNode>();

  public AndroidXMLNode(String name, Map<String, String> attributes) {
    this.name = name;
    this.attributes = attributes;
  }

  public void addChild(AndroidXMLNode child) {
    children.add(child);
  }

  public String render() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format("<%s ", name));

    for (String attributeName : attributes.keySet()) {
      String attributeValue = attributes.get(attributeName);
      sb.append(String.format("%s=\"%s\" ", attributeName, attributeValue));
    }

    if (children.size() == 0) {
      sb.append("/>\n");
    } else {
      sb.append(">\n");

      for (AndroidXMLNode child : children) {
        sb.append(child.render());
      }

      sb.append(String.format("</%s>\n", name));
    }

    return sb.toString();
  }

  public List<AndroidXMLNode> getChildren() {
    return children;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }
}
