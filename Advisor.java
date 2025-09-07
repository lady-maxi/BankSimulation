import java.util.ArrayList;
import java.util.List;

/**
 * Conseiller (type D) : peut servir tous les clients, mais priorité aux rendez-vous.
 */
public class Advisor extends Employee {
    private final List<ClientB> appointments;

    /**
     * Constructeur d'un conseiller
     */
    public Advisor() {
        appointments = new ArrayList<>();
        isFree = true;
        currentClient = null;
    }

    /**
     * Ajoute un rendez-vous pour ce conseiller
     * @param client Le client ayant pris rendez-vous
     */
    public void addAppointment(ClientB client) {
        appointments.add(client);
    }

    /**
 * Retourne le prochain rendez-vous après le temps donné (trié par heure)
 * @param time Temps actuel
 * @return Le prochain client avec rendez-vous ou null
 */
 public ClientB getNextAppointment(double time) {
    ClientB nextAppointment = null;
    for (ClientB client : appointments) {
        if (client.getAppointmentTime() >= time) {
            if (nextAppointment == null || client.getAppointmentTime() < nextAppointment.getAppointmentTime()) {
                nextAppointment = client;
            }
        }
    }
    return nextAppointment;
}

    /**
     * Démarre le service d'un client
     * @param client Le client à servir
     * @param time Temps actuel de la simulation
     */
    @Override
    public void startService(Client client, double time) {
        if (client == null) {
            return;
        }
        
        setCurrentClient(client);
        setFree(false);
        client.setStartServiceTime(time);
        client.setServedBy(this);
        
        double serviceTime;
        if (client instanceof ClientA) {
            serviceTime = LognormalGenerator.generate(Params.MU_A, Params.SIGMA_A);
        } else {
            serviceTime = LognormalGenerator.generate(Params.MU_B, Params.SIGMA_B);
        }
        
        client.setEndServiceTime(time + serviceTime);
        EventQueue.getInstance().addEvent(new Event(Event.Type.END_OF_SERVICE, time + serviceTime, this));
    }

    /**
     * Termine le service en cours
     * @param time Temps actuel de la simulation
     */
    @Override
    public void endService(double time) {
        setFree(true);
        setCurrentClient(null);
    }
    
    /**
     * Vérifie si le conseiller a des rendez-vous planifiés
     * @return true si des rendez-vous sont prévus
     */
    public boolean hasAppointments() {
        return !appointments.isEmpty();
    }


     /**
 * Vérifie si un rendez-vous est prévu dans les S secondes
 * @param currentTime Temps actuel
 * @return true si un rendez-vous arrive dans moins de S secondes
 */
public boolean hasAppointmentWithinThreshold(double currentTime) {
    for (ClientB client : appointments) {
        double timeUntilAppointment = client.getAppointmentTime() - currentTime;
        if (timeUntilAppointment >= 0 && timeUntilAppointment <= Params.S) {
            return true;
        }
    }
    return false;
}
}