/**
 * Classe abstraite pour un employé.
 */
public abstract class Employee {
    protected boolean isFree = true;
    protected Client currentClient = null;

    public boolean isFree() { return isFree; }
    public void setFree(boolean free) { isFree = free; }
    public Client getCurrentClient() { return currentClient; }
    public void setCurrentClient(Client client) { currentClient = client; }

    public abstract void startService(Client client, double time);
    public abstract void endService(double time);
}