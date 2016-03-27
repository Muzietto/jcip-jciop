package net.faustinelli.concurrent.desktop_search;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Muzietto on 27/03/2016.
 * http://alvinalexander.com/blog/post/java/how-implement-java-filefilter-list-files-directory
 */
public class ImageFileFilter implements FileFilter {
    private final String[] okFileExtensions = new String[]{"jpg", "png", "gif"};

    @Override
    public boolean accept(File pathname) {
        for (String extension :
                okFileExtensions) {
            if (pathname.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
