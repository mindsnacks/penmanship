package com.mindsnacks.markdroid;

import com.mindsnacks.markdroid.group_handlers.ImageGroupHandler;
import com.mindsnacks.markdroid.group_handlers.TextNodeGroupHandler;
import java.util.Map;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SuperNode;

/** Created by Tony Cosentini Date: 11/26/13 Time: 5:04 PM */
public class AndroidMarkdownVisitor extends BaseVisitor {
  private enum NodeGroupType {
    TEXT_NODE_GROUP, IMAGE_NODE_GROUP
  }

  private AndroidXMLNode rootLayoutNode;
  private AndroidXMLNode currentParentNode;

  private String namespace;
  private String customURIScheme;

  public static class Builder {
    private String namespace;
    private String customURIScheme;

    public Builder namespace(String namespace) {
      this.namespace = namespace;
      return this;
    }

    public Builder customURIScheme(String customURIScheme) {
      this.customURIScheme = customURIScheme;
      return this;
    }

    public AndroidMarkdownVisitor build() {
      return new AndroidMarkdownVisitor(this);
    }
  }

  public AndroidMarkdownVisitor(Builder builder) {
    this.namespace = builder.namespace;
    this.customURIScheme = builder.customURIScheme;
  }

  @Override
  public void visit(HeaderNode headerNode) {
    handleNodeGroup(headerNode, String.format("markdroid_header_%d", headerNode.getLevel()));
  }

  @Override
  public void visit(ParaNode paraNode) {
    visitChildren(paraNode);
  }

  @Override public void visit(OrderedListNode node) {
    for (int i = 0; i < node.getChildren().size(); i++) {
      Node child = node.getChildren().get(i);
      int bulletNumber = i + 1;
      handleListItem((ListItemNode)child, String.format("%d. ", bulletNumber));
    }
  }

  @Override
  public void visit(BulletListNode node) {
    for (Node child : node.getChildren()) {
      handleListItem((ListItemNode) child, "\u2022 ");
    }
  }

  public void handleListItem(ListItemNode node, String prependBullet) {
    for (Node child : node.getChildren()) {
      if (child instanceof RootNode) {
        RootNode root = (RootNode)child;

        for (Node childFromRoot : root.getChildren()) {
          handleNodeGroup(childFromRoot, "markdroid_list_item", prependBullet);
        }
      } else {
        throw new RuntimeException("Not a root node!");
      }
    }
  }

  @Override public void visit(BlockQuoteNode node) {
    Map<String, String> attributes = AndroidXMLConstants.getDefaultLinearLayoutAttributes();
    AndroidXMLNode blockquoteNode = new AndroidXMLNode(AndroidXMLConstants.LINEAR_LAYOUT, attributes);

    AndroidXMLNode previousParentNode = currentParentNode;
    currentParentNode.addChild(blockquoteNode);
    currentParentNode = blockquoteNode;

    visitChildren(node);

    currentParentNode = previousParentNode;
  }

  @Override
  public void visit(RootNode rootNode) {
    Map<String, String> rootAttributes = AndroidXMLConstants.getDefaultLinearLayoutAttributes();
    rootAttributes.put(AndroidXMLConstants.XMLNS_ANDROID, AndroidXMLConstants.ANDROID_NAMESPACE);
    rootAttributes.put(AndroidXMLConstants.ID, AndroidXMLConstants.ID_MARKDROID_CONTENT);
    rootAttributes.put(AndroidXMLConstants.STYLE, AndroidXMLConstants.STYLE_MARKDROID_CONTENT);

    rootLayoutNode = new AndroidXMLNode(AndroidXMLConstants.LINEAR_LAYOUT, rootAttributes);
    currentParentNode = rootLayoutNode;

    visitChildren(rootNode);
  }

  @Override
  public void visit(SuperNode superNode) {
    handleNodeGroup(superNode, "markdroid_text");
  }

  // Helpers
  private NodeGroupType getNodeGroupType(Node node) {
    if (TextNodeGroupHandler.isValidNodeGroup(node)) {
      return NodeGroupType.TEXT_NODE_GROUP;
    } else if (ImageGroupHandler.isValidNodeGroup(node)) {
      return NodeGroupType.IMAGE_NODE_GROUP;
    }else {
      throw new RuntimeException("Unable to determine node group type: " + node.getClass().toString());
    }
  }

  protected void visitChildren(SuperNode node) {
    for (Node child : node.getChildren()) {
      child.accept(this);
    }
  }

  private void handleNodeGroup(Node node, String style) {
    handleNodeGroup(node, style, null);
  }

  private void handleNodeGroup(Node node, String style, String prependText) {
    NodeGroupType nodeGroupType = getNodeGroupType(node);
    if (nodeGroupType.equals(NodeGroupType.TEXT_NODE_GROUP)) {
      TextNodeGroupHandler.Builder textNodeGroupHandlerBuilder = new TextNodeGroupHandler.Builder(node);
      textNodeGroupHandlerBuilder.style(style);
      textNodeGroupHandlerBuilder.prependText(prependText);
      textNodeGroupHandlerBuilder.namespace(namespace);
      textNodeGroupHandlerBuilder.customURIScheme(customURIScheme);

      TextNodeGroupHandler textNodeGroupHandler = textNodeGroupHandlerBuilder.build();
      currentParentNode.addChild(textNodeGroupHandler.render());
    } else {
      ImageGroupHandler.Builder imageGroupHandlerBuilder = new ImageGroupHandler.Builder(node);
      imageGroupHandlerBuilder.namespace(namespace);

      ImageGroupHandler imageGroupHandler = imageGroupHandlerBuilder.build();
      currentParentNode.addChild(imageGroupHandler.render());
    }
  }

  public String render() {
    return rootLayoutNode.render();
  }
}
