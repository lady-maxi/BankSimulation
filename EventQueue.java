import java.util.PriorityQueue;

/**
 * Gère la file des événements pour la simulation.
 * Les événements sont triés par temps croissant.
 * Implémente le pattern Singleton.
 */
public class EventQueue {
    private static EventQueue instance;
    private final PriorityQueue<Event> queue;

    private EventQueue() {
        queue = new PriorityQueue<>();
    }

    public static EventQueue getInstance() {
        if (instance == null) {
            instance = new EventQueue();
        }
        return instance;
    }

    public void addEvent(Event e) {
        queue.offer(e);
    }

    public Event nextEvent() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}