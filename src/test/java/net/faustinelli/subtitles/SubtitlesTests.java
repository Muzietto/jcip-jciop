package net.faustinelli.subtitles;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Subtitle subtitle = col2.harvest.get(1);
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
}
