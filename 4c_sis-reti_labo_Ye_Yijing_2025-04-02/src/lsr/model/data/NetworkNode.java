package lsr.model.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Rappresenta un nodo nella topologia di rete con i suoi collegamenti
 * Questa classe fa parte del modello nell'architettura MVC
 */
public class NetworkNode {
    private final int id;
    private final Map<NetworkNode, Integer> neighbors;

    /**
     * Costruttore per creare un nuovo nodo di rete
     * @param id Identificatore univoco del nodo
     */
    public NetworkNode(int id) {
        this.id = id;
        this.neighbors = new HashMap<>();
    }

    /**
     * Aggiunge un nodo vicino con il relativo costo del link
     * @param neighbor Nodo vicino
     * @param cost Costo del collegamento (deve essere positivo)
     * @throws IllegalArgumentException se il costo è negativo
     */
    public void addNeighbor(NetworkNode neighbor, int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Il costo del link non può essere negativo");
        }
        neighbors.put(neighbor, cost);
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public Map<NetworkNode, Integer> getNeighbors() {
        return new HashMap<>(neighbors); // Restituisce una copia per immutabilità
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkNode that = (NetworkNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}