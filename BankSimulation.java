import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe principale pour la simulation du système bancaire.
 * Lit les paramètres, exécute les simulations et affiche les résultats.
 */
public class BankSimulation {
    private static final Statistics globalStatistics = new Statistics();
    
    /**
     * Point d'entrée principal du programme
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Lire les paramètres depuis un fichier
        readParams("params.txt");
        
        double totalSumWaitA = 0;
        double totalSumWaitB = 0;
        int totalCountA = 0;
        int totalCountB = 0;

        System.out.println("Début de la simulation sur " + Params.N_DAYS + " jours...");
        
        for (int i = 0; i < Params.N_DAYS; i++) {
            BankDay day = new BankDay();
            day.simulate();
            
            // Collecter les statistiques globales
            Statistics dayStats = day.getStatistics();
            for (double time : dayStats.getWaitingTimesA()) {
                globalStatistics.addWaitingTimeA(time);
            }
            for (double time : dayStats.getWaitingTimesB()) {
                globalStatistics.addWaitingTimeB(time);
            }
            
            if (day.getCountA() > 0) {
                totalSumWaitA += day.getTotalWaitA();
                totalCountA += day.getCountA();
            }
            
            if (day.getCountB() > 0) {
                totalSumWaitB += day.getTotalWaitB();
                totalCountB += day.getCountB();
            }
            
            // Afficher la progression
            if ((i + 1) % 100 == 0) {
                System.out.println("Jour " + (i + 1) + " simulé");
            }
        }

        double wa = totalCountA > 0 ? totalSumWaitA / totalCountA : 0;
        double wb = totalCountB > 0 ? totalSumWaitB / totalCountB : 0;
        
        System.out.println("\n=== RÉSULTATS DE LA SIMULATION ===");
        System.out.println("Temps d'attente moyen type A (wa): " + wa + " secondes");
        System.out.println("Temps d'attente moyen type B (wb): " + wb + " secondes");
        System.out.println("Nombre total de clients A: " + totalCountA);
        System.out.println("Nombre total de clients B: " + totalCountB);
        System.out.println("Nombre de jours simulés: " + Params.N_DAYS);

        // Générer les histogrammes
        System.out.println("\n=== HISTOGRAMMES DES TEMPS D'ATTENTE ===");
        globalStatistics.generateBothHistograms();
    }

    /**
     * Lit les paramètres de simulation depuis un fichier
     * @param filename Le nom du fichier contenant les paramètres
     */
    private static void readParams(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Params.N1 = Integer.parseInt(br.readLine());
            Params.N2 = Integer.parseInt(br.readLine());
            Params.N3 = Integer.parseInt(br.readLine());
            Params.M1 = Integer.parseInt(br.readLine());
            Params.M2 = Integer.parseInt(br.readLine());
            Params.M3 = Integer.parseInt(br.readLine());
            Params.LAMBDA1 = Double.parseDouble(br.readLine());
            Params.LAMBDA2 = Double.parseDouble(br.readLine());
            Params.LAMBDA3 = Double.parseDouble(br.readLine());
            Params.MU_A = Double.parseDouble(br.readLine());
            Params.SIGMA_A = Double.parseDouble(br.readLine());
            Params.MU_R = Double.parseDouble(br.readLine());
            Params.SIGMA_R = Double.parseDouble(br.readLine());
            Params.MU_B = Double.parseDouble(br.readLine());
            Params.SIGMA_B = Double.parseDouble(br.readLine());
            Params.R = Double.parseDouble(br.readLine());
            Params.P = Double.parseDouble(br.readLine());
            Params.S = Double.parseDouble(br.readLine());
            Params.N_DAYS = Integer.parseInt(br.readLine());
            
            System.out.println("Paramètres chargés avec succès depuis " + filename);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de paramètres: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans le fichier de paramètres: " + e.getMessage());
            System.exit(1);
        }
    }
}