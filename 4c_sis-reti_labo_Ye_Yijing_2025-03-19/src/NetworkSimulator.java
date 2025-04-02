import java.util.HashMap;
import java.util.Map;

/**
 * Simula una rete di nodi che eseguono il protocollo Distance Vector.
 * Gestisce l'inizializzazione della rete, l'esecuzione dell'algoritmo
 * e la visualizzazione dei risultati.
 */
public class NetworkSimulator {
    /** Mappa dei nodi nella rete (nodeId -> DistanceVectorNode) */
    private final Map<String, DistanceVectorNode> nodes;

    /**
     * Costruttore che inizializza la rete con la topologia specificata.
     * La topologia è basata sull'esempio del libro ma con costi modificati
     * per testare meglio l'algoritmo:
     *
     *     A
     *  2/  \5
     * B     C
     *  \1 /
     *    D
     */
    public NetworkSimulator() {
        this.nodes = new HashMap<>();
        initializeNetwork();
    }

    /**
     * Inizializza la topologia di rete con nodi e collegamenti.
     * I costi dei link sono impostati per creare un caso interessante
     * dove il percorso ottimale non è sempre il link diretto.
     */
    private void initializeNetwork() {
        // Nodo A collegato a B (costo 2) e C (costo 5)
        nodes.put("A", new DistanceVectorNode("A", Map.of(
                "B", 2,
                "C", 5
        )));

        // Nodo B collegato ad A (costo 2) e D (costo 1)
        nodes.put("B", new DistanceVectorNode("B", Map.of(
                "A", 2,
                "D", 1
        )));

        // Nodo C collegato ad A (costo 5) e D (costo 1)
        nodes.put("C", new DistanceVectorNode("C", Map.of(
                "A", 5,
                "D", 1
        )));

        // Nodo D collegato a B (costo 1) e C (costo 1)
        nodes.put("D", new DistanceVectorNode("D", Map.of(
                "B", 1,
                "C", 1
        )));
    }

    /**
     * Esegue la simulazione dell'algoritmo DV fino alla convergenza.
     * @param maxIterations Il numero massimo di iterazioni consentite
     * @return true se la convergenza è stata raggiunta, false altrimenti
     */
    public boolean runSimulation(int maxIterations) {
        int iteration = 0;
        boolean updated;

        do {
            iteration++;
            updated = false;
            System.out.println("\n--- Iteration " + iteration + " ---");

            // Fase 1: Tutti i nodi preparano i loro DV
            Map<String, DistanceVectorMessage> messages = new HashMap<>();
            for (Map.Entry<String, DistanceVectorNode> entry : nodes.entrySet()) {
                messages.put(entry.getKey(), entry.getValue().sendDistanceVector());
            }

            // Fase 2: Tutti i nodi ricevono e processano i DV
            for (DistanceVectorNode node : nodes.values()) {
                for (String neighborId : node.getRoutingTable().getTable().keySet()) {
                    // Invia solo ai vicini diretti
                    if (node.getRoutingTable().getNextHop(neighborId).equals(neighborId)) {
                        DistanceVectorMessage message = messages.get(neighborId);
                        if (node.receiveDistanceVector(message)) {
                            updated = true;
                        }
                    }
                }
            }

            // Stampa lo stato corrente della rete
            printCurrentRoutingTables();

        } while (updated && iteration < maxIterations);

        // Stampa il risultato della convergenza
        printConvergenceStatus(!updated, iteration);
        return !updated;
    }

    /**
     * Stampa le tabelle di routing correnti di tutti i nodi.
     */
    private void printCurrentRoutingTables() {
        for (DistanceVectorNode node : nodes.values()) {
            System.out.println(node);
        }
    }

    /**
     * Stampa lo stato di convergenza dell'algoritmo.
     * @param converged true se l'algoritmo è convergito
     * @param iterations Il numero di iterazioni eseguite
     */
    private void printConvergenceStatus(boolean converged, int iterations) {
        if (converged) {
            System.out.println("\nConvergenza raggiunta in " + iterations + " iterazioni!");
        } else {
            System.out.println("\nAttenzione: massimo numero di iterazioni (" + iterations +
                    ") raggiunto senza convergenza");
        }
    }

    /**
     * Restituisce la tabella di routing di un nodo specifico.
     * @param nodeId L'ID del nodo richiesto
     * @return La RoutingTable del nodo, o null se il nodo non esiste
     */
    public RoutingTable getRoutingTable(String nodeId) {
        DistanceVectorNode node = nodes.get(nodeId);
        return node != null ? node.getRoutingTable() : null;
    }

    /**
     * Punto di ingresso principale per la simulazione.
     * @param args Argomenti da riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        NetworkSimulator simulator = new NetworkSimulator();

        System.out.println("=== Simulazione algoritmo Distance Vector ===");
        System.out.println("Topologia iniziale:");
        simulator.printCurrentRoutingTables();

        // Esegui la simulazione con un massimo di 10 iterazioni
        boolean converged = simulator.runSimulation(10);

        // Stampa i risultati finali
        System.out.println("\n=== Risultati finali ===");
        if (converged) {
            System.out.println("Tabelle di routing convergate:");
        } else {
            System.out.println("Tabelle di routing (non convergate):");
        }

        for (String nodeId : new String[]{"A", "B", "C", "D"}) {
            System.out.println(simulator.getRoutingTable(nodeId));
        }
    }
}