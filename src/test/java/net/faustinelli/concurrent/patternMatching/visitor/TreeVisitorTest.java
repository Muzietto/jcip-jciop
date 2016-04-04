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
public class TreeVisitorTest extends TestCase {
    public void testDepthEmptyTreeIsZero() throws Exception {
        Empty emptyTree = new Empty();
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(emptyTree);
        assertEquals(0, visitor.depth());
    }

    public void testDepthLeafIsOne() throws Exception {
        Leaf leaf = new Leaf("test");
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(leaf);
        assertEquals(1, visitor.depth());
    }

    public void testDepthNodeOfEmptiesIsOne() throws Exception {
        Node node = new Node(new Empty(), new Empty());
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(node);
        assertEquals(1, visitor.depth());
    }

    public void testDepthNodeOfOneLeaveIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Empty());
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(node);
        assertEquals(2, visitor.depth());
    }

    public void testDepthNodeOfTwoLeavesIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Leaf("right"));
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(node);
        assertEquals(2, visitor.depth());
    }

    public void testDepthNodeOfNodeOfLeavesIsTwo() throws Exception {
        Node node = new Node(new Leaf("left"), new Node(new Leaf("left2"), new Leaf("right2")));
        TreeDepthVisitor visitor = new TreeDepthVisitor();
        visitor.visit(node);
        assertEquals(3, visitor.depth());
    }}
