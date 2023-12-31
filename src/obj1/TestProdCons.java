package obj1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {

    public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {

        ArrayList<Thread> threads = new ArrayList<>();

        /*
         * Récupération des propriétés dans le fichier options
         */
        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream("options.xml"));

        int nProd = Integer.parseInt(properties.getProperty("nProd"));
        int nCons = Integer.parseInt(properties.getProperty("nCons"));
        int sizeB = Integer.parseInt(properties.getProperty("bufSz"));
        int prodTime = Integer.parseInt(properties.getProperty("prodTime"));
        int consTime = Integer.parseInt(properties.getProperty("consTime"));
        int minProd = Integer.parseInt(properties.getProperty("minProd"));
        int maxProd = Integer.parseInt(properties.getProperty("maxProd"));

        System.out.println("BUFFER SIZE = " + Integer.toString(sizeB) + "\n");

        // Création du buffer
        ProdConsBufferMonitor buffer = new ProdConsBufferMonitor(sizeB, prodTime, consTime);

        // Création des producteurs
        for (int i = 0; i < nProd; i++) {
            Producer p = new Producer(buffer, minProd, maxProd);
            p.setName(String.valueOf(i));
            threads.add(p);
        }

        // Création des consommateurs
        for (int i = 0; i < nCons; i++) {
            Consumer c = new Consumer(buffer);
            c.setName(String.valueOf(i));
            threads.add(c);
        }

        // On démarre tous les threads
        for (Thread t : threads) {
            t.start();
        }

        // On attend que tous les threads aient finit (les consommateurs ne terminerons jamais et donc le main non plus)
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All threads have finished. Terminating..."); // Code mort...
    }
}
