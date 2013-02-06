package org.sculptor.dsl.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

public class SculptordslSemanticHighlightingCalculator implements ISemanticHighlightingCalculator {

    private static final Set<String> TERMINAL_KEYWORDS = new HashSet<String>();
    {
        TERMINAL_KEYWORDS.add("=>");
        TERMINAL_KEYWORDS.add("delegates to");
        TERMINAL_KEYWORDS.add("<->");
        TERMINAL_KEYWORDS.add("opposite");
        TERMINAL_KEYWORDS.add("!");
        TERMINAL_KEYWORDS.add("not");
        TERMINAL_KEYWORDS.add("-");
        TERMINAL_KEYWORDS.add("reference");
        TERMINAL_KEYWORDS.add("Map");
    }

    public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
        if (resource == null)
            return;

        if (resource.getContents().size() > 0) {
            Iterable<INode> allNodes = resource.getParseResult().getRootNode().getAsTreeIterable();
            for (INode node : allNodes) {
                if (node instanceof ILeafNode) {
                    ILeafNode leafNode = (ILeafNode) node;
                    // TODO check returned valuse of GrammarElement
                    if ("doc".equals(leafNode.getGrammarElement())) {
                        acceptor.addPosition(node.getOffset(), node.getLength(),
                        		DefaultHighlightingConfiguration.COMMENT_ID);
                    } else if (TERMINAL_KEYWORDS.contains(leafNode.getText())) {
                        acceptor.addPosition(node.getOffset(), node.getLength(),
                        		DefaultHighlightingConfiguration.KEYWORD_ID);
                    }
                }
            }
        }
    }

}