package wordnet;

import java.util.*;

public class WordNetGraph {
    private Map<Integer, Set<Integer>> adjacents;

    public WordNetGraph() {
        adjacents = new HashMap<>();
    }

    public void addNode(int node) {
        adjacents.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(int start, int end) {
        addNode(start);
        addNode(end);

        adjacents.get(start).add(end);
    }

    public Set<Integer> getAdjacents(int node) {
        return adjacents.getOrDefault(node, new HashSet<>());
    }

    public Set<Integer> getChildren(Set<Integer> nodes) {
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>(nodes);

        while (!stack.isEmpty()) {
            int curr = stack.pop();
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int child : adjacents.getOrDefault(curr, Set.of())) {
                    stack.push(child);
                }
//                stack.addAll(getAdjacents(curr));
            }
        }
        return visited;
    }



}
