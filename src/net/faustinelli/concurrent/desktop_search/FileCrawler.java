package net.faustinelli.concurrent.desktop_search;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Muzietto on 27/03/2016.
 */
public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileQueue = new ArrayBlockingQueue<File>(1024);
    private final FileFilter fileFilter = new ImageFileFilter();
    private final File root =  new File("");


    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File entry :
                    entries) {
                if (entry.isDirectory()) {
                  crawl(entry);
                } else if (!alreadyIndexed(entry)){
                  fileQueue.put(entry);
                }
            }
        }
    }

    private boolean alreadyIndexed(File entry) {
        return false;
    }
}
