import java.util.HashMap;
import java.util.Map;

/**
 * Rappresenta un nodo che partecipa al protocollo Distance Vector.
 */
public class DistanceVectorNode {
    /** Identificatore unico del nodo */
    private final String nodeId;

    /** Mappa dei vicini diretti e costi dei link (neighbor -> cost) */
    private final Map<String, Integer> neighbors;

    /** Tabella di routing del nodo */
    private final RoutingTable routingTable;

    /**
     * Costruttore per inizializzare un nuovo nodo DV.
     * @param nodeId L'ID del nodo
     * @param neighbors Mappa dei vicini diretti e costi dei link
     */
    public DistanceVectorNode(String nodeId, Map<String, Integer> neighbors) {
        this.nodeId = nodeId;
        this.neighbors = new HashMap<>(neighbors); // Copia difensiva
        this.routingTable = new RoutingTable(nodeId);

        // Inizializzazione della tabella di routing
        initializeRoutingTable();
    }

    // Inizializza la tabella di routing con se stesso e i vicini diretti
    private void initializeRoutingTable() {
        routingTable.updateEntry(nodeId, 0, nodeId); // Distanza 0 verso se stesso
        neighbors.forEach((neighbor, cost) ->
                routingTable.updateEntry(neighbor, cost, neighbor));
    }

    /**
     * Prepara il distance vector da inviare ai vicini.
     * @return Un nuovo messaggio DistanceVectorMessage
     */
    public DistanceVectorMessage sendDistanceVector() {
        Map<String, Integer> vector = new HashMap<>();
        routingTable.getTable().forEach((dest, entry) ->
                vector.put(dest, entry.getDistance()));
        return new DistanceVectorMessage(nodeId, vector);
    }

    /**
     * Processa un distance vector ricevuto da un vicino.
     * @param message Il messaggio DV ricevuto
     * @return true se la tabella è stata aggiornata, false altrimenti
     */
    public boolean receiveDistanceVector(DistanceVectorMessage message) {
        String sender = message.getSender();
        boolean updated = false;

        for (Map.Entry<String, Integer> entry : message.getVector().entrySet()) {
            String destination = entry.getKey();
            int receivedDistance = entry.getValue();

            // Calcola il nuovo costo totale
            int costToSender = routingTable.getDistance(sender);
            int totalCost = costToSender + receivedDistance;

            // Aggiorna se troviamo un percorso migliore
            if (totalCost < routingTable.getDistance(destination)) {
                routingTable.updateEntry(destination, totalCost, sender);
                updated = true;
            }
        }

        return updated;
    }

    /**
     * @return La tabella di routing del nodo
     */
    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    @Override
    public String toString() {
        return routingTable.toString();
    }
}
