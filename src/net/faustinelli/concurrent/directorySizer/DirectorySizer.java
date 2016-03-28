/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.directorySizer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Marco Faustinelli (Muzietto) on 28/03/2016.
 * http://www.obsidianscheduler.com/blog/category/concurrency/
 */
public class DirectorySizer extends RecursiveTask<Long> {
    private List<File> mFiles;
    private boolean mAllFiles = true;

    public DirectorySizer(List<File> files) {
        this.mFiles = files;
        for (File file : files) {
            if (file.isDirectory()) {
                mAllFiles = false;
            }
        }
    }

    @Override
    protected Long compute() {
        if (mFiles.size() <= 4 && mAllFiles) {
            return computeLocal();
        } else {
            return forkAndJoin();
        }
    }

    private Long computeLocal() {
        long length = 0;
        for (File file : mFiles) {
            length += file.length();
        }
        return length;
    }

    private Long forkAndJoin() {
        List<File> dirsAndFiles = new ArrayList<File>();

        for (File file : mFiles) {
            if (file.isFile()) {
                dirsAndFiles.add(file);
            } else {
                dirsAndFiles.addAll(Arrays.asList(file.listFiles()));
            }
        }

        int rightSize = dirsAndFiles.size() / 2;
        int leftSize = dirsAndFiles.size() - rightSize;
        List<File> leftList = dirsAndFiles.subList(0, leftSize);
        List<File> rightList = dirsAndFiles.subList(leftSize, leftSize + rightSize);

        DirectorySizer d1 = new DirectorySizer(leftList);
        d1.fork();
        DirectorySizer d2 = new DirectorySizer(rightList);
        return d2.compute() + d1.join();
    }


    public static void main(String[] args) {
        List<File> files = Arrays.asList(new File("C:\\Users").listFiles());
        DirectorySizer sizer = new DirectorySizer(files);
        ForkJoinPool pool = new ForkJoinPool();
        Long size = pool.invoke(sizer);
        System.out.println(args[0] + " is " + size + " bytes");
    }
}
