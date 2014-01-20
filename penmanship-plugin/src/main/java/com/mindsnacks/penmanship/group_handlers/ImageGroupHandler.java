package com.mindsnacks.penmanship.group_handlers;

import com.mindsnacks.penmanship.AndroidXMLConstants;
import com.mindsnacks.penmanship.AndroidXMLNode;
import java.util.Map;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.Node;

/** Created by Tony Cosentini Date: 12/4/13 Time: 1:14 PM */
public class ImageGroupHandler extends BaseHandler {
  public ImageGroupHandler(Builder builder) {
    super(builder);
  }

  public static class Builder extends BaseHandler.Builder {
    public Builder(Node rootNode) {
      super(rootNode);
    }

    @Override public ImageGroupHandler build() {
      return new ImageGroupHandler(this);
    }
  }

  public static boolean isValidNodeGroup(Node node) {
    if (node.getChildren().size() != 1) return false;

    boolean result = true;

    for (Node child : node.getChildren()) {
      result = result && isValidImageNode(child);
    }

    return result;
  }

  private static boolean isValidImageNode(Node node) {
    return node.getClass().equals(ExpImageNode.class);
  }

  @Override public AndroidXMLNode render() {
    ExpImageNode imageNode = (ExpImageNode)rootNode.getChildren().get(0);

    String imageFilenameWithoutExtension = imageNode.url.substring(0,
        imageNode.url.lastIndexOf('.'));

    String drawableSrc;

    if (namespace != null) {
      drawableSrc = String.format("@drawable/%s_%s", namespace, imageFilenameWithoutExtension);
    } else {
      drawableSrc = String.format("@drawable/%s", imageFilenameWithoutExtension);
    }

    Map<String, String> attributes = AndroidXMLConstants.getDefaultLayoutSizes();
    attributes.put(AndroidXMLConstants.ADJUST_VIEW_BOUNDS, AndroidXMLConstants.TRUE);
    attributes.put(AndroidXMLConstants.SOURCE, drawableSrc);
    attributes.put(AndroidXMLConstants.STYLE, "@style/markdroid_image");

    AndroidXMLNode imageViewNode = new AndroidXMLNode(AndroidXMLConstants.IMAGE_VIEW, attributes);
    return imageViewNode;
  }
}
