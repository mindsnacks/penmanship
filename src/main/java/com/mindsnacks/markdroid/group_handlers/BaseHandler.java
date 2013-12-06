package com.mindsnacks.markdroid.group_handlers;

import org.pegdown.Printer;
import org.pegdown.ast.Node;

/** Created by Tony Cosentini Date: 12/4/13 Time: 11:50 AM */
public abstract class BaseHandler {
  protected Node rootNode;
  protected Printer printer;

  public BaseHandler(Node rootNode, Printer printer) {
    this.rootNode = rootNode;
    this.printer = printer;
  }
}
