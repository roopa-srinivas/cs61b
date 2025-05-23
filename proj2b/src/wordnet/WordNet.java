package wordnet;

import net.sf.saxon.trans.SymbolicName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class WordNet {
    private Map<Integer, Set<String>> IDtoWords;
    private Map<String, Set<Integer>> wordToIDs;
    private WordNetGraph wnGraph;

    public WordNet(String synsFile, String hypFile) {
        IDtoWords = new HashMap<>();
        wordToIDs = new HashMap<>();
        wnGraph = new WordNetGraph();
        loadSynsets(synsFile);
        loadHyponyms(hypFile);
    }

    private void loadSynsets(String fileName) {
        try {
            for (String line : Files.readAllLines(Path.of(fileName))) {
                String[] lineParts = line.split(",", 3);
                int node = Integer.parseInt(lineParts[0]);
                Set<String> words = new HashSet<>(Arrays.asList(lineParts[1].split(" ")));
                IDtoWords.put(node, words);
                for (String word : words) {
                    wordToIDs.computeIfAbsent(word, k -> new HashSet<>()).add(node);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read synsets", e);
        }
    }

    private void loadHyponyms(String fileName) {
        try {
            for (String line : Files.readAllLines((Path.of(fileName)))) {
                String[] lineParts = line.split(",");
                int start = Integer.parseInt(lineParts[0]);
                for (int i = 1; i < lineParts.length; i++) {
                    int end = Integer.parseInt(lineParts[i]);
                    wnGraph.addEdge(start, end);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read hyponyms", e);
        }
    }

    public Set<String> getHyponyms(String word) {
        Set<Integer> synsetIDs = wordToIDs.getOrDefault(word, Set.of());
        Set<Integer> relevantChildren = new HashSet<>(synsetIDs);
        relevantChildren.addAll(wnGraph.getChildren(synsetIDs));
        Set<String> result = new HashSet<>();

        for (int node : relevantChildren) {
            result.addAll(IDtoWords.getOrDefault(node, Set.of()));
        }
        return result;
    }
}
