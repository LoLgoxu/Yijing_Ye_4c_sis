package lsr.model.logic;

import lsr.model.data.NetworkNode;

import java.util.*;

/**
 * Implementa l'algoritmo di Dijkstra per il calcolo dei percorsi più brevi
 * Questa classe contiene la logica di business principale
 */
public class LinkStateRoutingAlgorithm {
    private static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Calcola i percorsi più brevi dal nodo sorgente a tutti gli altri nodi
     * @param startNode Nodo sorgente
     * @param allNodes Insieme di tutti i nodi nella topologia
     * @return Mappa con le distanze minime per ogni nodo
     * @throws IllegalArgumentException se startNode è null o non è presente in allNodes
     */
    public Map<NetworkNode, Integer> calculateShortestPaths(NetworkNode startNode, Set<NetworkNode> allNodes) {
        validateInput(startNode, allNodes);

        // Inizializzazione delle strutture dati
        Map<NetworkNode, Integer> distances = initializeDistances(startNode, allNodes);
        Map<NetworkNode, NetworkNode> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = initializePriorityQueue(distances);

        // Algoritmo principale
        while (!priorityQueue.isEmpty()) {
            NetworkNode currentNode = priorityQueue.poll().getNode();

            for (Map.Entry<NetworkNode, Integer> neighborEntry : currentNode.getNeighbors().entrySet()) {
                relaxEdge(currentNode, neighborEntry.getKey(), neighborEntry.getValue(),
                        distances, previousNodes, priorityQueue);
            }
        }

        return distances;
    }

    private void validateInput(NetworkNode startNode, Set<NetworkNode> allNodes) {
        if (startNode == null) {
            throw new IllegalArgumentException("Il nodo sorgente non può essere null");
        }
        if (!allNodes.contains(startNode)) {
            throw new IllegalArgumentException("Il nodo sorgente non è presente nella topologia");
        }
    }

    private Map<NetworkNode, Integer> initializeDistances(NetworkNode startNode, Set<NetworkNode> allNodes) {
        Map<NetworkNode, Integer> distances = new HashMap<>();
        allNodes.forEach(node ->
                distances.put(node, node.equals(startNode) ? 0 : INFINITY));
        return distances;
    }

    private PriorityQueue<NodeDistance> initializePriorityQueue(Map<NetworkNode, Integer> distances) {
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>();
        distances.forEach((node, dist) -> queue.add(new NodeDistance(node, dist)));
        return queue;
    }

    private void relaxEdge(NetworkNode current, NetworkNode neighbor, int edgeCost,
                           Map<NetworkNode, Integer> distances, Map<NetworkNode, NetworkNode> previousNodes,
                           PriorityQueue<NodeDistance> queue) {
        int alternativeDistance = distances.get(current) + edgeCost;

        if (alternativeDistance < distances.get(neighbor)) {
            distances.put(neighbor, alternativeDistance);
            previousNodes.put(neighbor, current);
            updatePriorityQueue(queue, neighbor, alternativeDistance);
        }
    }

    private void updatePriorityQueue(PriorityQueue<NodeDistance> queue, NetworkNode node, int newDistance) {
        queue.removeIf(nd -> nd.getNode().equals(node));
        queue.add(new NodeDistance(node, newDistance));
    }

    /**
     * Classe helper per la coda di priorità
     */
    private static class NodeDistance implements Comparable<NodeDistance> {
        private final NetworkNode node;
        private final int distance;

        public NodeDistance(NetworkNode node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        public NetworkNode getNode() {
            return node;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
