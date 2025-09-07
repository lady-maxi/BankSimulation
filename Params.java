/**
 * Stocke tous les paramètres de simulation pour le système bancaire.
 * Toutes les variables sont statiques pour un accès global.
 */
public class Params {
    /** Nombre de caissiers par période */
    public static int N1, N2, N3;
    
    /** Nombre de conseillers par période */
    public static int M1, M2, M3;
    
    /** Taux d'arrivée des clients A par heure pour chaque période */
    public static double LAMBDA1, LAMBDA2, LAMBDA3;
    
    /** Moyenne et écart-type du temps de service pour les clients A (secondes) */
    public static double MU_A, SIGMA_A;
    
    /** Moyenne et écart-type du retard des clients B (secondes) */
    public static double MU_R, SIGMA_R;
    
    /** Moyenne et écart-type du temps de service pour les clients B (secondes) */
    public static double MU_B, SIGMA_B;
    
    /** Probabilité d'avoir un rendez-vous pour une plage horaire */
    public static double R;
    
    /** Probabilité de non-présentation d'un client avec rendez-vous */
    public static double P;
    
    /** Seuil en secondes pour qu'un conseiller serve un client A */
    public static double S;
    
    /** Nombre de jours à simuler */
    public static int N_DAYS;
    
    /** Constante pour convertir les minutes en secondes */
    public static final int MINUTE = 60;
    
    /** Constante pour convertir les heures en secondes */
    public static final int HOUR = 3600;
}