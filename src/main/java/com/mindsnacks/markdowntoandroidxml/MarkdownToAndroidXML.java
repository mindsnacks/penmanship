package com.mindsnacks.markdowntoandroidxml;

import java.io.File;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/** Created by Tony Cosentini Date: 11/26/13 Time: 4:50 PM */
public class MarkdownToAndroidXML {
  public static void main(String[] args) {
    System.out.println("Starting parser!");

    //String markdown = "Hello! **how** are you doing?\nThis should be another textview.\n\n![image junk](image description)";

    String markdown =
        "# Fuck yeah it's a header. Hello! **how** are you doing?\n\nIt has beautiful new\n\n![Alt text](/path/to/img.jpg)\n\nlines with _emphasis_ and escaped quotes\". [This is a link](http://google.com). Pretty cool, right?";

    PegDownProcessor pegDownProcessor = new PegDownProcessor();
    RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

    AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor();
    androidMarkdownVisitor.visit(rootNode);

    System.out.println(androidMarkdownVisitor.printer.getString());
  }

  public void convertMarkdownDirectoryToAndroidResources(File inputMarkdownDirectory, File outputResourceDirectory) {

  }
}