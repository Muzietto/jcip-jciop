/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.subtitles;

/**
 * Created by Marco Faustinelli (Muzietto) on 18/07/2016.
 */
public class Subtitle {

    public static String SRT_MATCHER = "(\\d+)\\n(\\d{2}:\\d{2}:\\d{2}).+-->.*(\\d{2}:\\d{2}:\\d{2}).+?\\n([\\S\\s]*?)\\n{2}";
}
