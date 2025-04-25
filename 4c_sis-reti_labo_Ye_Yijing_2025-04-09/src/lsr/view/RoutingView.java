package lsr.view;

import lsr.controller.*;
import lsr.model.data.NetworkNode;

import java.util.*;

/**
 * Classe per la visualizzazione dei risultati all'utente
 * Gestisce l'interfaccia utente e la presentazione dei dati
 */
public class RoutingView {
    private final RoutingController controller;

    public RoutingView(RoutingController controller) {
        this.controller = controller;
    }

    /**
     * Visualizza la topologia di rete
     */
    public void displayNetworkTopology() {
        System.out.println("\nTopologia di rete:");
        for (NetworkNode node : controller.getNetworkTopology()) {
            System.out.print("Nodo " + node.getId() + " collega a: ");
            node.getNeighbors().forEach((neighbor, cost) ->
                    System.out.print(neighbor.getId() + "(costo:" + cost + ") "));
            System.out.println();
        }
    }

    /**
     * Visualizza i percorsi più brevi da un nodo specifico
     * @param startNodeId ID del nodo sorgente
     */
    public void displayShortestPaths(int startNodeId) {
        try {
            Map<NetworkNode, Integer> shortestPaths = controller.calculateShortestPaths(startNodeId);

            System.out.println("\nDistanze minime dal nodo " + startNodeId + ":");
            shortestPaths.forEach((node, distance) -> {
                String distanceStr = (distance == Integer.MAX_VALUE) ?
                        "Irraggiungibile" : String.valueOf(distance);
                System.out.println("Nodo " + node.getId() + ": " + distanceStr);
            });
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**
     * Mostra un menu interattivo all'utente
     */
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu Link State Routing:");
            System.out.println("1. Visualizza topologia");
            System.out.println("2. Calcola percorsi da un nodo");
            System.out.println("3. Esci");
            System.out.print("Scelta: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayNetworkTopology();
                        break;
                    case 2:
                        System.out.print("Inserisci ID nodo sorgente (1-5): ");
                        int nodeId = scanner.nextInt();
                        displayShortestPaths(nodeId);
                        break;
                    case 3:
                        System.out.println("Uscita...");
                        return;
                    default:
                        System.out.println("Scelta non valida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input non valido. Inserire un numero.");
                scanner.next(); // Pulisce l'input errato
            }
        }
    }
}