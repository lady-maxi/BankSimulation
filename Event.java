/**
 * Représente un événement dans la simulation à événements discrets.
 * Les événements sont comparés par leur temps d'occurrence.
 */
public class Event implements Comparable<Event> {
    /** Types d'événements possibles dans la simulation */
    public enum Type { 
        /** Arrivée d'un client de type A */
        ARRIVAL_A, 
        /** Arrivée d'un client de type B */
        ARRIVAL_B, 
        /** Fin de service d'un client */
        END_OF_SERVICE, 
        /** Fin de la journée de travail */
        END_OF_DAY,
        CHANGE_PERIOD  
    }
    
    private final Type type;
    private final double time;
    private final Object data;

    /**
     * Constructeur d'un événement
     * @param type Type de l'événement
     * @param time Temps de l'événement en secondes
     * @param data Données associées à l'événement
     */
    public Event(Type type, double time, Object data) {
        this.type = type;
        this.time = time;
        this.data = data;
    }

    /** @return Le type de l'événement */
    public Type getType() { return type; }
    
    /** @return Le temps de l'événement en secondes */
    public double getTime() { return time; }
    
    /** @return Les données associées à l'événement */
    public Object getData() { return data; }

    /**
     * Compare deux événements par leur temps
     * @param other L'autre événement à comparer
     * @return Une valeur négative, zéro ou positive selon l'ordre
     */
    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}