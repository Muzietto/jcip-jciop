/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.patternMatching.visitor;

import sun.reflect.generics.visitor.Visitor;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class Leaf implements Tree {
    private String value;

    public Leaf(String value) {
        this.value = value;
    }

    @Override
    public Object accept(TreeVisitor visitor) {
        return visitor.visit(this);
    }
}
