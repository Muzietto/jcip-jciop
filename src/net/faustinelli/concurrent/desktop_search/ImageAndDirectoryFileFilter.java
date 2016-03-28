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

/**
 * Created by Marco Faustinelli (Muzietto) on 27/03/2016.
 * http://alvinalexander.com/blog/post/java/how-implement-java-filefilter-list-files-directory
 */
public class ImageAndDirectoryFileFilter implements FileFilter {
    private final String[] okFileExtensions = new String[]{"jpg", "png", "gif"};

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        for (String extension : okFileExtensions) {
            if (pathname.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
