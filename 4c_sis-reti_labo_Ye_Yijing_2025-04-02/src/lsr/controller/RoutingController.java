package lsr.controller;

import lsr.model.data.NetworkNode;
import lsr.model.logic.LinkStateRoutingAlgorithm;

import java.util.*;

/**
 * Controller che coordina le operazioni tra modello e vista
 * Gestisce la logica applicativa e la comunicazione tra i componenti
 */
public class RoutingController {
    private final LinkStateRoutingAlgorithm routingAlgorithm;
    private final Set<NetworkNode> networkTopology;

    public RoutingController() {
        this.routingAlgorithm = new LinkStateRoutingAlgorithm();
        this.networkTopology = new HashSet<>();
    }

    /**
     * Costruisce la topologia di rete di esempio
     */
    public void buildSampleTopology() {
        NetworkNode nodeA = new NetworkNode(1);
        NetworkNode nodeB = new NetworkNode(2);
        NetworkNode nodeC = new NetworkNode(3);
        NetworkNode nodeD = new NetworkNode(4);
        NetworkNode nodeE = new NetworkNode(5);

        // Configurazione dei collegamenti (grafo non diretto)
        nodeA.addNeighbor(nodeB, 2);
        nodeA.addNeighbor(nodeC, 5);

        nodeB.addNeighbor(nodeA, 2);
        nodeB.addNeighbor(nodeC, 3);
        nodeB.addNeighbor(nodeD, 1);

        nodeC.addNeighbor(nodeA, 5);
        nodeC.addNeighbor(nodeB, 3);
        nodeC.addNeighbor(nodeD, 3);
        nodeC.addNeighbor(nodeE, 1);

        nodeD.addNeighbor(nodeB, 1);
        nodeD.addNeighbor(nodeC, 3);
        nodeD.addNeighbor(nodeE, 4);

        nodeE.addNeighbor(nodeC, 1);
        nodeE.addNeighbor(nodeD, 4);

        networkTopology.addAll(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE));
    }

    /**
     * Calcola i percorsi più brevi dal nodo specificato
     * @param startNodeId ID del nodo sorgente
     * @return Mappa con le distanze minime
     * @throws IllegalArgumentException se il nodo non esiste
     */
    public Map<NetworkNode, Integer> calculateShortestPaths(int startNodeId) {
        NetworkNode startNode = networkTopology.stream()
                .filter(node -> node.getId() == startNodeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nodo non trovato"));

        return routingAlgorithm.calculateShortestPaths(startNode, networkTopology);
    }

    public Set<NetworkNode> getNetworkTopology() {
        return Collections.unmodifiableSet(networkTopology);
    }
}
