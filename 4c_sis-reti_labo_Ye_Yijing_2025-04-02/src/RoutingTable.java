import java.util.HashMap;
import java.util.Map;

/**
 * Rappresenta la tabella di routing di un nodo nella rete.
 * Mantiene le informazioni sulle destinazioni, distanze e next hop.
 */
public class RoutingTable {
    /** Identificatore del nodo a cui appartiene questa tabella */
    private final String nodeId;

    /** Mappa che contiene le voci di routing (destination -> RoutingEntry) */
    private final Map<String, RoutingEntry> table;

    /**
     * Costruttore per inizializzare una tabella di routing.
     * @param nodeId L'ID del nodo a cui appartiene questa tabella
     */
    public RoutingTable(String nodeId) {
        this.nodeId = nodeId;
        this.table = new HashMap<>();
    }

    /**
     * Aggiorna una voce nella tabella di routing.
     * @param destination La destinazione da aggiungere/aggiornare
     * @param distance La distanza verso la destinazione
     * @param nextHop Il next hop per raggiungere la destinazione
     */
    public void updateEntry(String destination, int distance, String nextHop) {
        table.put(destination, new RoutingEntry(distance, nextHop));
    }

    /**
     * Restituisce la distanza verso una destinazione.
     * @param destination La destinazione da interrogare
     * @return La distanza, o Integer.MAX_VALUE se la destinazione non è conosciuta
     */
    public int getDistance(String destination) {
        RoutingEntry entry = table.get(destination);
        return entry != null ? entry.getDistance() : Integer.MAX_VALUE;
    }

    /**
     * Restituisce il next hop verso una destinazione.
     * @param destination La destinazione da interrogare
     * @return Il next hop, o null se la destinazione non è conosciuta
     */
    public String getNextHop(String destination) {
        RoutingEntry entry = table.get(destination);
        return entry != null ? entry.getNextHop() : null;
    }

    /**
     * Restituisce una copia della tabella di routing.
     * @return Una mappa non modificabile delle voci di routing
     */
    public Map<String, RoutingEntry> getTable() {
        return new HashMap<>(table);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Routing Table for Node ").append(nodeId).append(":\n");
        sb.append("Dest\tDist\tNext Hop\n");
        for (Map.Entry<String, RoutingEntry> entry : table.entrySet()) {
            sb.append(entry.getKey()).append("\t")
                    .append(entry.getValue().getDistance()).append("\t")
                    .append(entry.getValue().getNextHop()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Classe interna che rappresenta una singola voce nella tabella di routing.
     */
    public static final class RoutingEntry {
        /** Distanza verso la destinazione */
        private final int distance;

        /** Next hop per raggiungere la destinazione */
        private final String nextHop;

        /**
         * Costruttore per una voce di routing.
         * @param distance La distanza verso la destinazione
         * @param nextHop Il next hop per raggiungere la destinazione
         */
        public RoutingEntry(int distance, String nextHop) {
            this.distance = distance;
            this.nextHop = nextHop;
        }

        /**
         * @return La distanza verso la destinazione
         */
        public int getDistance() {
            return distance;
        }

        /**
         * @return Il next hop per la destinazione
         */
        public String getNextHop() {
            return nextHop;
        }
    }
}