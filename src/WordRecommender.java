import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordRecommender{
    ArrayList<String> dictionaryContent;
    public WordRecommender(String dictionaryFile){
        dictionaryContent = new ArrayList<>();
        FileInputStream input = new FileInputStream(dictionaryFile);
        Scanner sc = new Scanner(input);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            dictionaryContent.add(line);
    }  sc.close();

        }


        public double getSimilarity (String word1, String word2){
            // TODO: change this!
            return 0.0;
        }

        public ArrayList<String> getWordSuggestions (String word,int tolerance, double commonPercent, int topN){

            String s = "8,90,-7";
            Scanner scnr = new Scanner(s).useDelimiter(",");
            return null;
        }
        // making changes to commit
        // You can of course write other methods as well.
    }
