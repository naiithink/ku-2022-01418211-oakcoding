package ku.cs.oakcoding.app.helpers.events;

public class OakEventType {
    private OakEventType() {}

    public enum Configs {

        LOGGER
    }

    public enum ResourceEvents {

        RESOURCE_INFO,
        RESOURCE_ADD,
        RESOURCE_OPEN,
        RESOURCE_CLOSE;

    }
}
