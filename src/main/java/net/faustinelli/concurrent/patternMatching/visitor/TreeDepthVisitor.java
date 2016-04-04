/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.patternMatching.visitor;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class TreeDepthVisitor implements TreeVisitor {
    private int depth;

    @Override
    public void visit(Empty empty) {
    }

    @Override
    public void visit(Leaf leaf) {
        depth++;
    }

    @Override
    public void visit(Node node) {
        depth++;
        TreeDepthVisitor leftVisitor = new TreeDepthVisitor();
        TreeDepthVisitor rightVisitor = new TreeDepthVisitor();
        node.getLeft().accept(leftVisitor);
        node.getRight().accept(rightVisitor);
        depth += Math.max(leftVisitor.depth(), rightVisitor.depth());
    }

    public int depth() {
        return depth;
    }
}
