import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //creo uno scanner che ricevera' in input il numero degli host
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci il numero di host: ");
        int numeroHost = scanner.nextInt();

        int prefissoCIDR = PainoIndirizzamentoCIDR.calcolaPrefissoCIDR(numeroHost);

        System.out.println("Il prefisso CIDR suggerito è: /" + prefissoCIDR);

        scanner.close();
    }
}
