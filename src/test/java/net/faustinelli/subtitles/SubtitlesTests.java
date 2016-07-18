package net.faustinelli.subtitles;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class SubtitlesTests {

    @Test
    public void handleCollectorTestStartingFromPosition() {

        StringBuilder stbu = new StringBuilder();
        stbu.append("Very well,");
        stbu.append("\n");
        stbu.append("my friend");

        Subtitle.SubtitlePiecesCollector collo = new Subtitle.SubtitlePiecesCollector();
        collo.harvest.add(new Subtitle("00", "00:00:00", "00:00:00", "test message"));
        collo.position = "01";
        collo.instants = "01:02:03,4  --> 05:06:07,08";
        collo.text = stbu;

        Subtitle.SubtitlePiecesCollector col2 = collo.flushSubtitle();

        assertEquals(2, col2.harvest.size());
        assertEquals(0, col2.text.length());
        assertEquals("", col2.position);
        assertEquals("", col2.instants);
        Subtitle subtitle = col2.harvest.get(0);
        assertEquals(new Integer(1), subtitle.position);

    }

    @Test
    public void splitInstantsTest() {
        String[] xxx = Subtitle.splitInstants("01:02:03,4  --> 05:06:07,08");
        assertEquals("01:02:03", xxx[0]);
        assertEquals("05:06:07", xxx[1]);
    }

    @Test
    public void readFileIntoSubtitlesListTest() {

    }
}
