package net.faustinelli.concurrent.desktop_search;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Muzietto on 27/03/2016.
 */
public class Indexer implements Runnable {
    private BlockingQueue<File> queue;

    @Override
    public void run() {
        while (true) {
            try {
                indexFile(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void indexFile(File file) {
        System.out.println(file.getName());
    }
}
