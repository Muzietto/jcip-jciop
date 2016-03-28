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
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Marco Faustinelli (Muzietto) on 28/03/2016.
 */
public class Main {
    private static final int BOUND = 48;

    public static void main(String[] args) {
        startIndexing(new File[]{
                new File("C:\\Users"),
                new File("C:\\Temp"),
                new File("C:\\Windows"),
                new File("C:\\Python27")
        });

    }

    private static void startIndexing(File[] strings) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = new ImageAndDirectoryFileFilter();

        for (File root : strings) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        new Thread(new Indexer(queue)).start();
        new Thread(new Indexer(queue)).start();
        new Thread(new Indexer(queue)).start();
        new Thread(new Indexer(queue)).start();
    }
}
