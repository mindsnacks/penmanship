package com.mindsnacks.markdroid.group_handlers;

import com.mindsnacks.markdroid.AndroidXMLConstants;
import com.mindsnacks.markdroid.AndroidXMLNode;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.pegdown.FastEncoder;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;

/**
 * Created by Tony Cosentini Date: 12/4/13 Time: 11:49 AM
 */
public class TextNodeGroupHandler extends BaseHandler {
  private String style;
  private String prependText;

  protected TextNodeGroupHandler(Builder builder) {
    super(builder);
    this.style = builder.style;
    this.prependText = builder.prependText;
  }

  public static class Builder extends BaseHandler.Builder {
    private String style;
    private String prependText;

    public Builder(Node rootNode) {
      super(rootNode);
    }

    public Builder style(String style) {
      this.style = style;
      return this;
    }

    public Builder prependText(String prependText) {
      this.prependText = prependText;
      return this;
    }

    public TextNodeGroupHandler build() {
      return new TextNodeGroupHandler(this);
    }
  }

  @Override
  public AndroidXMLNode render() {
    StringBuilder stringBuilder = new StringBuilder();

    if (prependText != null) {
      FastEncoder.encode(prependText, stringBuilder);
    }

    return handleTextNodeGroup(this.rootNode, stringBuilder);
  }

  private AndroidXMLNode handleTextNodeGroup(Node node, StringBuilder stringBuilder) {
    Map<String, String> attributes = AndroidXMLConstants.getDefaultLayoutSizes();

    for (Node child : node.getChildren()) {
      Class childClass = child.getClass();

      if (childClass.equals(TextNode.class)) {
        TextNode textNode = (TextNode) child;
        FastEncoder.encode(textNode.getText(), stringBuilder);
      } else if (childClass.equals(SpecialTextNode.class)) {
        SpecialTextNode textNode = (SpecialTextNode) child;
        FastEncoder.encode(textNode.getText(), stringBuilder);
      } else if (childClass.equals(StrongEmphSuperNode.class)) {
        StrongEmphSuperNode strongEmphSuperNode = (StrongEmphSuperNode) child;

        String tag = "i";
        if (strongEmphSuperNode.isStrong()) {
          tag = "b";
        }

        FastEncoder.encode(String.format("<%s>", tag), stringBuilder);
        handleTextNodeGroup(strongEmphSuperNode, stringBuilder);
        FastEncoder.encode(String.format("</%s>", tag), stringBuilder);
      } else if (childClass.equals(ExpLinkNode.class)) {
        ExpLinkNode linkNode = (ExpLinkNode) child;

        URI uri;

        try {
          uri = new URI(linkNode.url);
        } catch (URISyntaxException e) {
          throw new RuntimeException(String.format("Unable to parse URI: %s", linkNode.url), e);
        }

        if (namespace != null && uri.getScheme().equals(customURIScheme)) {
          File uriPath = new File(uri.getPath());
          String filenameWithNameSpace = String.format("%s_%s", namespace, uriPath.getName());
          String newPath = String.format("%s/%s", uriPath.getParent(), filenameWithNameSpace);
          try {
            uri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), newPath, uri.getQuery(), uri.getFragment());
          } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating new URI with namespace and custom scheme.", e);
          }
        }

        stringBuilder.append(FastEncoder.encode(String.format("<a href=\"%s\">", uri.toString())));
        handleTextNodeGroup(linkNode, stringBuilder);
        stringBuilder.append(FastEncoder.encode("</a>"));
      } else if (childClass.equals(SuperNode.class)) {
        handleTextNodeGroup(child, stringBuilder);
      } else {
        throw new RuntimeException("Unexpected node type: " + childClass);
      }
    }

    attributes.put(AndroidXMLConstants.TEXT, stringBuilder.toString());
    if (style != null) {
      attributes.put(AndroidXMLConstants.STYLE, String.format("@style/%s", style));
      attributes.put(AndroidXMLConstants.TAG, style);
    }

    AndroidXMLNode textViewNode = new AndroidXMLNode(AndroidXMLConstants.TEXT_VIEW, attributes);
    return textViewNode;
  }

  private static List<Class> validTextNodes = new ArrayList<Class>(
      Arrays.asList(TextNode.class, SpecialTextNode.class, StrongEmphSuperNode.class,
          ExpLinkNode.class, ListItemNode.class));

  private static boolean isValidTextNode(Node node) {
    return validTextNodes.contains(node.getClass());
  }

  public static boolean isValidNodeGroup(Node node) {
    boolean result = true;

    for (Node child : node.getChildren()) {
      result = result && isValidTextNode(child);
    }

    return result;
  }
}
