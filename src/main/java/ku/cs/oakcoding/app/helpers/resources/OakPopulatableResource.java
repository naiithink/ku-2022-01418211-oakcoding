package ku.cs.oakcoding.app.helpers.resources;

public interface OakPopulatableResource<T> {

    String DUPLICATE_NAME_DELIMITER = "-";

    T duplicate();

    T duplicate(String name);
}
