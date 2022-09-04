package ku.cs.oakcoding.app.helpers.events.publisher;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import ku.cs.oakcoding.app.helpers.events.OakEventType;
import ku.cs.oakcoding.app.helpers.events.listeners.OakEventListener;

public final class OakEventManager<K extends OakEventType> {

    private ConcurrentMap<K, CopyOnWriteArrayList<OakEventListener>> listeners = new ConcurrentHashMap<>();

    public OakEventManager(K... operations) {
        Objects.requireNonNull(operations);

        for (K operation : operations) {
            this.listeners.put(operation, new CopyOnWriteArrayList<>());
        }
    }

    public void subscribe(K eventType, OakEventListener listener) {
        CopyOnWriteArrayList<OakEventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(K eventType, OakEventListener listener) {
        CopyOnWriteArrayList<OakEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public <T> void publish(K eventType, T context) {
        CopyOnWriteArrayList<OakEventListener> users = listeners.get(eventType);

        for (OakEventListener listener : users) {
            listener.update(eventType, context);
        }
    }
}
