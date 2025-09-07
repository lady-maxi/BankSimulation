/**
 * Caissier (type C) : ne sert que les clients de type A.
 */
public class Cashier extends Employee {
    
    /**
     * Constructeur
     */
    public Cashier() {
        isFree = true;
        currentClient = null;
    }
    
    @Override
    public void startService(Client client, double time) {
        if (client instanceof ClientA) {
            setCurrentClient(client);
            setFree(false);
            client.setStartServiceTime(time);
            client.setServedBy(this);
            
            double serviceTime = LognormalGenerator.generate(Params.MU_A, Params.SIGMA_A);
            client.setEndServiceTime(time + serviceTime);
            
            EventQueue.getInstance().addEvent(new Event(Event.Type.END_OF_SERVICE, time + serviceTime, this));
        }
    }

    @Override
    public void endService(double time) {
        setFree(true);
        setCurrentClient(null);
    }
}