package net.faustinelli.subtitles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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

    public Subtitle(String position, String startInstant, String endInstant, String text) {
        this.position = Integer.parseInt(position);
        this.startInstant = parseTimeString(startInstant);
        this.endInstant = parseTimeString(endInstant);
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
        Long hourInSeconds = TimeUnit.SECONDS.convert(Long.parseLong(pieces[0]), TimeUnit.HOURS);
        Long minuteInSeconds = TimeUnit.SECONDS.convert(Long.parseLong(pieces[1]), TimeUnit.MINUTES);
        Long seconds = Long.parseLong(pieces[2]);
        return hourInSeconds + minuteInSeconds + seconds;
    }

    public static class SubtitlePiecesCollector {

        public String position = "";
        public String instants = "";
        public StringBuilder text = new StringBuilder();
        public List<Subtitle> harvest = new ArrayList<>();

        public SubtitlePiecesCollector() {
        }

        public SubtitlePiecesCollector(List<Subtitle> subtitles) {
            this.harvest = new ArrayList<>(subtitles);
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
            SubtitlePiecesCollector result = new SubtitlePiecesCollector(this.harvest);
            result.harvest.add(newOne);
            return result;
        }
    }

    public static void main(String args[]) throws FileNotFoundException {

//        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        String filename = "I:/software/experiments/module_1/src/main/resources/subtitles/samples/example1.srt";
        File file = new File(filename);

        URL url = new Object().getClass().getResource(filename);
        //File file = new File(url.getPath());

        //System.out.println(new Subtitle("1","00:02:17","00:02:20","Senator, we're making\n our final approach into Coruscant.").parseTimeString("01:01:13"));

        //read file into stream, try-with-resources
        try {

            Stream<String> stream = Files.lines(Paths.get(filename));
//            stream
//                .filter(PRED_TEXT)
//                .forEach(System.out::println);

            List<Subtitle> subs = stream
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
                                acc.text.append((acc.text.length()==0) ? line : "\n" + line);
                            }
                            if (Subtitle.PRED_EMPTY_LINE.test(line)) {
                                System.out.println("FLUSHING");
                                acc = acc.flushSubtitle();
                            }
                            return acc;
                        },
                        (a, b) -> a).harvest;

            String pippo = "";

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
