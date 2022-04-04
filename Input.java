package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

enum TestMode {
    ARI,
    FK,
    SMOG,
    CL,
    ALL
}

public class Input {

    protected double characters;
    protected double words;
    protected double sentences;
    protected double syllables;
    protected double polysyllables;
    protected double l;
    protected double s;
    private String source;
    protected TestMode testMode;

    Input(String[] args) {
        readFile(args);
        System.out.println("The text is:");
        System.out.println(source);
        initInput();
        System.out.println("Words: " + (int) words);
        System.out.println("Sentences: " + (int) sentences);
        System.out.println("Characters: " + (int) characters);
        System.out.println("Syllables: " + (int) syllables);
        System.out.println("Polysyllables: " + (int) polysyllables);
        System.out.println("Enter the score you want to " +
                "calculate (ARI, FK, SMOG, CL, all): ");
        setTestMode();
    }

    private void readFile(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            source = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initInput() {
        characters = Arrays.stream(source
                .replaceAll("[ \\n\\t]", "")
                .split("")).count();
        words = Arrays.stream(source.split("\\s+")).count();
        sentences = Arrays.stream(source.split("[!.?]")).count();
        int count = 0;
        polysyllables = 0;
        String[] wordsTemp = source.split("[!.?]*\\s");
        for (String word : wordsTemp) {
            Pattern pat = Pattern.compile("(?i)[aeuioy]{1,2}");
            Matcher mat = pat.matcher(word);
            long temp = mat.results().count();
            if (word.charAt(word.length() - 1) == 'e') {
                temp -= 1;
            }
            if (word.equalsIgnoreCase("you")) {
                temp = 0;
            }
            if (temp == 0) {
                count++;
            } else if (temp > 2) {
                count += temp;
                polysyllables++;
            } else count +=temp;
        }
        syllables = count;
        l = characters / words * 100;
        s = sentences / words * 100;
    }

    private void setTestMode() {
        Scanner sc = new Scanner(System.in);
        switch (sc.nextLine().toLowerCase()) {
            case "ari" -> testMode = TestMode.ARI;
            case "fk" -> testMode = TestMode.FK;
            case "smog" -> testMode = TestMode.SMOG;
            case "cl" -> testMode = TestMode.CL;
            case "all" -> testMode = TestMode.ALL;
            default -> System.out.println("Error");
        }
    }
}
