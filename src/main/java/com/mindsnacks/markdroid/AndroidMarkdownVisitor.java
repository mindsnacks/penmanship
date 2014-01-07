package com.mindsnacks.markdroid;

import com.mindsnacks.markdroid.group_handlers.ImageGroupHandler;
import com.mindsnacks.markdroid.group_handlers.TextNodeGroupHandler;
import org.pegdown.Printer;
import org.pegdown.ast.*;

/** Created by Tony Cosentini Date: 11/26/13 Time: 5:04 PM */
public class AndroidMarkdownVisitor extends BaseVisitor {
  Printer printer = new Printer();

  private enum NodeGroupType {
    TEXT_NODE_GROUP, IMAGE_NODE_GROUP
  }

  @Override
  public void visit(HeaderNode headerNode) {
    createTextView(headerNode, String.format("header_%d", headerNode.getLevel()));
  }

  @Override
  public void visit(ParaNode paraNode) {
    visitChildren(paraNode);
  }

  @Override
  public void visit(RootNode rootNode) {
    printer.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
    printer.print("<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
        + "  android:id=\"@+id/markdown_content\"\n"
        + "  style=\"@style/markdown_content\"\n"
        + "  android:layout_width=\"match_parent\"\n"
        + "  android:layout_height=\"wrap_content\"\n"
        + "  android:orientation=\"vertical\">\n");

    visitChildren(rootNode);

    printer.print("</LinearLayout>\n");
  }

  @Override
  public void visit(SuperNode superNode) {
    createTextView(superNode, "markdroid_text");
  }

  @Override
  public void visit(Node node) {
  }

  // Helpers
  private NodeGroupType getNodeGroupType(Node node) {
    if (TextNodeGroupHandler.isValidNodeGroup(node)) {
      return NodeGroupType.TEXT_NODE_GROUP;
    } else if (ImageGroupHandler.isValidNodeGroup(node)) {
      return NodeGroupType.IMAGE_NODE_GROUP;
    }else {
      throw new RuntimeException("Unable to determine node group type.");
    }
  }

  protected void visitChildren(SuperNode node) {
    for (Node child : node.getChildren()) {
      child.accept(this);
    }
  }

  private void createTextView(Node node, String style) {
    NodeGroupType nodeGroupType = getNodeGroupType(node);
    if (nodeGroupType.equals(NodeGroupType.TEXT_NODE_GROUP)) {
      printer.print("<TextView android:layout_width=\"match_parent\"\n" +
          "  android:layout_height=\"wrap_content\"\n");

      if (style != null) {
        printer.print(String.format("  style=\"@style/%s\"\n", style));
        printer.print(String.format("  android:tag=\"%s\"\n", style));
      }

      printer.print("  android:text=\"");

      handleTextNodeGroup(node);

      printer.print("\"/>\n");
    } else {
      handleImageNodeGroup(node);
    }
  }

  private void handleTextNodeGroup(Node node) {
    TextNodeGroupHandler textNodeGroupHandler = new TextNodeGroupHandler(node, printer);
    textNodeGroupHandler.handle();
  }

  private void handleImageNodeGroup(Node node) {
    ImageGroupHandler imageGroupHandler = new ImageGroupHandler(node, printer);
    imageGroupHandler.handle();
  }
}
