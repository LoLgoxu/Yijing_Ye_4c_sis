package it.sis.methods;

import java.util.Random;

/***
 * public class that contains all the methods I need
 * using classless VLSM methods
 */
public class VlanFuncs {

    Random rand = new Random();

    /***
     * empty constructor
     */
    public VlanFuncs() {

    }

    /***
     * Create a random number of VLANs
     * @return number from 3-6 that are VLANs
     */
    public int VlanRandomGenerator() {
        return rand.nextInt(4) + 3; // 3, 4, 5 o 6
    }

    /***
     * Generate an addressing plan based of the number of VLANs and hosts
     * @param vlanId
     * @param hostCount
     */
    public void generateAddressingPlan(int vlanId, int hostCount) {

        // Calculating the prefix of the subnet based by the number of hosts
        int bitsForHosts = (int) Math.ceil(Math.log(hostCount + 2) / Math.log(2)); // (int) = the result must be integer
        int prefixLength = 32 - bitsForHosts; //Net// ID                           // ceil rounds a double created from a division from to logarithms

        // base address (192.168.x.0 private address in this case)
        int baseAddress = 19216800 + vlanId * 10; // 19216800 represents 192.168.0.0

        String networkAddress = "192.168." + vlanId + ".0/" + prefixLength;
        int usableHosts = (int) Math.pow(2, bitsForHosts) - 2;

        System.out.println("Indirizzo di rete: " + networkAddress);
        System.out.println("Host utilizzabili: " + usableHosts);
        System.out.println();
    }

    /***
     * Random number of hosts generator
     * @param numberOfVLANs
     */
    public void hostNumGen(int numberOfVLANs) {
        // for cycle for the addressing output while generating hosts
        for (int i = 0; i < numberOfVLANs; i++) {
            // for every VLAN generate a casual number of hosts from 10-100
            int numberOfHosts = rand.nextInt(91) + 10; // 10-100 host
            System.out.println("VLAN " + (i + 1) + ": Numero di host = " + numberOfHosts);

            // Calcola e mostra il piano di indirizzamento IP per la VLAN
            generateAddressingPlan(i+1, numberOfHosts);
        }
    }
}
