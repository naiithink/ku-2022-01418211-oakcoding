package ku.cs.oakcoding.app.helpers.events.listeners;

import ku.cs.oakcoding.app.helpers.events.OakEventType;
import ku.cs.oakcoding.app.helpers.events.OakSignal;

public interface OakEventListener {

    void kill(OakSignal sig,
              String message);

    <T> void update(OakEventType eventType,
                    T context);

}
