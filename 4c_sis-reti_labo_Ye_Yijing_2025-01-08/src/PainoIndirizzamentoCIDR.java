import java.util.Scanner;

public class PainoIndirizzamentoCIDR {
    //creazione di un metodo che in base ai numeri degli host che riceve fa una aggiunta di 2 che servono alla rete e al broadcast
    public static int calcolaPrefissoCIDR(int numeroHost) {
        int indirizziNecessari = numeroHost + 2; //servono alla rete e al broadcast
        int potenza = 1;
        //la forma primitiva del risultato della potenza(se non la inizializzassi verrebbero numeri non precisi visto che prende il numero intero della cella di memoria nella quale si trova)
        int prefisso = 32; //massimo numero per i prefissi di CIDR

        while (potenza < indirizziNecessari) {
            potenza *= 2; //la potenza di due definisce il prefisso CIDR piu' piccolo la quale puo' contenere tutti gli indirizzi IP
            prefisso--; //indica i primi tot bit che definiscono la rete
        }
        return prefisso;
    }
}
