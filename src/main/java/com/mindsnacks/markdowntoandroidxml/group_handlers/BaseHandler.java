package com.mindsnacks.markdowntoandroidxml.group_handlers;

import org.pegdown.Printer;
import org.pegdown.ast.Node;

/**
 * Created by Tony Cosentini
 * Date: 12/4/13
 * Time: 11:50 AM
 */
public class BaseHandler {
    protected Node rootNode;
    protected Printer printer;

    public BaseHandler(Node rootNode, Printer printer) {
        this.rootNode = rootNode;
        this.printer = printer;
    }

    protected String getStringWithEscapedQuotes(String text) {
        return text.replace("\"", "\\\"");
    }

    protected void printWithEscapedQuotes(String text) {
        String escapedText = getStringWithEscapedQuotes(text);
        printer.print(escapedText);
    }
}
