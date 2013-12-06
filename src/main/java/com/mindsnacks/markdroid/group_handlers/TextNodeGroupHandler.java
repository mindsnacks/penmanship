package com.mindsnacks.markdroid.group_handlers;

import org.pegdown.Printer;
import org.pegdown.ast.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tony Cosentini
 * Date: 12/4/13
 * Time: 11:49 AM
 */
public class TextNodeGroupHandler extends BaseHandler {
    public TextNodeGroupHandler(Node rootNode, Printer printer) {
        super(rootNode, printer);
    }

    public void handle() {
        handleTextNodeGroup(this.rootNode);
    }

    private void handleTextNodeGroup(Node node) {
        for (Node child : node.getChildren()) {
            Class childClass = child.getClass();

            if (childClass.equals(TextNode.class)) {
                TextNode textNode = (TextNode)child;
                printer.printEncoded(textNode.getText());
            } else if (childClass.equals(SpecialTextNode.class)) {
                SpecialTextNode textNode = (SpecialTextNode)child;
                printer.printEncoded(textNode.getText());
            } else if (childClass .equals(StrongEmphSuperNode.class)) {
                StrongEmphSuperNode strongEmphSuperNode = (StrongEmphSuperNode)child;

                String tag = "i";
                if (strongEmphSuperNode.isStrong()) {
                    tag = "b";
                }

                printer.printEncoded(String.format("<%s>", tag));
                handleTextNodeGroup(strongEmphSuperNode);
                printer.printEncoded(String.format("</%s>", tag));
            } else if (childClass.equals(ExpLinkNode.class)) {
                ExpLinkNode linkNode = (ExpLinkNode)child;
                printer.printEncoded(String.format("<a href=\\\"%s\\\">", linkNode.url));
                handleTextNodeGroup(linkNode);
                printer.printEncoded("</a>");
            } else if (childClass.equals(SuperNode.class)) {
                handleTextNodeGroup((SuperNode)child);
            } else {
                throw new RuntimeException("Unexpected node type: " + childClass);
            }
        }
    }

    private static List<Class> validTextNodes = new ArrayList<Class>(Arrays.asList(TextNode.class, SpecialTextNode.class, StrongEmphSuperNode.class, ExpLinkNode.class));

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
