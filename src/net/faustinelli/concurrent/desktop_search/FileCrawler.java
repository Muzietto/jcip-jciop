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
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Marco Faustinelli (Muzietto) on 27/03/2016.
 */
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> queue, FileFilter filter, File root) {
        this.fileQueue = queue;
        this.fileFilter = filter;
        this.root = root;
    }


    @Override
    public void run() {
        System.out.println("THREAD - starting crawler");
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void crawl(File root) throws InterruptedException {
        System.out.println(Thread.currentThread().toString() + " CRAWLER about to crawl " + root.getName());
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File entry : entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                } else if (!alreadyIndexed(entry)) {
                    System.out.println(Thread.currentThread().toString() + " CRAWLER enqueuing " + entry.getName());
                    fileQueue.put(entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File entry) {
        return false;
    }
}
