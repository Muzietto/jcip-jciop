<<<<<<< HEAD
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
=======
package net.faustinelli.subtitles;

import static java.util.concurrent.TimeUnit.HOURS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Subtitle {

    public static String SRT_PATTERN = "(\\d+)\\n(\\d{2}:\\d{2}:\\d{2}).*-->\\s+(\\d{2}:\\d{2}:\\d{2}).+?\\n([\\s\\S]+)\\n{2}";

    public static String SRT_POSITION = "^\\d+$";
    public static String SRT_INSTANTS = "(^\\d{2}:\\d{2}:\\d{2}).*-->\\s+(\\d{2}:\\d{2}:\\d{2}).*$";
    public static String SRT_EMPTY_LINE = "^(?![\\s\\S])";

    public static Pattern PATTERN_POSITION = Pattern.compile(SRT_POSITION);
    public static Pattern PATTERN_INSTANTS = Pattern.compile(SRT_INSTANTS);
    public static Pattern PATTERN_EMPTY_LINE = Pattern.compile(SRT_EMPTY_LINE);

    public static Predicate<String> PRED_POSITION = line -> PATTERN_POSITION.matcher(line).matches();
    public static Predicate<String> PRED_INSTANTS = line -> PATTERN_INSTANTS.matcher(line).matches();
    public static Predicate<String> PRED_TEXT = line -> !PATTERN_POSITION.matcher(line).matches()
                                                        && !PATTERN_INSTANTS.matcher(line).matches()
                                                        && !PATTERN_EMPTY_LINE.matcher(line).matches();
    public static Predicate<String> PRED_EMPTY_LINE = line -> PATTERN_EMPTY_LINE.matcher(line).matches();

    public Integer position = null;
    public Long startInstant = null;
    public Long endInstant = null;
    public String text = "";

    public Subtitle(String position, String instants, String text) {
        this(position, splitInstants(instants)[0], splitInstants(instants)[1], text);
    }

    public Subtitle(String position, String startInstantString, String endInstantString, String text) {
        this.position = Integer.parseInt(position);
        this.startInstant = parseTimeString(startInstantString);
        this.endInstant = parseTimeString(endInstantString);
        this.text = text;
    }

    // sample input: 01:12:23,345   --> 12:23:34,456
    public static String[] splitInstants(String instants) {
        String[] result = instants.split("-->");
        result[0] = result[0].trim().split(",")[0];
        result[1] = result[1].trim().split(",")[0];
        return result;
    }

    // sample input: 01:12:23
    private Long parseTimeString(String instant) {
        String[] pieces = instant.split(":");
        Long hourInSeconds = TimeUnit.SECONDS.convert(Long.parseLong(pieces[0]), HOURS);
        Long minuteInSeconds = TimeUnit.SECONDS.convert(Long.parseLong(pieces[1]), TimeUnit.MINUTES);
        Long seconds = Long.parseLong(pieces[2]);
        return hourInSeconds + minuteInSeconds + seconds;
    }

    public String instantsString() {
        return instantString(this.startInstant) + " --> " + instantString(this.endInstant);
    }

    private String instantString(Long instant) {

        Long hours = instant / 3600L;
        Long minutes = (instant - hours * 3600) / 60;
        Long seconds = instant - hours * 3600 - minutes * 60;

        return String.format("%02d", Integer.parseInt(hours.toString())) + ":"
               + String.format("%02d", Integer.parseInt(minutes.toString())) + ":"
               + String.format("%02d", Integer.parseInt(seconds.toString()));
    }

    @Override
    public String toString() {
        return ""
               + this.position.toString() + "\n"
               + instantsString() + "\n"
               + this.text.toString() + "\n";
    }

    public void increaseInstants(TimeUnit unit, Integer i) {
        switch (unit) {
            case HOURS: {
                this.startInstant += 3600 * i;
                this.endInstant += 3600 * i;
                break;
            }
            case MINUTES: {
                this.startInstant += 60 * i;
                this.endInstant += 60 * i;
                break;
            }
            case SECONDS: {
                this.startInstant += i;
                this.endInstant += i;
                break;
            }
            default: {}
        }
    }

    public static void increaseInstants(List<Subtitle> subtitles, TimeUnit unit, Integer i) {
        subtitles.stream().forEach(sub -> sub.increaseInstants(unit, i));
    }

    public static class SubtitlePiecesCollector {

        public String position = "";
        public String instants = "";
        public StringBuilder text = new StringBuilder();
        public List<Subtitle> subtitles = new ArrayList<>();

        public SubtitlePiecesCollector() {
        }

        public SubtitlePiecesCollector(List<Subtitle> subtitles) {
            this.subtitles = new ArrayList<>(subtitles);
        }

        public SubtitlePiecesCollector flushSubtitle() {
            if ("".equals(this.position)) {
                throw new RuntimeException("Cannot build the subtitle without position");
            }
            if ("".equals(this.instants)) {
                throw new RuntimeException("Cannot build the subtitle without instants");
            }
            if ("".equals(this.text)) {
                throw new RuntimeException("Cannot build the subtitle without text");
            }

            Subtitle newOne = new Subtitle(this.position, this.instants, this.text.toString());
            SubtitlePiecesCollector result = new SubtitlePiecesCollector(this.subtitles);
            result.subtitles.add(newOne);
            return result;
        }

        public void dumpToFile(String filename) throws IOException {
            Stream<String> subsStream = this.subtitles.stream().map(Subtitle::toString);
            Iterable<String> iterableSubtitles = subsStream::iterator;

            Files.write(Paths.get(filename), iterableSubtitles);

        }

        public void increaseInstants(TimeUnit unit, int i) {
            this.subtitles.stream().forEach(sub -> sub.increaseInstants(unit, i));
        }
    }

    public static List<Subtitle> readFileIntoSubtitles(String filename) throws IOException {

        return Files.lines(Paths.get(filename))
            .reduce(new SubtitlePiecesCollector(),
                    (SubtitlePiecesCollector acc, String line) -> {
                        if (Subtitle.PRED_POSITION.test(line)) {
                            System.out.println("position=" + line);
                            acc.position = line;
                        }
                        if (Subtitle.PRED_INSTANTS.test(line)) {
                            System.out.println("instants=" + line);
                            acc.instants = line;
                        }
                        if (Subtitle.PRED_TEXT.test(line)) {
                            System.out.println("newtext=" + line);
                            acc.text.append((acc.text.length() == 0) ? line : "\n" + line);
                        }
                        if (Subtitle.PRED_EMPTY_LINE.test(line)) {
                            System.out.println("FLUSHING");
                            acc = acc.flushSubtitle();
                        }
                        return acc;
                    },
                    (a, b) -> a).subtitles;
    }

    public static void main(String[] args) throws IOException {
        String input = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1.srt";
        String output = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1_out.srt";
        List<Subtitle> subtitles1 = Subtitle.readFileIntoSubtitles(input);

        SubtitlePiecesCollector collector = new SubtitlePiecesCollector(subtitles1);
        collector.increaseInstants(TimeUnit.SECONDS, 27);
        collector.dumpToFile(output);
    }
>>>>>>> origin/master
}
