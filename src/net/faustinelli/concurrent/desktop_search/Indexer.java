/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 *
 */

package net.faustinelli.concurrent.desktop_search;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Marco Faustinelli (Muzietto) on 27/03/2016.
 */
public class Indexer implements Runnable {
    private BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("THREAD - starting indexer");
        while (true) {
            try {
                indexFile(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void indexFile(File file) {
        System.out.println(Thread.currentThread().toString() + " - INDEXER indexed " + file.getName());
    }
}
