/**
 * Client de type B (avec rendez-vous).
 */
public class ClientB extends Client {
    private final Advisor advisor;
    private final double appointmentTime;

    public ClientB(double arrivalTime, Advisor advisor, double appointmentTime) {
        this.arrivalTime = arrivalTime;
        this.advisor = advisor;
        this.appointmentTime = appointmentTime;
    }

    public Advisor getAdvisor() { return advisor; }
    public double getAppointmentTime() { return appointmentTime; }
}