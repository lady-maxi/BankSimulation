/**
 * Classe abstraite représentant un client de la banque.
 * Contient les informations communes à tous les types de clients.
 */
public abstract class Client {
    /** Temps d'arrivée du client en secondes */
    protected double arrivalTime;
    
    /** Temps de début de service en secondes */
    protected double startServiceTime;
    
    /** Temps de fin de service en secondes */
    protected double endServiceTime;
    
    /** Employé qui sert le client */
    protected Employee servedBy;

    /** @return Le temps d'arrivée du client */
    public double getArrivalTime() { return arrivalTime; }
    
    /** @return Le temps de début de service */
    public double getStartServiceTime() { return startServiceTime; }
    
    /** @return Le temps de fin de service */
    public double getEndServiceTime() { return endServiceTime; }
    
    /** @return L'employé qui sert le client */
    public Employee getServedBy() { return servedBy; }

    /**
     * Définit le temps de début de service
     * @param time Temps de début de service en secondes
     */
    public void setStartServiceTime(double time) { startServiceTime = time; }
    
    /**
     * Définit le temps de fin de service
     * @param time Temps de fin de service en secondes
     */
    public void setEndServiceTime(double time) { endServiceTime = time; }
    
    /**
     * Définit l'employé qui sert le client
     * @param e L'employé
     */
    public void setServedBy(Employee e) { servedBy = e; }

    /** @return Le temps d'attente du client en secondes */
    public double getWaitingTime() {
        return startServiceTime - arrivalTime;
    }
}