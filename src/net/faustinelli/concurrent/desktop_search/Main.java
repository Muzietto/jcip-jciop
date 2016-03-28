package net.faustinelli.concurrent.desktop_search;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Muzietto on 28/03/2016.
 */
public class Main {
    private static final int BOUND = 48;

    public static void main(String[] args) {
        startIndexing(new File[]{new File("C:\\Users")});

    }

    private static void startIndexing(File[] strings) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = new ImageFileFilter();

        for (File root : strings) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }
    }
}
