package lsr;

import lsr.controller.RoutingController;
import lsr.view.RoutingView;

/**
 * Classe principale che avvia l'applicazione
 * Configura l'architettura MVC e avvia il flusso
 */
public class LinkStateRoutingApp {
    public static void main(String[] args) {
        // Configurazione MVC
        RoutingController controller = new RoutingController();
        controller.buildSampleTopology();

        RoutingView view = new RoutingView(controller);

        // Avvio dell'interfaccia utente
        view.showMenu();
    }
}
