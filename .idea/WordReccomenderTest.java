
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

        class WordRecommenderTest {

            private static final String dictionaryfile = "engDictionary.txt";
            private WordRecommender wr;

            @BeforeEach
            void setUp() throws FileNotFoundException {
                wr = new WordRecommender(DICT_PATH);
            }

            @Test
            void getSimilarity_identicalWords_fullLength() {
                double actual = wr.getSimilarity("cat", "cat");
                assertEquals(3.0, actual, 1e-6);
            }

            @Test
            void getSimilarity_oneLetterDifferent() {
                double actual = wr.getSimilarity("cat", "cut");
                assertEquals(2.0, actual, 1e-6);
            }

            @Test
            void getSimilaritydifferentLen() {
                double actual = wr.getSimilarity("cat", "coat");
                assertEquals(1.5, actual, 1e-6);
            }

            @Test
            void suggestions_generalProperties() {
                String misspelled = "morbit";
                ArrayList<String> suggestions = wr.getWordSuggestions(misspelled, 2, 0.5, 4);

                assertNotNull(suggestions);
                assertTrue(suggestions.size() <= 4);

                for (int i = 0; i < suggestions.size(); i++) {
                    String cand = suggestions.get(i);
                    int lengthDiff = Math.abs(cand.length() - misspelled.length());
                    assertTrue(lengthDiff <= 2);
                    double pct = commonPercentTest(misspelled, cand);
                    assertTrue(pct >= 0.5);

                    if (i + 1 < suggestions.size()) {
                        double s1 = wr.getSimilarity(misspelled, cand);
                        double s2 = wr.getSimilarity(misspelled, suggestions.get(i + 1));
                        assertTrue(s1 >= s2);
                    }
                }
            }

            @Test
            void suggestions_respectTopN() {
                ArrayList<String> suggestions = wr.getWordSuggestions("cot", 2, 0.3, 2);
                assertTrue(suggestions.size() <= 2);
            }

            @Test
            void suggestions_emptyWhenStrict() {
                ArrayList<String> suggestions = wr.getWordSuggestions("qzxqzx", 0, 1.0, 4);
                assertTrue(suggestions.isEmpty());
            }

            private static double commonPercentTest(String a, String b) {
                HashSet<Character> setA = new HashSet<>();
                for (int i = 0; i < a.length(); i++) setA.add(a.charAt(i));
                HashSet<Character> setB = new HashSet<>();
                for (int i = 0; i < b.length(); i++) setB.add(b.charAt(i));
                HashSet<Character> intersection = new HashSet<>(setA);
                intersection.retainAll(setB);
                HashSet<Character> union = new HashSet<>(setA);
                union.addAll(setB);
                if (union.isEmpty()) return 0.0;
                return (double) intersection.size() / union.size();
            }
        }
