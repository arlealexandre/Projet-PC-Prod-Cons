package objAdditionnel2;

import objAdditionnel1.MessageExec;
import objAdditionnel1.utils;

public class Executor extends Thread {

    private String tache;
    private int value;
    private boolean available;

    public Executor(MessageExec m) {
        this.tache = m.getTache();
        this.value = m.getValue();
        available = true;
    }

    public boolean isAvailable () {
        return this.available;
    }

    public void start(MessageExec m) {
        this.tache = m.getTache();
        this.value = m.getValue();
        this.run();
    }

    public void run() {
        available = false;
        System.out.println("Début du traitement de la tache");
        if (this.value < 0) { // Au vue des diverse tache choisi on interdit les valeurs négative
            System.out.println("Traitement de la tache impossible, valeur négative.");
        } else {
            // On réalise la bonne action en fonction de la tache demandé
            switch (tache) {
                case "fibo":
                    System.out.println("Calcule de la valeur de fibonacci au rang " + this.value + " ...");
                    System.out.println("La valeur au rang " + this.value + " de fibonacci est : " + utils.fibo(value));
                    break;
                case "factoriel":
                    System.out.println("Calcule de la valeur de " + this.value + "! ...");
                    System.out.println("La valeur de " + this.value + "! est : " + utils.fact(value));
                    break;
                case "diviseur":
                    System.out.println("Calcule des diviseurs de " + this.value + " ...");
                    System.out.print("Les diviseurs de " + this.value + " sont : ");
                    int[] div = utils.diviseur(value);
                    for (int i = 0; i < div.length; i++) {
                        if (i == div.length - 1) {
                            System.out.println(div[i]);
                        } else {
                            System.out.print(div[i] + ", ");
                        }
                        
                    }
                    break;
                case "premier":
                    System.out.println("Vérification si " + this.value + " est un nombre premier...");
                    if (utils.estPremier(value)) {
                        System.out.println("Le nombre " + this.value + " est premier !");
                    } else {
                        System.out.println("Le nombre " + this.value + " n'est pas premier !");
                    }

                    break;
                default:
                    System.out.println("Aucune tache à réaliser.");
                    break;
            }
            System.out.println("Fin de la tache.");
        }
        available = true;
    }
    
}
