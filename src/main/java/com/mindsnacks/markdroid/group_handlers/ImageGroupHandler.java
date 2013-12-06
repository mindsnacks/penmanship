package com.mindsnacks.markdroid.group_handlers;

import java.net.URL;
import org.pegdown.Printer;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.Node;

/** Created by Tony Cosentini Date: 12/4/13 Time: 1:14 PM */
public class ImageGroupHandler extends BaseHandler {
  public ImageGroupHandler(Node rootNode, Printer printer) {
    super(rootNode, printer);
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

  public void handle() {
    ExpImageNode imageNode = (ExpImageNode)this.rootNode.getChildren().get(0);

    String imageFilenameWithoutExtension = imageNode.url.substring(0,
        imageNode.url.lastIndexOf('.'));
    String drawableSrc = String.format("@drawable/%s", imageFilenameWithoutExtension);

    printer.print("<ImageView android:layout_width=\"match_parent\"\n"
        + "  android:layout_height=\"wrap_content\"\n"
        + "  android:adjustViewBounds=\"true\"\n");
    printer.print(String.format("  android:src=\"%s\"", drawableSrc));
    printer.print("/>\n");
  }
}
