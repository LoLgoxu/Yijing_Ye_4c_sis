import java.util.Map;

/**
 * Rappresenta un messaggio Distance Vector scambiato tra nodi.
 * Contiene il vettore delle distanze del nodo mittente.
 */
public class DistanceVectorMessage {
    /** Nodo mittente del messaggio */
    private final String sender;

    /** Vettore delle distanze (destination -> distance) */
    private final Map<String, Integer> vector;

    /**
     * Costruttore per creare un nuovo messaggio DV.
     * @param sender L'ID del nodo mittente
     * @param vector Il vettore delle distanze del mittente
     */
    public DistanceVectorMessage(String sender, Map<String, Integer> vector) {
        this.sender = sender;
        this.vector = Map.copyOf(vector); // Copia difensiva per immutabilità
    }

    /**
     * @return L'ID del nodo mittente
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return Una vista non modificabile del vettore delle distanze
     */
    public Map<String, Integer> getVector() {
        return vector;
    }

    @Override
    public String toString() {
        return "DV from " + sender + ": " + vector;
    }
}
