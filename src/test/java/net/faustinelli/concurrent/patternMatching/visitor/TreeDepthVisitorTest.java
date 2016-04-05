/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.patternMatching.visitor;

import junit.framework.TestCase;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class TreeDepthVisitorTest extends TestCase {

    public void testDepthEmptyTreeIsZero() throws Exception {
        Empty emptyTree = new Empty();
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(0), emptyTree.accept(visitor));
    }

    public void testDepthLeafIsOne() throws Exception {
        Leaf leaf = new Leaf("test");
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(1), leaf.accept(visitor));
    }

    public void testDepthNodeOfEmptiesIsOne() throws Exception {
        Node node = new Node(new Empty(), new Empty());
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(1), node.accept(visitor));
    }

    public void testDepthNodeOfOneLeaveIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Empty());
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(2), node.accept(visitor));
    }

    public void testDepthNodeOfTwoLeavesIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Leaf("right"));
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(2), node.accept(visitor));
    }

    public void testDepthNodeOfNodeOfLeavesIsThree() throws Exception {
        Node node = new Node(new Leaf("left"), new Node(new Leaf("left2"), new Leaf("right2")));
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(3), node.accept(visitor));
    }

    public void testComplexTree() throws Exception {
        Node node = new Node(new Leaf("left"), new Node(new Node(new Leaf("left3"), new Leaf("right3")), new Leaf("right2")));
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        assertEquals(new Integer(4), node.accept(visitor));
    }
}
