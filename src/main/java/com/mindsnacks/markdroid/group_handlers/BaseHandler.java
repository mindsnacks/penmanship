package com.mindsnacks.markdroid.group_handlers;

import com.mindsnacks.markdroid.AndroidXMLNode;
import org.pegdown.ast.Node;

/** Created by Tony Cosentini Date: 12/4/13 Time: 11:50 AM */
public abstract class BaseHandler {
  protected Node rootNode;

  public BaseHandler(Node rootNode) {
    this.rootNode = rootNode;
  }

  public abstract AndroidXMLNode render();
}