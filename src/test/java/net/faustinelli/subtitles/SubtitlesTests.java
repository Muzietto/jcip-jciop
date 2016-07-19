<<<<<<< HEAD
/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.subtitles;

import org.junit.Test;

/**
 * Created by Marco Faustinelli (Muzietto) on 18/07/2016.
 */
public class SubtitlesTests {

    @Test
    public void testReadAndMatch() throws Exception {


=======
package net.faustinelli.subtitles;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SubtitlesTests {

    @Test
    public void handleCollectorTestStartingFromPosition() {

        StringBuilder stbu = new StringBuilder();
        stbu.append("Very well,");
        stbu.append("\n");
        stbu.append("my friend");

        Subtitle.SubtitlePiecesCollector collo = new Subtitle.SubtitlePiecesCollector();
        collo.subtitles.add(new Subtitle("00", "00:00:00", "00:00:00", "test message"));
        collo.position = "01";
        collo.instants = "01:02:03,4  --> 05:06:07,08";
        collo.text = stbu;

        Subtitle.SubtitlePiecesCollector col2 = collo.flushSubtitle();

        assertEquals(2, col2.subtitles.size());
        assertEquals(0, col2.text.length());
        assertEquals("", col2.position);
        assertEquals("", col2.instants);
        Subtitle subtitle = col2.subtitles.get(1);
        assertEquals(new Integer(1), subtitle.position);
        assertEquals(new Long(3723L), subtitle.startInstant);
        assertEquals(new Long(18367L), subtitle.endInstant);
        assertEquals("Very well,\nmy friend", subtitle.text);

    }

    @Test
    public void testIncreaseInstants() {
        Subtitle sub = new Subtitle("12", "00:01:02", "03:04:05", "dear\nfriend");

        sub.increaseInstants(TimeUnit.HOURS, 1);
        assertEquals("01:01:02 --> 04:04:05", sub.instantsString());
        sub.increaseInstants(TimeUnit.HOURS, -1);
        assertEquals("00:01:02 --> 03:04:05", sub.instantsString());

        sub.increaseInstants(TimeUnit.MINUTES, 1);
        assertEquals("00:02:02 --> 03:05:05", sub.instantsString());
        sub.increaseInstants(TimeUnit.MINUTES, -1);
        assertEquals("00:01:02 --> 03:04:05", sub.instantsString());

        sub.increaseInstants(TimeUnit.SECONDS, 1);
        assertEquals("00:01:03 --> 03:04:06", sub.instantsString());
        sub.increaseInstants(TimeUnit.SECONDS, -1);
        assertEquals("00:01:02 --> 03:04:05", sub.instantsString());
    }

    @Test
    public void testNastyIncreases() {
        Subtitle sub = new Subtitle("12", "00:59:52", "03:59:55", "dear\nfriend");

        sub.increaseInstants(TimeUnit.MINUTES, 1);
        assertEquals("01:00:52 --> 04:00:55", sub.instantsString());
        sub.increaseInstants(TimeUnit.MINUTES, -1);
        assertEquals("00:59:52 --> 03:59:55", sub.instantsString());

        sub.increaseInstants(TimeUnit.SECONDS, 10);
        assertEquals("01:00:02 --> 04:00:05", sub.instantsString());
        sub.increaseInstants(TimeUnit.SECONDS, -10);
        assertEquals("00:59:52 --> 03:59:55", sub.instantsString());
    }

    @Test
    public void testIncreaseAllInstants() {
        Subtitle sub1 = new Subtitle("12", "00:59:52", "03:59:55", "dear\nfriend");
        Subtitle sub2 = new Subtitle("13", "00:59:56", "03:59:57", "dear\nfriends");
        Subtitle sub3 = new Subtitle("14", "00:59:58", "03:59:59", "dear\nfriendss");

        List<Subtitle> subs = new ArrayList<>();
        subs.add(sub1);
        subs.add(sub2);
        subs.add(sub3);

        Subtitle.SubtitlePiecesCollector collo = new Subtitle.SubtitlePiecesCollector(subs);
        collo.increaseInstants(TimeUnit.SECONDS, 10);

        List<Subtitle> newSubs = collo.subtitles;
        Subtitle sub10 = newSubs.get(0);
        assertEquals("12\n01:00:02 --> 04:00:05\ndear\nfriend\n", sub10.toString());
        Subtitle sub11 = newSubs.get(1);
        assertEquals("13\n01:00:06 --> 04:00:07\ndear\nfriends\n", sub11.toString());
        Subtitle sub12 = newSubs.get(2);
        assertEquals("14\n01:00:08 --> 04:00:09\ndear\nfriendss\n", sub12.toString());
    }

    @Test
    public void splitInstantsTest() {
        String[] xxx = Subtitle.splitInstants("01:02:03,4  --> 05:06:07,08");
        assertEquals("01:02:03", xxx[0]);
        assertEquals("05:06:07", xxx[1]);
    }

    @Test
    public void testSubtitlePrintings() {
        Subtitle sub = new Subtitle("12", "00:01:02", "03:04:05", "dear\nfriend");

        assertEquals("00:01:02 --> 03:04:05", sub.instantsString());
        assertEquals("12\n00:01:02 --> 03:04:05\ndear\nfriend\n", sub.toString());
    }

    @Test
    public void readFileIntoSubtitlesListTest() throws IOException {
        String filename = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1.srt";

        List<Subtitle> subtitles = Subtitle.readFileIntoSubtitles(filename);

        Subtitle sub0 = subtitles.get(0);
        assertEquals("1\n00:02:17 --> 00:02:20\nSenator, we're making\nour final approach into Coruscant.\n", sub0.toString());
        Subtitle sub1 = subtitles.get(1);
        assertEquals("2\n00:02:20 --> 00:02:22\nVery good, Lieutenant.\n", sub1.toString());
    }

    @Test
    public void writeSubtitlesListIntoFileTest() throws IOException {
        String input = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1.srt";
        String output = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1_out.srt";
        List<Subtitle> subtitles1 = Subtitle.readFileIntoSubtitles(input);

        new Subtitle.SubtitlePiecesCollector(subtitles1).dumpToFile(output);

        List<Subtitle> subtitles2 = Subtitle.readFileIntoSubtitles(output);
        IntStream.range(0,1).forEach(i -> {
            assertEquals(subtitles1.get(i).toString(), subtitles2.get(i).toString());
        });
    }

    @Test
    public void readFileModifyInstantsTest() throws IOException {
        String filename = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1.srt";

        List<Subtitle> subtitles = Subtitle.readFileIntoSubtitles(filename);

        Subtitle.increaseInstants(subtitles, TimeUnit.HOURS, 1);

        Subtitle sub0 = subtitles.get(0);
        assertEquals("1\n01:02:17 --> 01:02:20\nSenator, we're making\nour final approach into Coruscant.\n", sub0.toString());
        Subtitle sub1 = subtitles.get(1);
        assertEquals("2\n01:02:20 --> 01:02:22\nVery good, Lieutenant.\n", sub1.toString());
>>>>>>> origin/master
    }
}
