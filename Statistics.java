import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour collecter les statistiques et générer des histogrammes
 */
public class Statistics {
    private final List<Double> waitingTimesA;
    private final List<Double> waitingTimesB;
    
    public Statistics() {
        waitingTimesA = new ArrayList<>();
        waitingTimesB = new ArrayList<>();
    }
    
    /**
     * Ajoute un temps d'attente pour un client de type A
     * @param waitingTime Temps d'attente en secondes
     */
    public void addWaitingTimeA(double waitingTime) {
        waitingTimesA.add(waitingTime);
    }
    
    /**
     * Ajoute un temps d'attente pour un client de type B
     * @param waitingTime Temps d'attente en secondes
     */
    public void addWaitingTimeB(double waitingTime) {
        waitingTimesB.add(waitingTime);
    }
    
    /**
     * Génère un histogramme pour les temps d'attente
     * @param times Liste des temps d'attente
     * @param title Titre de l'histogramme
     * @param binSize Taille des intervalles en secondes
     */
    public void generateHistogram(List<Double> times, String title, double binSize) {
        if (times.isEmpty()) {
            System.out.println("Aucune donnée pour " + title);
            return;
        }
        
        // Trouver le min et max
        double min = times.stream().min(Double::compare).orElse(0.0);
        double max = times.stream().max(Double::compare).orElse(0.0);
        
        // Calculer le nombre de bins
        int numBins = (int) Math.ceil((max - min) / binSize);
        if (numBins == 0) numBins = 1;
        
        int[] bins = new int[numBins];
        
        // Remplir les bins
        for (double time : times) {
            int binIndex = (int) ((time - min) / binSize);
            if (binIndex >= numBins) binIndex = numBins - 1;
            bins[binIndex]++;
        }
        
        // Afficher l'histogramme
        System.out.println("\n=== " + title + " ===");
        System.out.println("Nombre de données: " + times.size());
        System.out.println("Min: " + min + "s, Max: " + max + "s");
        System.out.println("Taille des intervalles: " + binSize + "s");
        System.out.println("\nHistogramme:");
        
        for (int i = 0; i < numBins; i++) {
            double lowerBound = min + i * binSize;
            double upperBound = min + (i + 1) * binSize;
            System.out.printf("[%6.1f - %6.1f]s: %4d | %s\n", 
                lowerBound, upperBound, bins[i], 
                "*".repeat(bins[i] * 50 / times.size()));
        }
    }
    
    /**
     * Génère les deux histogrammes demandés
     */
    public void generateBothHistograms() {
        generateHistogram(waitingTimesA, "HISTOGRAMME TEMPS D'ATTENTE CLIENTS TYPE A", 60.0); // Bins de 60s
        generateHistogram(waitingTimesB, "HISTOGRAMME TEMPS D'ATTENTE CLIENTS TYPE B", 120.0); // Bins de 120s
    }
    
    public List<Double> getWaitingTimesA() { return waitingTimesA; }
    public List<Double> getWaitingTimesB() { return waitingTimesB; }
} 