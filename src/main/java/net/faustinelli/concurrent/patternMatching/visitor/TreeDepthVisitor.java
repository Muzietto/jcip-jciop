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

    @Override
    public Integer visit(Empty empty) {
        return 0;
    }

    @Override
    public Integer visit(Leaf leaf) {
        return 1;
    }

    @Override
    public Integer visit(Node node) {
        Integer leftDepth = (Integer) node.getLeft().accept(this);
        Integer rightDepth = (Integer) node.getRight().accept(this);
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
