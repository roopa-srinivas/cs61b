package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    private final WordNet wn;
    private final NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        if (words == null || words.isEmpty()) {
            return "[]";
        }

        Set<String> common = null;

        for (String word : words) {
            Set<String> hyponyms = wn.getHyponyms(word);
            if (common == null) {
                common = new HashSet<>(hyponyms);
            } else {
                common.retainAll(hyponyms);
            }
        }

        if (common == null) {
            return "[]";
        }

        if (q.k() == 0) {
            List<String> sorted = new ArrayList<>(common);
            Collections.sort(sorted);
            return sorted.toString();
        } else {
            Map<String, Long> wordToCount = new HashMap<>();
            for (String word : common) {
                TimeSeries ts = ngm.countHistory(word, q.startYear(), q.endYear());
                long total = ts.values().stream().mapToLong(Double::longValue).sum();
                if (total > 0) {
                    wordToCount.put(word, total);
                }
            }

            List<Map.Entry<String, Long>> countSorted = new ArrayList<>(wordToCount.entrySet());
            countSorted.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));

            List<String> topK = new ArrayList<>();
            for (int i = 0; i < Math.min(q.k(), countSorted.size()); i++) {
                topK.add(countSorted.get(i).getKey());
            }
            Collections.sort(topK);
            return topK.toString();
        }

    }
}
