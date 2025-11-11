import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordRecommender {
    ArrayList<String> dictionaryContent;

    public WordRecommender(String dictionaryFile) {
        dictionaryContent = new ArrayList<>();
        FileInputStream input = new FileInputStream(dictionaryFile);
        Scanner sc = new Scanner(input);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            dictionaryContent.add(line);
        }
        sc.close();

    }


    // final String shortest = List.of("hello", "yes", "no")
    //      .stream()
    //       .min(Comparator.comparing(String::length))
    //      .get();

//          for (int i = 0; i < minLength)

    // Calculate IF %  of characters in common are at least 50%
    // similarity should be close to .length of word, NVM


    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
        HashSet<String> setA = new HashSet<>();
        for (String i : setA) {
            setA.add(i);
        }
        HashSet<String> setB = new HashSet<>();
        for (String i : setB) {
            setB.add(i);
        }
        tolerance = setA.size() - setB.size();
        commonPercent = setA.size() /setB.size();


        return 0;
    }

    String s = "8,90,-7";
    Scanner scnr = new Scanner(s).useDelimiter(",");


    public double getSimilarity(String word1, String word2) {
        String shortWord;
        int leftSim = 0;
        int rightSim = 0;
        if (word1.length() <= word2.length()) {
            shortWord = word1;
        } else {
            shortWord = word2;
        }

        for (int i = 0; i < shortWord.length(); i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                leftSim++;
            }
        }
        for (int i = 0; i < shortWord.length(); i++) {
            if (word1.charAt(word1.length() - 1 - i) == word2.charAt(word2.length() - 1 - i)) {
                rightSim++;
            }
        }
        return (rightSim + leftSim) / 2.0;


    }
}
