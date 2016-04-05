/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.patternMatching.visitor;

import java.util.AbstractMap;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class TreeDeepestLeafVisitor implements TreeVisitor {

    @Override
    public AbstractMap.SimpleImmutableEntry<String, Integer> visit(Empty empty) {
        return new AbstractMap.SimpleImmutableEntry("", 0);
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<String, Integer> visit(Leaf leaf) {
        return new AbstractMap.SimpleImmutableEntry(leaf.getValue(), 1);
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<String, Integer> visit(Node node) {
        AbstractMap.SimpleImmutableEntry<String, Integer> leftResult = (AbstractMap.SimpleImmutableEntry<String, Integer>) node.getLeft().accept(this);
        AbstractMap.SimpleImmutableEntry<String, Integer> rightResult = (AbstractMap.SimpleImmutableEntry<String, Integer>) node.getRight().accept(this);
        Integer leftDepth = leftResult.getValue();
        Integer rightDepth = rightResult.getValue();
        return (leftDepth >= rightDepth) ?
                new AbstractMap.SimpleImmutableEntry<String, Integer>(leftResult.getKey(), 1 + leftDepth) :
                new AbstractMap.SimpleImmutableEntry<String, Integer>(rightResult.getKey(), 1 + rightDepth);
    }
}
