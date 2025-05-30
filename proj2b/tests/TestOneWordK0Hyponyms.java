import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;
import wordnet.WordNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

/** Tests the most basic case for Hyponyms where the list of words is one word long, and k = 0.*/
public class TestOneWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    // ngrams files
    public static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String SMALL_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";

    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms1000-subgraph.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets1000-subgraph.txt";

    // EECS files
    private static final String FREQUENCY_EECS_FILE = "data/ngrams/frequency-EECS.csv";
    private static final String HYPONYMS_EECS_FILE = "data/wordnet/hyponyms-EECS.txt";
    private static final String SYNSETS_EECS_FILE = "data/wordnet/synsets-EECS.txt";

    @Test
    public void testActK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = new ArrayList<>();
        words.add("act");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[act, action, change, demotion, human_action, human_activity, variation]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDescent() {
        WordNet wn = new WordNet("data/wordnet/synsets11.txt", "data/wordnet/hyponyms11.txt");
        Set<String> expected = Set.of("descent", "jump", "parachuting");
        assertEquals(expected, wn.getHyponyms("descent"));
    }


    @Test
    public void testChange() {
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        Set<String> expected = Set.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation");
        assertEquals(expected, wn.getHyponyms("change"));
    }

//    @Test
//    public void testTransition() {
//        WordNet wn = new WordNet(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
//        Set<String> expected = Set.of("transition", "jump", "leap", "saltation", "flashback");
//        assertEquals(expected, wn.getHyponyms("transition"));
//    }
//
//    @Test
//    public void testMutation() {
//        WordNet wn = new WordNet(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
//        Set<String> expected = Set.of("mutation");
//        assertEquals(expected, wn.getHyponyms("mutation"));
//    }
}
