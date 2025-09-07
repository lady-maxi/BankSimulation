import java.util.ArrayList;
import java.util.List;

/**
 * Simule une journée de travail à la banque.
 */
public class BankDay {
    private final EventQueue eventQueue;
    private final List<Cashier> cashiers;
    private final List<Advisor> advisors;
    private final List<ClientA> waitingA;
    private final List<ClientB> waitingB;
    private double currentTime;
    private double totalWaitA;
    private double totalWaitB;
    private int countA;
    private int countB;
    private final Statistics statistics;

    public BankDay() {
        eventQueue = EventQueue.getInstance();
        cashiers = new ArrayList<>();
        advisors = new ArrayList<>();
        waitingA = new ArrayList<>();
        waitingB = new ArrayList<>();
        currentTime = 0;
        totalWaitA = 0;
        totalWaitB = 0;
        countA = 0;
        countB = 0;
        statistics = new Statistics();
        
        // Réinitialiser la file d'événements
        while (!eventQueue.isEmpty()) {
            eventQueue.nextEvent();
        }
    }

    public void simulate() {
        initializeDay();
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.nextEvent();
            currentTime = event.getTime();
            switch (event.getType()) {
                case ARRIVAL_A -> handleArrivalA((ClientA) event.getData());
                case ARRIVAL_B -> handleArrivalB((ClientB) event.getData());
                case END_OF_SERVICE -> handleEndOfService((Employee) event.getData());
                case CHANGE_PERIOD -> handleChangePeriod((Integer) event.getData());
                case END_OF_DAY -> {} // Fin de journée
            }
        }
    }

    private void initializeDay() {
        // Initialiser les employés pour la première période
        initializeEmployees(Params.N1, Params.M1);
        
        // Générer les arrivées pour les 3 périodes
        generateArrivalsA(0, 2 * Params.HOUR, Params.LAMBDA1);
        generateArrivalsA(2 * Params.HOUR, 4 * Params.HOUR, Params.LAMBDA2);
        generateArrivalsA(4 * Params.HOUR, 6 * Params.HOUR, Params.LAMBDA3);
        
        // Événements de changement de période
        eventQueue.addEvent(new Event(Event.Type.CHANGE_PERIOD, 2 * Params.HOUR, 2));
        eventQueue.addEvent(new Event(Event.Type.CHANGE_PERIOD, 4 * Params.HOUR, 3));
        eventQueue.addEvent(new Event(Event.Type.END_OF_DAY, 6 * Params.HOUR, null));
        
        generateAppointments();
    }

    private void initializeEmployees(int n, int m) {
        for (int i = 0; i < n; i++) {
            cashiers.add(new Cashier());
        }
        for (int i = 0; i < m; i++) {
            advisors.add(new Advisor());
        }
    }

    private void generateArrivalsA(double start, double end, double lambda) {
        double t = start;
        while (t < end) {
            double interArrival = -Math.log(Math.random()) / (lambda / Params.HOUR);
            t += interArrival;
            if (t < end) {
                ClientA client = new ClientA(t);
                eventQueue.addEvent(new Event(Event.Type.ARRIVAL_A, t, client));
            }
        }
    }

    private void generateAppointments() {
        for (Advisor advisor : advisors) {
            for (int slot = 0; slot < 12; slot++) {
                if (Math.random() < Params.R) {
                    double appointmentTime = 10 * Params.HOUR + slot * 30 * Params.MINUTE;
                    if (Math.random() > Params.P) {
                        double arrivalTime = appointmentTime + NormalGenerator.generate(Params.MU_R, Params.SIGMA_R);
                        ClientB client = new ClientB(arrivalTime, advisor, appointmentTime);
                        advisor.addAppointment(client);
                        eventQueue.addEvent(new Event(Event.Type.ARRIVAL_B, arrivalTime, client));
                    }
                }
            }
        }
    }

private void handleArrivalA(ClientA client) {
    countA++;
    
    // Chercher d'abord un caissier libre
    for (Cashier cashier : cashiers) {
        if (cashier.isFree()) {
            cashier.startService(client, currentTime);
            return;
        }
    }
    
    // Si pas de caissier libre, chercher un sonseiller  libre
    for (Advisor advisor : advisors) {
        if (advisor.isFree()) {
            // Vérifier le seuil S : ne sert un client A que si aucun rendez-vous dans S secondes
            if (!advisor.hasAppointmentWithinThreshold(currentTime)) {
                advisor.startService(client, currentTime);
                return;
            }
        }
    }
    
    // Si aucun employé libre, mettre en attente
    waitingA.add(client);
    // System.out.println("Client A mis en attente"); // debug
}
    private void handleArrivalB(ClientB client) {
        countB++;
        
        Advisor advisor = client.getAdvisor();
        if (advisor.isFree()) {
            advisor.startService(client, currentTime);
        } else {
            waitingB.add(client);
        }
    }

    private void handleChangePeriod(int period) {
        if (period == 2) {
            adjustEmployees(Params.N2, Params.M2);
        } else if (period == 3) {
            adjustEmployees(Params.N3, Params.M3);
        }
    }

    private void adjustEmployees(int targetCashiers, int targetAdvisors) {
        // Ajuster les caissiers
        while (cashiers.size() > targetCashiers) {
            Cashier cashier = cashiers.remove(cashiers.size() - 1);
            if (!cashier.isFree()) {
                cashier.endService(currentTime);
            }
        }
        while (cashiers.size() < targetCashiers) {
            cashiers.add(new Cashier());
        }
        
        // Ajuster les conseillers
        while (advisors.size() > targetAdvisors) {
            Advisor advisor = advisors.remove(advisors.size() - 1);
            if (!advisor.isFree()) {
                advisor.endService(currentTime);
            }
        }
        while (advisors.size() < targetAdvisors) {
            advisors.add(new Advisor());
        }
    }

    private void handleEndOfService(Employee employee) {
        Client client = employee.getCurrentClient();
        if (client != null) {
            double waitingTime = client.getWaitingTime();
            
            if (client instanceof ClientA) {
                totalWaitA += waitingTime;
                statistics.addWaitingTimeA(waitingTime);
            } else {
                totalWaitB += waitingTime;
                statistics.addWaitingTimeB(waitingTime);
            }
        }
        
        employee.endService(currentTime);
        
        // Gérer les files d'attente
        if (!waitingA.isEmpty()) {
            ClientA nextClient = waitingA.remove(0);
            employee.startService(nextClient, currentTime);
        } else if (employee instanceof Advisor && !waitingB.isEmpty()) {
            ClientB nextClient = waitingB.remove(0);
            employee.startService(nextClient, currentTime);
        }
    }

    public double getTotalWaitA() { return totalWaitA; }
    public double getTotalWaitB() { return totalWaitB; }
    public int getCountA() { return countA; }
    public int getCountB() { return countB; }
    public Statistics getStatistics() { return statistics; }
}