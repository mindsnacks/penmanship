package com.mindsnacks.markdowntoandroidxml;

import org.pegdown.ast.*;

/**
 * Created by Tony Cosentini
 * Date: 11/26/13
 * Time: 5:04 PM
 */
public class AndroidMarkdownVisitor implements Visitor {
    @Override
    public void visit(AbbreviationNode abbreviationNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(AutoLinkNode autoLinkNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(BlockQuoteNode blockQuoteNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(BulletListNode bulletListNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(CodeNode codeNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(DefinitionListNode definitionListNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(DefinitionNode definitionNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(DefinitionTermNode definitionTermNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ExpImageNode expImageNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ExpLinkNode expLinkNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(HeaderNode headerNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(HtmlBlockNode htmlBlockNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(InlineHtmlNode inlineHtmlNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ListItemNode listItemNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(MailLinkNode mailLinkNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(OrderedListNode orderedListNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ParaNode paraNode) {
        visitChildren(paraNode);
    }

    @Override
    public void visit(QuotedNode quotedNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(ReferenceNode referenceNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(RefImageNode refImageNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(RefLinkNode refLinkNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(RootNode rootNode) {
        visitChildren(rootNode);
    }

    @Override
    public void visit(SimpleNode simpleNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(SpecialTextNode specialTextNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(StrongEmphSuperNode strongEmphSuperNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableBodyNode tableBodyNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableCaptionNode tableCaptionNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableCellNode tableCellNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableColumnNode tableColumnNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableHeaderNode tableHeaderNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableNode tableNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TableRowNode tableRowNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(VerbatimNode verbatimNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(WikiLinkNode wikiLinkNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(TextNode textNode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(SuperNode superNode) {
        visitChildren(superNode);
    }

    @Override
    public void visit(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    // Helpers
    protected void visitChildren(SuperNode node) {
        for (Node child : node.getChildren()) {
            System.out.println("Going to visit: " + child.getClass());
            child.accept(this);
            System.out.println("Done visiting: " + child.getClass());
        }
    }
}
