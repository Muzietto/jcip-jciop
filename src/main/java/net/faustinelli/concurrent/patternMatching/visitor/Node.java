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
public class Node implements Tree {
    private Tree left;
    private Tree right;

    public Node(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(TreeVisitor visitor) {
        visitor.visit(this);

    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }
}
