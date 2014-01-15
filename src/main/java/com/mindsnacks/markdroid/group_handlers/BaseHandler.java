package com.mindsnacks.markdroid.group_handlers;

import com.mindsnacks.markdroid.AndroidXMLNode;
import org.pegdown.ast.Node;

/** Created by Tony Cosentini Date: 12/4/13 Time: 11:50 AM */
public abstract class BaseHandler {
  protected Node rootNode;
  protected String namespace;
  protected String customURIScheme;

  protected BaseHandler(Builder builder) {
    this.rootNode = builder.rootNode;
    this.namespace = builder.namespace;
    this.customURIScheme = builder.customURIScheme;
  }

  public abstract AndroidXMLNode render();

  public abstract static class Builder {
    private Node rootNode;
    private String namespace;
    private String customURIScheme;

    public Builder(Node rootNode) {
      this.rootNode = rootNode;
    }

    public Builder namespace(String namespace) {
      this.namespace = namespace;
      return this;
    }

    public Builder customURIScheme(String customURIScheme) {
      this.customURIScheme = customURIScheme;
      return this;
    }

    public abstract BaseHandler build();
  }
}