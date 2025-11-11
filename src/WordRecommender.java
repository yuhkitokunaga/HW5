import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class WordRecommender {
    ArrayList<String> dictionaryContent;

    public WordRecommender(String dictionaryFile) throws FileNotFoundException {
        dictionaryContent = new ArrayList<>();
        FileInputStream input = new FileInputStream(dictionaryFile);
        Scanner sc = new Scanner(input);
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                dictionaryContent.add(line);
            }
        }
        sc.close();
    }
    // they have to be able to Calculate IF %  of characters in common are at least 50%
    // thr similarity should be close to .length of word, NVM


    public ArrayList<String> getWordSuggestions(String word,
                                                int tolerance,
                                                double commonPercent,
                                                int topN) {
        ArrayList<String> bestSuggest = new ArrayList<>();
        ArrayList<Double> similarNum = new ArrayList<>();

        for (int i = 0; i < dictionaryContent.size(); i++) {
            String candidate = dictionaryContent.get(i);
            int lengthSim = Math.abs(candidate.length() - word.length());
            if (lengthSim > tolerance) {
                continue;
            }
            double percent = helperCommonPercent(word, candidate);
            if (percent < commonPercent) {
                continue;
            }
            double sim = getSimilarity(word, candidate);
            int insertIndex = helperGetIndex(similarNum, sim);
            if (insertIndex < topN) {
                bestSuggest.add(insertIndex, candidate);
                similarNum.add(insertIndex, sim);
                if (bestSuggest.size() > topN) {
                    bestSuggest.remove(bestSuggest.size() - 1);
                    similarNum.remove(similarNum.size() - 1);
                }
            }
        }

        return bestSuggest;
    }

    private double helperCommonPercent(String one, String two) {
        HashSet<Character> setA = new HashSet<>();
        for (int i = 0; i < one.length(); i++) {
            setA.add(one.charAt(i));
        }
        HashSet<Character> setB = new HashSet<>();
        for (int i = 0; i < two.length(); i++) {
            setB.add(two.charAt(i));
        }
        HashSet<Character> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        HashSet<Character> union = new HashSet<>(setA);
        union.addAll(setB);
        if (union.isEmpty()) {
            return 0.0;
        }
        return (double) intersection.size() / union.size();
    }

    private int helperGetIndex(ArrayList<Double> scores, double sim) {
        int i = 0;
        while (i < scores.size() && sim <= scores.get(i)) {
            i++;
        }
        return i;
    }

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
            if (word1.charAt(word1.length() - 1 - i) ==
                    word2.charAt(word2.length() - 1 - i)) {
                rightSim++;
            }
        }
        return (rightSim + leftSim) / 2.0;
    }
}



    // final String shortest = List.of("hello", "yes", "no")
    //      .stream()
    //       .min(Comparator.comparing(String::length))??
    //      .get();

//          for (int i = 0; i < minLength)


