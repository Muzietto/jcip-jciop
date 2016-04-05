/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.patternMatching.visitor;

import junit.framework.TestCase;

import java.util.AbstractMap;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class TreeDeepestLeafVisitorTest extends TestCase {

    private String value(Object result) {
        return ((AbstractMap.SimpleImmutableEntry<String, Integer>)result).getKey();
    }

    public void testDeepestLeafOfEmptyTreeIsEmptyString() throws Exception {
        Empty emptyTree = new Empty();
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("", value(emptyTree.accept(visitor)));
    }

    public void testDeepestLeafOfLeafIsLeafOfString() throws Exception {
        Leaf leaf = new Leaf("test");
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("test", value(leaf.accept(visitor)));
    }

    public void testDepthNodeOfEmptiesIsOne() throws Exception {
        Node node = new Node(new Empty(), new Empty());
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("", value(node.accept(visitor)));
    }

    public void testDepthNodeOfOneLeaveIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Empty());
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("left", value(node.accept(visitor)));
    }

    public void testDepthNodeOfTwoLeavesIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Leaf("right"));
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("left", value(visitor.visit(node)));
    }

    public void testDepthNodeOfNodeOfLeavesIsThree() throws Exception {
        Node node = new Node(new Leaf("left"), new Node(new Leaf("left2"), new Leaf("right2")));
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("left2", value(visitor.visit(node)));
    }

    public void testComplexTree() throws Exception {
        Node node = new Node(new Leaf("left"), new Node(new Node(new Leaf("left3"), new Leaf("right3")), new Leaf("right2")));
        TreeDeepestLeafVisitor visitor = new TreeDeepestLeafVisitor();
        assertEquals("left3", value(visitor.visit(node)));
    }
}
