package com.mindsnacks.penmanship.test.elements;

import com.mindsnacks.penmanship.AndroidMarkdownVisitor;
import com.mindsnacks.penmanship.AndroidXMLNode;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 * Created by tonyc on 1/20/14.
 */
public class ElementTest {
    protected AndroidXMLNode convertMarkdownToAndroidNodes(String markdown) {
      return convertMarkdownToAndroidNodes(markdown, null, null);
    }

    protected AndroidXMLNode convertMarkdownToAndroidNodes(String markdown, String customURIScheme, String customNamespace) {
      PegDownProcessor pegDownProcessor = new PegDownProcessor();
      RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

      AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor.Builder().customURIScheme(customURIScheme).namespace(customNamespace).build();
      androidMarkdownVisitor.visit(rootNode);

      return androidMarkdownVisitor.getRootLayoutNode();
    }
}
