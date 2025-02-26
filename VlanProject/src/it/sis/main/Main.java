package it.sis.main;
import it.sis.methods.VlanFuncs;

public class Main {
    public static void main(String[] args) {
        VlanFuncs funcs = new VlanFuncs();

        // Genera un numero random di VLAN da 3 a 6
        int numberOfVLANs = funcs.VlanRandomGenerator();
        System.out.println("Numero di VLAN generate: " + numberOfVLANs);

        funcs.hostNumGen(numberOfVLANs);
    }
}