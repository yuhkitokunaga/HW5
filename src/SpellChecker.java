import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;


public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader; // DO NOT MODIFY
    private Set<String> dictionary;
    private WordRecommender recommender;

    public SpellChecker() {
        inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
        dictionary = new HashSet<>();

    }

    public void start() {
        String dictFileName = promptDictionary();
        loadDict(dictFileName);
        recommender = new WordRecommender(dictFileName);
        String inputFileName = promptForInput();
        spellCheck(inputFileName);
        inputReader.close();  // DO NOT MODIFY - must be the last line of this method!
    }

    private String promptDictionary() {
        while (true) {
            System.out.printf(Util.DICTIONARY_PROMPT);
            String fileName = inputReader.nextLine();
            File x = new File(fileName);

            if (x.exists()) {
                System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION);
                return fileName;
            } else {
                System.out.printf(Util.FILE_OPENING_ERROR);
            }
        }
    }

    private void loadDict(String fileName) {
        File dictFile = new File(fileName);

        try {
            Scanner fileReader = new Scanner(dictFile);
            while (fileReader.hasNextLine()) {
                String word = fileReader.nextLine();
                word = word.trim();
                if (!word.isEmpty()) {
                    dictionary.add(word);

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open " + fileName);
        }
    }

    private String promptForInput() {
        while (true) {
            System.out.printf(Util.FILENAME_PROMPT);
            String fileName = inputReader.nextLine();

            File inputFile = new File(fileName);
            if (inputFile.exists()) {
                String outputName = makeOutputName(fileName);
                System.out.printf(Util.FILE_SUCCESS_NOTIFICATION);
                return fileName;
            } else {
                System.out.printf(Util.FILE_OPENING_ERROR);
            }

        }
    }

    private String makeOutputName(String inputFile) {
        int period = inputFile.lastIndexOf(".");
        if (period == -1) {
            return inputFile + "_chk.txt";
        }
        return inputFile.substring(0, period) + "_chk.txt";
    }

    private void spellCheck(String inputFileName) {
        String outputFileName = makeOutputName(inputFileName);
        Scanner fileReader = null;
        PrintWriter printer = null;

        try {
            fileReader = new Scanner(new File(inputFileName));
            printer = new PrintWriter(outputFileName);
            while (fileReader.hasNext()) {
                String word = fileReader.next();

                if (!dictionary.contains(word)) {
                    word = misspelledWord(word);
                }

                printer.print(word + " ");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open file " + inputFileName);
        } finally {
            if (fileReader != null) fileReader.close();
            if (printer != null) printer.close();
        }
    }

    private String misspelledWord(String word) {
        System.out.printf(Util.MISSPELL_NOTIFICATION, word);
        ArrayList<String> wordSuggestions = recommender.getWordSuggestions(word, 2, 0.5, 4);

        if (wordSuggestions == null || wordSuggestions.isEmpty()) {
            System.out.printf(Util.NO_SUGGESTIONS);
            return promptAOrT(word);
        } else {
            System.out.printf(Util.FOLLOWING_SUGGESTIONS);
            for (int i = 0; i < wordSuggestions.size(); i++) {
                System.out.printf(Util.SUGGESTION_ENTRY, i + 1, wordSuggestions.get(i));
            }
            return promptRAorT(word, wordSuggestions);
        }
    }

    private String promptAOrT(String originalWord) {
        while (true) {
            System.out.printf(Util.TWO_OPTION_PROMPT);
            String inputtedChoice = inputReader.nextLine();

            if (inputtedChoice.equals("a")) {
                return originalWord;
            } else if (inputtedChoice.equals("t")) {
                System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                return inputReader.nextLine();
            } else {
                System.out.printf(Util.INVALID_RESPONSE);
            }
        }
    }

    private String promptRAorT(String originalWord, ArrayList<String> wordSuggestions) {
        while (true) {
            System.out.printf(Util.THREE_OPTION_PROMPT);
            String inputtedChoice = inputReader.nextLine();

            if (inputtedChoice.equals("a")) {
                return originalWord;
            } else if (inputtedChoice.equals("t")) {
                System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                return inputReader.nextLine();
            } else if (inputtedChoice.equals("r")) {
                System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                int option = inputReader.nextInt();
                inputReader.nextLine();
                if (option >= 1 && option <= wordSuggestions.size()) {
                    return wordSuggestions.get(option - 1);
                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            } else {
                System.out.printf(Util.INVALID_RESPONSE);
            }
        }
    }
}