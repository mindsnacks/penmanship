package com.mindsnacks.markdowntoandroidxml;

import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 * Created by Tony Cosentini
 * Date: 11/26/13
 * Time: 4:50 PM
 */
public class MarkdownToAndroidXML {
    public static void main(String[] args) {
        System.out.println("Starting parser!");

        String markdown = "Hello! **how** are you doing?\nThis should be another textview.\n\n![image junk](image description)";

        PegDownProcessor pegDownProcessor = new PegDownProcessor();
        RootNode rootNode = pegDownProcessor.parseMarkdown(markdown.toCharArray());

        AndroidMarkdownVisitor androidMarkdownVisitor = new AndroidMarkdownVisitor();
        androidMarkdownVisitor.visit(rootNode);
    }
}

/*
org.pegdown.ast.ParaNode
  org.pegdown.ast.SuperNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.SpecialTextNode
    org.pegdown.ast.SpecialTextNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.StrongEmphSuperNode
    org.pegdown.ast.StrongEmphSuperNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.TextNode
  org.pegdown.ast.SuperNode
org.pegdown.ast.ParaNode

org.pegdown.ast.ParaNode
  org.pegdown.ast.SuperNode
    org.pegdown.ast.RefImageNode
    org.pegdown.ast.RefImageNode
    org.pegdown.ast.TextNode
    org.pegdown.ast.TextNode
  org.pegdown.ast.SuperNode
org.pegdown.ast.ParaNode
*/